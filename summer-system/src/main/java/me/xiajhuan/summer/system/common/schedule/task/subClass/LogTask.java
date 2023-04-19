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

package me.xiajhuan.summer.system.common.schedule.task.subClass;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiajhuan.summer.system.common.schedule.task.SystemTask;
import me.xiajhuan.summer.system.log.service.LogErrorService;
import me.xiajhuan.summer.system.log.service.LogLoginService;
import me.xiajhuan.summer.system.log.service.LogOperationService;
import me.xiajhuan.summer.system.log.service.LogTaskService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 日志定时任务
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
@Setter
@Accessors(chain = true)
public class LogTask extends SystemTask {

    private LogOperationService logOperationService;

    private LogErrorService logErrorService;

    private LogLoginService logLoginService;

    private LogTaskService logTaskService;

    /**
     * 清理操作日志<br>
     * note：按标准时间计，每天 00:30 执行
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void clearOperationLog() {
        String methodSignature = "LogTask#clearOperationLog";

        if (acquireLock(methodSignature)) {
            TimeInterval timer = DateUtil.timer();
            LOGGER.info(startMsg(methodSignature));

            logOperationService.clear();

            LOGGER.info(endMsg(methodSignature, timer.interval()));
        }
    }

    /**
     * 清理错误日志<br>
     * note：按标准时间计，每天 01:00 执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearErrorLog() {
        String methodSignature = "LogTask#clearErrorLog";

        if (acquireLock(methodSignature)) {
            TimeInterval timer = DateUtil.timer();
            LOGGER.info(startMsg(methodSignature));

            logErrorService.clear();

            LOGGER.info(endMsg(methodSignature, timer.interval()));
        }
    }

    /**
     * 清理登录日志<br>
     * note：按标准时间计，每天 01:30 执行
     */
    @Scheduled(cron = "0 30 1 * * ?")
    public void clearLoginLog() {
        String methodSignature = "LogTask#clearLoginLog";

        if (acquireLock(methodSignature)) {
            TimeInterval timer = DateUtil.timer();
            LOGGER.info(startMsg(methodSignature));

            logLoginService.clear();

            LOGGER.info(endMsg(methodSignature, timer.interval()));
        }
    }

    /**
     * 清理任务日志<br>
     * note：按标准时间计，每天 02:00 执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void clearTaskLog() {
        String methodSignature = "LogTask#clearTaskLog";

        if (acquireLock(methodSignature)) {
            TimeInterval timer = DateUtil.timer();
            LOGGER.info(startMsg(methodSignature));

            logTaskService.clear();

            LOGGER.info(endMsg(methodSignature, timer.interval()));
        }
    }

}