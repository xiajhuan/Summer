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
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.properties.QuartzStartupProperties;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.system.schedule.quartz.helper.QuartzHelper;
import me.xiajhuan.summer.system.schedule.dto.ScheduleTaskDto;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import me.xiajhuan.summer.system.schedule.mapper.ScheduleTaskMapper;
import me.xiajhuan.summer.system.schedule.service.ScheduleTaskService;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 定时任务 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/17
 * @see Scheduler
 * @see CronExpression
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class ScheduleTaskServiceImpl extends ServiceImpl<ScheduleTaskMapper, ScheduleTaskEntity> implements ScheduleTaskService, MpHelper<ScheduleTaskDto, ScheduleTaskEntity> {

    @Resource
    private QuartzStartupProperties quartzStartupProperties;

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
        // 类型
        Integer type = dto.getType();
        queryWrapper.eq(type != null, ScheduleTaskEntity::getType, type);
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
        // Cron表达式校验
        if (!CronExpression.isValidExpression(dto.getCronExpression())) {
            throw ValidationException.of(ErrorCode.CRON_EXPRESSION_ERROR);
        }

        // 新增时Bean名称不能重复
        if (dto.getId() == null) {
            String beanName = dto.getBeanName();
            if (baseMapper.exist(beanName) != null) {
                throw ValidationException.of(ErrorCode.BEAN_NAME_EXISTS, beanName);
            }
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
    @Transactional(rollbackFor = Exception.class)
    public void add(ScheduleTaskDto dto) {
        handleDtoBefore(dto);

        ScheduleTaskEntity entity = BeanUtil.convert(dto, ScheduleTaskEntity.class);

        if (save(entity)) {
            // 新增任务
            QuartzHelper.addTask(scheduler, entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleTaskDto dto) {
        handleDtoBefore(dto);

        ScheduleTaskEntity entity = BeanUtil.convert(dto, ScheduleTaskEntity.class);

        if (updateById(entity)) {
            // 修改任务
            QuartzHelper.updateTask(scheduler, entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        if (removeByIds(idSet)) {
            // 删除任务
            idSet.forEach(id -> QuartzHelper.deleteTask(scheduler, id));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(Long[] ids) {
        checkStartup();

        LambdaQueryWrapper<ScheduleTaskEntity> queryWrapper = addSelectFieldForQuartz();
        queryWrapper.in(ScheduleTaskEntity::getId, ids);
        List<ScheduleTaskEntity> entityList = list(queryWrapper);

        if (entityList.size() > 0) {
            // 执行任务
            entityList.forEach(entity -> QuartzHelper.executeTask(scheduler, entity));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] ids) {
        checkStartup();

        Arrays.stream(ids).forEach(id -> {
            if (updateById(ScheduleTaskEntity.builder()
                    .id(id).status(StatusEnum.DISABLE.getValue())
                    .build())) {
                // 暂停任务
                QuartzHelper.pauseTask(scheduler, id);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] ids) {
        checkStartup();

        Arrays.stream(ids).forEach(id -> {
            if (updateById(ScheduleTaskEntity.builder()
                    .id(id).status(StatusEnum.ENABLE.getValue())
                    .build())) {
                // 恢复任务
                QuartzHelper.resumeTask(scheduler, id);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void manualStart() {
        if (UserTypeEnum.SUPER_ADMIN.getValue() != SecurityUtil.getLoginUser().getUserType()) {
            // 只有超级管理员可以手动启动定时任务
            throw ValidationException.of(ErrorCode.MANUAL_START_ERROR);
        }

        try {
            if (scheduler.isStarted()) {
                // 不允许重复启动
                throw SystemException.of(ErrorCode.REPEAT_START_ERROR);
            }
            // 初始化定时任务
            initSchedule();
            // 启动定时任务
            scheduler.startDelayed(quartzStartupProperties.getDelay());
        } catch (SchedulerException e) {
            throw SystemException.of(e, ErrorCode.MANUAL_START_FAILURE, e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initSchedule() {
        // 全部定时任务
        List<ScheduleTaskEntity> entityList = list(addSelectFieldForQuartz());

        if (entityList.size() > 0) {
            entityList.forEach(entity -> {
                CronTrigger cronTrigger = QuartzHelper.getCronTrigger(scheduler, entity.getId());
                if (cronTrigger == null) {
                    // 新增任务
                    QuartzHelper.addTask(scheduler, entity);
                } else {
                    // 修改任务
                    QuartzHelper.updateTask(scheduler, entity);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * 检查定时任务是否已被启动
     */
    private void checkStartup() {
        try {
            if (!scheduler.isStarted()) {
                // 定时任务未启动
                throw SystemException.of(ErrorCode.NOT_STARTED_ERROR);
            }
        } catch (SchedulerException e) {
            throw SystemException.of(ErrorCode.CHECK_STARTUP_FAILURE);
        }
    }

    /**
     * 添加查询字段（Quartz需要的字段）
     *
     * @return {@link LambdaQueryWrapper}
     */
    private LambdaQueryWrapper<ScheduleTaskEntity> addSelectFieldForQuartz() {
        LambdaQueryWrapper<ScheduleTaskEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.select(ScheduleTaskEntity::getId, ScheduleTaskEntity::getBeanName, ScheduleTaskEntity::getJson,
                ScheduleTaskEntity::getCronExpression, ScheduleTaskEntity::getType, ScheduleTaskEntity::getStatus);

        return queryWrapper;
    }

}
