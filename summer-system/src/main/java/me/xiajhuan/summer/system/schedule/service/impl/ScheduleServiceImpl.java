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

package me.xiajhuan.summer.system.schedule.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.common.quartz.helper.QuartzHelper;
import me.xiajhuan.summer.system.schedule.dto.ScheduleTaskDto;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import me.xiajhuan.summer.system.schedule.mapper.ScheduleTaskMapper;
import me.xiajhuan.summer.system.schedule.service.ScheduleTaskService;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 任务 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/17
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class ScheduleServiceImpl extends ServiceImpl<ScheduleTaskMapper, ScheduleTaskEntity> implements ScheduleTaskService, MpHelper<ScheduleTaskDto, ScheduleTaskEntity> {

    @Resource
    private Scheduler scheduler;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<ScheduleTaskEntity> getQueryWrapper(ScheduleTaskDto dto) {
        LambdaQueryWrapper<ScheduleTaskEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // Bean名称
        String beanName = dto.getBeanName();
        queryWrapper.eq(StrUtil.isNotBlank(beanName), ScheduleTaskEntity::getBeanName, beanName);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, ScheduleTaskEntity::getStatus, status);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<ScheduleTaskEntity> getSelectWrapper(ScheduleTaskDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<ScheduleTaskEntity> addSelectField(LambdaQueryWrapper<ScheduleTaskEntity> queryWrapper) {
        return queryWrapper.select(ScheduleTaskEntity::getId, ScheduleTaskEntity::getBeanName, ScheduleTaskEntity::getJson,
                ScheduleTaskEntity::getCronExpression, ScheduleTaskEntity::getType, ScheduleTaskEntity::getStatus,
                ScheduleTaskEntity::getDescription, ScheduleTaskEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(ScheduleTaskDto dto) {
        // Bean名称不能重复
        String beanName = dto.getBeanName();
        if (baseMapper.exist(beanName) != null) {
            throw ValidationException.of(ErrorCode.BEAN_NAME_EXISTS, beanName);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<ScheduleTaskDto> page(ScheduleTaskDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), ScheduleTaskDto.class);
    }

    @Override
    public ScheduleTaskDto getById(Long id) {
        LambdaQueryWrapper<ScheduleTaskEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(ScheduleTaskEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), ScheduleTaskDto.class);
    }

    @Override
    public void add(ScheduleTaskDto dto) {
        handleDtoBefore(dto);

        ScheduleTaskEntity entity = BeanUtil.convert(dto, ScheduleTaskEntity.class);

        if (save(entity)) {
            // 新增任务
            QuartzHelper.addTask(scheduler, entity);
        }
    }

    @Override
    public void update(ScheduleTaskDto dto) {
        handleDtoBefore(dto);

        ScheduleTaskEntity entity = BeanUtil.convert(dto, ScheduleTaskEntity.class);

        if (updateById(entity)) {
            // 修改任务
            QuartzHelper.updateTask(scheduler, entity);
        }
    }

    @Override
    public void delete(Long[] ids) {
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        if (removeByIds(idSet)) {
            // 删除任务
            idSet.forEach(id -> QuartzHelper.deleteTask(scheduler, id));
        }
    }

}
