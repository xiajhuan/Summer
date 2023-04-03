/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.system.log.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.ServletUtil;
import me.xiajhuan.summer.system.log.dto.LogErrorDto;
import me.xiajhuan.summer.system.log.entity.LogErrorEntity;
import me.xiajhuan.summer.system.log.mapper.LogErrorMapper;
import me.xiajhuan.summer.system.log.service.LogErrorService;
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
@DS(DataSourceConst.SYSTEM)
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogErrorEntity> implements LogErrorService, MpHelper<LogErrorDto, LogErrorEntity> {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LogErrorEntity> getQueryWrapper(LogErrorDto dto) {
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 创建时间（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        if (createTimeStart != null) {
            queryWrapper.ge(LogErrorEntity::getCreateTime, createTimeStart);
        }
        Date createTimeEnd = dto.getCreateTimeEnd();
        if (createTimeEnd != null) {
            queryWrapper.le(LogErrorEntity::getCreateTime, createTimeEnd);
        }

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogErrorEntity> getSelectWrapper(LogErrorDto dto) {
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(LogErrorEntity::getId, LogErrorEntity::getRequestUri, LogErrorEntity::getRequestMethod,
                LogErrorEntity::getRequestParams, LogErrorEntity::getUserAgent, LogErrorEntity::getIp,
                LogErrorEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LogErrorDto> page(LogErrorDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LogErrorDto.class);
    }

    @Override
    public List<LogErrorDto> list(LogErrorDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LogErrorDto.class);
    }

    @Override
    public LogErrorDto getById(Long id) {
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(LogErrorEntity::getId, id);
        queryWrapper.select(LogErrorEntity::getId, LogErrorEntity::getErrorInfo);

        return BeanUtil.convert(getOne(queryWrapper), LogErrorDto.class);
    }

    @Override
    public void saveAsync(Exception e, HttpServletRequest request) {
        // 构建错误日志
        LogErrorEntity entity = LogErrorEntity.builder()
                .ip(ServletUtil.getClientIP(request))
                .userAgent(ServletUtil.getUserAgent(request))
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod()).build();

        // 请求参数，note：这里只能获取Query/FORM-DATA参数
        Map<String, String> params = ServletUtil.getParamMap(request);
        if (MapUtil.isNotEmpty(params)) {
            entity.setRequestParams(JSONUtil.toJsonStr(params));
        }

        // 异常堆栈信息
        entity.setErrorInfo(ExceptionUtil.stacktraceToString(e,
                setting.getInt("error.stacktrace-length", "Log", 65535)));

        save(entity);
    }

    @Override
    public void clear() {
        // 删除错误日志
        LambdaQueryWrapper<LogErrorEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.lt(LogErrorEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(),
                setting.getInt("error.clear-days-limit", "Log", -90)));
        queryWrapper.select(LogErrorEntity::getId);

        List<LogErrorEntity> entityList = list(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            removeByIds(entityList.stream().map(LogErrorEntity::getId)
                    .collect(Collectors.toSet()));
        }
    }

}