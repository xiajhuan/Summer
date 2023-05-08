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

import cn.hutool.core.date.DateUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.log.dto.LogOperationDto;
import me.xiajhuan.summer.system.log.entity.LogOperationEntity;
import me.xiajhuan.summer.system.log.mapper.LogOperationMapper;
import me.xiajhuan.summer.system.log.service.LogOperationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 操作日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperationEntity> implements LogOperationService, MpHelper<LogOperationDto, LogOperationEntity> {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    /**
     * 日志清理天数限制
     */
    private int clearDaysLimit;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        clearDaysLimit = setting.getInt("operation.clear-days-limit", "Log", -30);
    }

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LogOperationEntity> getQueryWrapper(LogOperationDto dto) {
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 操作分组
        Integer operationGroup = dto.getOperationGroup();
        queryWrapper.eq(operationGroup != null, LogOperationEntity::getOperationGroup, operationGroup);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogOperationEntity::getStatus, status);
        // 创建时间范围（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        queryWrapper.ge(createTimeStart != null, LogOperationEntity::getCreateTime, createTimeStart);
        Date createTimeEnd = dto.getCreateTimeEnd();
        queryWrapper.le(createTimeEnd != null, LogOperationEntity::getCreateTime, createTimeEnd);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogOperationEntity> getSelectWrapper(LogOperationDto dto) {
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(LogOperationEntity::getId, LogOperationEntity::getOperation, LogOperationEntity::getOperationGroup,
                LogOperationEntity::getRequestUri, LogOperationEntity::getRequestMethod, LogOperationEntity::getRequestParams,
                LogOperationEntity::getRequestTime, LogOperationEntity::getUserAgent, LogOperationEntity::getIp,
                LogOperationEntity::getStatus, LogOperationEntity::getOperateBy, LogOperationEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LogOperationDto> page(LogOperationDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LogOperationDto.class);
    }

    @Override
    public List<LogOperationDto> list(LogOperationDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LogOperationDto.class);
    }

    @Override
    public long count(LogOperationDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public void saveAsync(LogOperationEntity entity) {
        save(entity);
    }

    @Override
    public void clear() {
        // 删除操作日志
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.lt(LogOperationEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), clearDaysLimit));
        remove(queryWrapper);
    }

}
