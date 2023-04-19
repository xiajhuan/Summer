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

package me.xiajhuan.summer.system.common.quartz.helper;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.BusinessException;
import me.xiajhuan.summer.system.common.quartz.executor.TaskExecutor;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import org.quartz.*;

/**
 * Quartz Helper
 *
 * @author xiajhuan
 * @date 2023/4/19
 * @see Scheduler
 */
public class QuartzHelper {

    /**
     * 构造QuartzHelper（不允许实例化）
     */
    private QuartzHelper() {
    }

    /**
     * 任务ID Key
     */
    public static final String TASK_ID = "taskId";

    /**
     * Bean名称 Key
     */
    public static final String BEAN_NAME = "beanName";

    /**
     * 参数（Json格式） Key
     */
    public static final String JSON = "json";

    /**
     * 获取 {@link CronTrigger}
     *
     * @param scheduler {@link Scheduler}
     * @param taskId    任务ID
     * @return {@link CronTrigger}
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long taskId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(taskId));
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "getCronTrigger");
        }
    }

    /**
     * 新增任务
     *
     * @param scheduler {@link Scheduler}
     * @param entity    任务Entity
     */
    public static void addTask(Scheduler scheduler, ScheduleTaskEntity entity) {
        Long taskId = entity.getId();

        // 构建任务详情
        JobDetail jobDetail = JobBuilder.newJob(TaskExecutor.class)
                .withIdentity(getJobKey(taskId)).build();

        // Cron表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(entity.getCronExpression())
                .withMisfireHandlingInstructionFireAndProceed();

        // 根据Cron表达式构建一个新的Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(taskId))
                .withSchedule(scheduleBuilder).build();

        // 任务信息放入JobDataMap，用于运行时获取
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.putAsString(TASK_ID, taskId);
        jobDataMap.put(BEAN_NAME, entity.getBeanName());
        jobDataMap.put(JSON, entity.getJson());

        try {
            // 新增任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "createTask");
        }

        // 暂停任务
        if (StatusEnum.DISABLE.getValue() == entity.getStatus()) {
            pauseTask(scheduler, entity.getId());
        }
    }

    /**
     * 修改任务
     *
     * @param scheduler {@link Scheduler}
     * @param entity    任务Entity
     */
    public static void updateTask(Scheduler scheduler, ScheduleTaskEntity entity) {
        Long taskId = entity.getId();

        TriggerKey triggerKey = getTriggerKey(taskId);

        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(entity.getCronExpression())
                .withMisfireHandlingInstructionFireAndProceed();

        CronTrigger trigger = getCronTrigger(scheduler, taskId);

        // 根据Cron表达式重新构建一个Trigger
        trigger = trigger.getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();

        // 任务信息放入JobDataMap，用于运行时获取
        JobDataMap jobDataMap = trigger.getJobDataMap();
        jobDataMap.putAsString(TASK_ID, taskId);
        jobDataMap.put(BEAN_NAME, entity.getBeanName());
        jobDataMap.put(JSON, entity.getJson());

        try {
            // 修改任务
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "updateTask");
        }

        // 暂停任务
        if (StatusEnum.DISABLE.getValue() == entity.getStatus()) {
            pauseTask(scheduler, entity.getId());
        }
    }

    /**
     * 执行任务
     *
     * @param scheduler {@link Scheduler}
     * @param entity    任务Entity
     */
    public static void runTask(Scheduler scheduler, ScheduleTaskEntity entity) {
        Long taskId = entity.getId();

        // 任务信息放入JobDataMap，用于运行时获取
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString(TASK_ID, taskId);
        jobDataMap.put(BEAN_NAME, entity.getBeanName());
        jobDataMap.put(JSON, entity.getJson());

        try {
            // 执行任务
            scheduler.triggerJob(getJobKey(taskId), jobDataMap);
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "runTask");
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler {@link Scheduler}
     * @param taskId    任务ID
     */
    public static void pauseTask(Scheduler scheduler, Long taskId) {
        try {
            // 暂停任务
            scheduler.pauseJob(getJobKey(taskId));
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "pauseTask");
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler {@link Scheduler}
     * @param taskId    任务ID
     */
    public static void resumeJob(Scheduler scheduler, Long taskId) {
        try {
            // 恢复任务
            scheduler.resumeJob(getJobKey(taskId));
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "resumeJob");
        }
    }

    /**
     * 删除任务
     *
     * @param scheduler {@link Scheduler}
     * @param taskId    任务ID
     */
    public static void deleteJob(Scheduler scheduler, Long taskId) {
        try {
            // 删除任务
            scheduler.deleteJob(getJobKey(taskId));
        } catch (SchedulerException e) {
            throw BusinessException.of(e, ErrorCode.BUSINESS_TASK_ERROR, "deleteJob");
        }
    }

    /**
     * 获取 {@link TriggerKey}
     *
     * @param taskId 任务ID
     * @return {@link TriggerKey}
     */
    private static TriggerKey getTriggerKey(Long taskId) {
        return TriggerKey.triggerKey(getTaskKey(taskId));
    }

    /**
     * 获取 {@link JobKey}
     *
     * @param taskId 任务ID
     * @return {@link JobKey}
     */
    private static JobKey getJobKey(Long taskId) {
        return JobKey.jobKey(getTaskKey(taskId));
    }

    /**
     * 获取任务Key
     *
     * @param taskId 任务ID
     * @return 任务Key
     */
    private static String getTaskKey(Long taskId) {
        return StrUtil.format("TASK_{}", taskId);
    }

}
