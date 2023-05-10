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

package me.xiajhuan.summer.system.schedule.executor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.TaskTypeEnum;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;
import me.xiajhuan.summer.system.schedule.task.QuartzTask;
import me.xiajhuan.summer.system.log.entity.LogTaskEntity;
import me.xiajhuan.summer.system.log.service.LogTaskService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import static me.xiajhuan.summer.system.schedule.helper.QuartzHelper.*;

/**
 * Quartz任务执行器
 *
 * @author xiajhuan
 * @date 2023/4/19
 * @see QuartzJobBean#executeInternal(JobExecutionContext)
 */
public class QuartzTaskExecutor extends QuartzJobBean {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 开始消息格式
     */
    private static final String START_MSG_FORMAT = "【{}】【{}】开始执行...";

    /**
     * 结束消息格式
     */
    private static final String END_MSG_FORMAT = "【{}】【{}】执行{}，耗时【{}】ms";

    /**
     * 定时任务日志 Service
     */
    private final LogTaskService logTaskService = SpringUtil.getBean("logTaskServiceImpl", LogTaskService.class);

    /**
     * 异常堆栈最大长度
     */
    private final int stacktraceMaxLength = SpringUtil.getBean(SettingConst.SYSTEM, Setting.class)
            .getInt("task.stacktrace-length", "Log", 65535);

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        // 任务信息
        String beanName = jobDataMap.getString(BEAN_NAME);
        String className = StrUtil.upperFirst(beanName);
        String json = jobDataMap.getString(JSON);
        int type = jobDataMap.getInt(TYPE);

        // 构建定时任务日志
        LogTaskEntity entity = LogTaskEntity.builder()
                .taskId(jobDataMap.getLong(TASK_ID))
                .beanName(beanName).json(json)
                .type(type).build();

        TimeInterval timer = DateUtil.timer();
        LOGGER.info(startMsg(type, className));
        try {
            // 执行任务
            ReflectUtil.invoke(SpringUtil.getBean(beanName, QuartzTask.class), "execute", json);

            // 执行成功
            long cost = timer.interval();
            LOGGER.info(endMsg(type, className, OperationStatusEnum.SUCCESS.getName(), cost));

            entity.setTaskTime((int) cost);
            entity.setStatus(OperationStatusEnum.SUCCESS.getValue());
        } catch (RuntimeException e) {
            // 执行失败
            long cost = timer.interval();
            LOGGER.info(endMsg(type, className, OperationStatusEnum.FAIL.getName(), cost));

            entity.setTaskTime((int) cost);
            entity.setStatus(OperationStatusEnum.FAIL.getValue());
            entity.setErrorInfo(ExceptionUtil.stacktraceToString(e, stacktraceMaxLength));
        } finally {
            entity.setCreateTime(DateUtil.date());
            // 异步保存定时任务日志
            logTaskService.saveAsync(entity);
        }
    }

    /**
     * 开始消息
     *
     * @param type      类型，参考{@link TaskTypeEnum}
     * @param className 类名称
     * @return 开始消息
     */
    private String startMsg(int type, String className) {
        switch (type) {
            case 0:
                return StrUtil.format(START_MSG_FORMAT, TaskTypeEnum.SYSTEM.getName(), className);
            case 1:
                return StrUtil.format(START_MSG_FORMAT, TaskTypeEnum.API.getName(), className);
            case 2:
                return StrUtil.format(START_MSG_FORMAT, TaskTypeEnum.BUSINESS.getName(), className);
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的定时任务类型【{}】", type));
        }
    }

    /**
     * 结束消息
     *
     * @param type      类型，参考{@link TaskTypeEnum}
     * @param className 类名称
     * @param result    结果，参考{@link OperationStatusEnum}
     * @param cost      耗时（ms）
     * @return 结束消息
     */
    private String endMsg(int type, String className, String result, long cost) {
        switch (type) {
            case 0:
                return StrUtil.format(END_MSG_FORMAT,
                        TaskTypeEnum.SYSTEM.getName(), className, result, cost);
            case 1:
                return StrUtil.format(END_MSG_FORMAT,
                        TaskTypeEnum.API.getName(), className, result, cost);
            default:
                return StrUtil.format(END_MSG_FORMAT,
                        TaskTypeEnum.BUSINESS.getName(), className, result, cost);
        }
    }

}
