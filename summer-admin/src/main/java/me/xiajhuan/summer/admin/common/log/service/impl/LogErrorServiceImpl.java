/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.log.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.data.PageAndSort;
import me.xiajhuan.summer.admin.common.log.dto.LogErrorDto;
import me.xiajhuan.summer.admin.common.log.entity.LogErrorEntity;
import me.xiajhuan.summer.admin.common.log.mapper.LogErrorMapper;
import me.xiajhuan.summer.admin.common.log.service.LogErrorService;
import me.xiajhuan.summer.core.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import me.xiajhuan.summer.core.utils.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 错误日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
@Service
@DS(DataSourceConst.COMMON)
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogErrorEntity> implements LogErrorService, MpCommonOperation<LogErrorDto, LogErrorEntity> {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<LogErrorEntity> getSelectWrapper(Class<LogErrorEntity> entityClass) {
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(LogErrorEntity::getId, LogErrorEntity::getRequestUri, LogErrorEntity::getRequestMethod,
                LogErrorEntity::getRequestParams, LogErrorEntity::getUserAgent, LogErrorEntity::getIp,
                LogErrorEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogErrorEntity> getQueryWrapper(LogErrorDto dto, boolean isCount) {
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 创建时间（闭区间）
        Date createTimeStart = dto.getParams().getDate("createTimeStart");
        if (createTimeStart != null) {
            queryWrapper.ge(LogErrorEntity::getCreateTime, createTimeStart);
        }
        Date createTimeEnd = dto.getParams().getDate("createTimeEnd");
        if (createTimeEnd != null) {
            queryWrapper.le(LogErrorEntity::getCreateTime, createTimeEnd);
        }

        return queryWrapper;
    }

    @Override
    public IPage<LogErrorEntity> customPage(Page<LogErrorEntity> page, LogErrorDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<LogErrorEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public IPage<LogErrorDto> page(PageAndSort pageAndSort, LogErrorDto dto) {
        return ConvertUtil.convert(customPage(handlePageAndSort(pageAndSort), dto), LogErrorDto.class);
    }

    @Override
    public List<LogErrorDto> list(LogErrorDto dto) {
        return ConvertUtil.convert(list(getQueryWrapper(dto, false)), LogErrorDto.class);
    }

    @Override
    public LogErrorDto getById(Long id) {
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(LogErrorEntity::getId, LogErrorEntity::getErrorInfo);
        queryWrapper.eq(LogErrorEntity::getId, id);

        return ConvertUtil.convert(getOne(queryWrapper), LogErrorDto.class);
    }

    @Override
    public void saveLogAsync(Exception e, HttpServletRequest request) {
        // 构建错误日志Entity
        LogErrorEntity log = LogErrorEntity.builder()
                .ip(IpUtil.getRequestOriginIp(request))
                .userAgent(HttpContextUtil.getUserAgent(request))
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod()).build();

        // 请求参数
        Map<String, String> params = HttpContextUtil.getParameterMap(request);
        if (MapUtil.isNotEmpty(params)) {
            log.setRequestParams(JSONUtil.toJsonStr(params));
        }

        // 异常堆栈信息
        log.setErrorInfo(ExceptionUtil.stacktraceToString(e, setting.getInt("error.stacktrace.length", "Log", 65535)));

        save(log);
    }

    @Override
    public void clearLog() {
        // 删除错误日志
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(LogErrorEntity::getId);
        queryWrapper.lt(LogErrorEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), setting.getInt("error.clear.day-limit", "Log", -90)));

        List<LogErrorEntity> entityList = list(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            removeByIds(entityList.stream().map(LogErrorEntity::getId)
                    .collect(Collectors.toSet()));
        }
    }

}