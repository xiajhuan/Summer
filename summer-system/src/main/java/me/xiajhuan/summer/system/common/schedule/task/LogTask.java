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

package me.xiajhuan.summer.system.common.schedule.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiajhuan.summer.system.log.service.LogErrorService;
import me.xiajhuan.summer.system.log.service.LogLoginService;
import me.xiajhuan.summer.system.log.service.LogOperationService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 日志定时任务
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
@Setter
@Accessors(chain = true)
public class LogTask {

    private static final Log LOGGER = LogFactory.get();

    private LogOperationService logOperationService;

    private LogErrorService logErrorService;

    private LogLoginService logLoginService;

    /**
     * 清理操作日志<br>
     * note：按标准时间计，每天 00:30 执行
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void clearOperationLog() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【LogTask】【clearOperationLog】开始执行：{}", DateUtil.date());

        logOperationService.clear();

        LOGGER.info("【LogTask】【clearOperationLog】执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

    /**
     * 清理错误日志<br>
     * note：按标准时间计，每天 01:00 执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearErrorLog() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【LogTask】【clearErrorLog】开始执行：{}", DateUtil.date());

        logErrorService.clear();

        LOGGER.info("【LogTask】【clearErrorLog】执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

    /**
     * 清理登录日志<br>
     * note：按标准时间计，每天 01:30 执行
     */
    @Scheduled(cron = "0 30 1 * * ?")
    public void clearLoginLog() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【LogTask】【clearLoginLog】开始执行：{}", DateUtil.date());

        logLoginService.clear();

        LOGGER.info("【LogTask】【clearLoginLog】执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

}