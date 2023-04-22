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

package me.xiajhuan.summer.system.schedule.quartz.helper;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.system.schedule.quartz.executor.QuartzTaskExecutor;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import org.quartz.*;

/**
 * Quartz Helper
 *
 * @author xiajhuan
 * @date 2023/4/19
 * @see CronTrigger
 * @see Scheduler
 * @see JobDetail
 * @see CronScheduleBuilder
 * @see JobDataMap
 * @see JobKey
 * @see TriggerKey
 */
public class QuartzHelper {

    /**
     * 构造QuartzHelper（不允许实例化）
     */
    private QuartzHelper() {
    }

    /**
     * 任务ID
     */
    public static final String TASK_ID = "taskId";

    /**
     * Bean名称
     */
    public static final String BEAN_NAME = "beanName";

    /**
     * 参数（Json格式）
     */
    public static final String JSON = "json";

    /**
     * 类型
     */
    public static final String TYPE = "type";

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
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "getCronTrigger");
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
        JobDetail jobDetail = JobBuilder.newJob(QuartzTaskExecutor.class)
                .withIdentity(getJobKey(taskId)).build();

        // 根据Cron表达式构建一个新的Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(taskId))
                .withSchedule(getScheduleBuilder(entity.getCronExpression()))
                .build();

        // 任务信息放入JobDataMap，用于运行时获取
        fillDataMap(jobDetail.getJobDataMap(), taskId, entity.getBeanName(), entity.getJson(), entity.getType());

        try {
            // 新增任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "addTask");
        }

        // 暂停任务
        if (StatusEnum.DISABLE.getValue() == entity.getStatus()) {
            pauseTask(scheduler, taskId);
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

        // 根据Cron表达式重新构建一个Trigger
        CronTrigger trigger = getCronTrigger(scheduler, taskId)
                .getTriggerBuilder()
                .withIdentity(triggerKey)
                .withSchedule(getScheduleBuilder(entity.getCronExpression()))
                .build();

        // 任务信息放入JobDataMap，用于运行时获取
        fillDataMap(trigger.getJobDataMap(), taskId, entity.getBeanName(), entity.getJson(), entity.getType());

        try {
            // 修改任务
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "updateTask");
        }

        // 暂停任务
        if (StatusEnum.DISABLE.getValue() == entity.getStatus()) {
            pauseTask(scheduler, taskId);
        }
    }

    /**
     * 删除任务
     *
     * @param scheduler {@link Scheduler}
     * @param taskId    任务ID
     */
    public static void deleteTask(Scheduler scheduler, Long taskId) {
        try {
            // 删除任务
            scheduler.deleteJob(getJobKey(taskId));
        } catch (SchedulerException e) {
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "deleteTask");
        }
    }

    /**
     * 执行任务
     *
     * @param scheduler {@link Scheduler}
     * @param entity    任务Entity
     */
    public static void executeTask(Scheduler scheduler, ScheduleTaskEntity entity) {
        Long taskId = entity.getId();

        // 任务信息放入JobDataMap，用于运行时获取
        JobDataMap jobDataMap = new JobDataMap();
        fillDataMap(jobDataMap, taskId, entity.getBeanName(), entity.getJson(), entity.getType());

        try {
            // 执行任务
            scheduler.triggerJob(getJobKey(taskId), jobDataMap);
        } catch (SchedulerException e) {
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "executeTask");
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
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "pauseTask");
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler {@link Scheduler}
     * @param taskId    任务ID
     */
    public static void resumeTask(Scheduler scheduler, Long taskId) {
        try {
            // 恢复任务
            scheduler.resumeJob(getJobKey(taskId));
        } catch (SchedulerException e) {
            throw SystemException.of(e, ErrorCode.SCHEDULE_ERROR, "resumeTask");
        }
    }

    /**
     * 获取表达式调度构建器
     *
     * @param cronExpression Cron表达式
     * @return {@link CronScheduleBuilder}
     * @see CronTrigger#MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
     */
    private static CronScheduleBuilder getScheduleBuilder(String cronExpression) {
        return CronScheduleBuilder.cronSchedule(cronExpression)
                .withMisfireHandlingInstructionFireAndProceed();
    }

    /**
     * 填充 {@link JobDataMap}
     *
     * @param jobDataMap {@link JobDataMap}
     * @param taskId     任务ID
     * @param beanName   Bean名称
     * @param json       参数（Json格式）
     * @param type       类型
     */
    private static void fillDataMap(JobDataMap jobDataMap, Long taskId, String beanName, String json, Integer type) {
        jobDataMap.put(TASK_ID, taskId);
        jobDataMap.put(BEAN_NAME, beanName);
        jobDataMap.put(JSON, json);
        jobDataMap.put(TYPE, type);
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
     * 获取 {@link TriggerKey}
     *
     * @param taskId 任务ID
     * @return {@link TriggerKey}
     */
    private static TriggerKey getTriggerKey(Long taskId) {
        return TriggerKey.triggerKey(getTaskKey(taskId));
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
