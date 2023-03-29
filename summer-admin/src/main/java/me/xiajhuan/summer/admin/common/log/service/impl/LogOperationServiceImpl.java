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
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.admin.common.log.dto.LogOperationDto;
import me.xiajhuan.summer.admin.common.log.entity.LogOperationEntity;
import me.xiajhuan.summer.admin.common.log.mapper.LogOperationMapper;
import me.xiajhuan.summer.admin.common.log.service.LogOperationService;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Service
@DS(DataSourceConst.COMMON)
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperationEntity> implements LogOperationService, MpHelper<LogOperationDto, LogOperationEntity> {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LogOperationEntity> getSelectWrapper(Class<LogOperationEntity> entityClass) {
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(LogOperationEntity::getId, LogOperationEntity::getOperation, LogOperationEntity::getOperationGroup,
                LogOperationEntity::getRequestUri, LogOperationEntity::getRequestMethod, LogOperationEntity::getRequestParams,
                LogOperationEntity::getRequestTime, LogOperationEntity::getUserAgent, LogOperationEntity::getIp,
                LogOperationEntity::getStatus, LogOperationEntity::getOperateBy, LogOperationEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogOperationEntity> getQueryWrapper(LogOperationDto dto) {
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = getSelectWrapper(currentModelClass());
        // 动态Sql查询条件
        // 操作分组
        Integer operationGroup = dto.getOperationGroup();
        queryWrapper.eq(operationGroup != null, LogOperationEntity::getOperationGroup, operationGroup);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogOperationEntity::getStatus, status);
        // 创建时间（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        if (createTimeStart != null) {
            queryWrapper.ge(LogOperationEntity::getCreateTime, createTimeStart);
        }
        Date createTimeEnd = dto.getCreateTimeEnd();
        if (createTimeEnd != null) {
            queryWrapper.le(LogOperationEntity::getCreateTime, createTimeEnd);
        }

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LogOperationDto> page(LogOperationDto dto) {
        return ConvertUtil.convert(page(handlePageSort(dto), getQueryWrapper(dto)), LogOperationDto.class);
    }

    @Override
    public List<LogOperationDto> list(LogOperationDto dto) {
        return ConvertUtil.convert(list(getSortWrapper(dto)), LogOperationDto.class);
    }

    @Override
    public void saveAsync(LogOperationEntity entity) {
        save(entity);
    }

    @Override
    public void clear() {
        // 删除操作日志
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(LogOperationEntity::getId);
        queryWrapper.lt(LogOperationEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(),
                setting.getInt("operation.clear-days-limit", "Log", -30)));

        List<LogOperationEntity> entityList = list(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            removeByIds(entityList.stream().map(LogOperationEntity::getId)
                    .collect(Collectors.toSet()));
        }
    }

}
