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

package me.xiajhuan.summer.admin.common.schedule.base.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiajhuan.summer.admin.common.log.service.LogErrorService;
import me.xiajhuan.summer.admin.common.log.service.LogLoginService;
import me.xiajhuan.summer.admin.common.log.service.LogOperationService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 通用模块基本定时任务
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
@Setter
@Accessors(chain = true)
public class CommonTaskJob {

    private static final Log LOGGER = LogFactory.get();

    private LogOperationService logOperationService;

    private LogErrorService logErrorService;

    private LogLoginService logLoginService;

    /**
     * 清理操作日志/错误日志/登录日志<br>
     * note：按标准时间计，每天1：00执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearLog() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【CommonTaskJob】【clearLog】Job开始执行：{}", DateUtil.date());

        logOperationService.clearLog();
        logErrorService.clearLog();
        logLoginService.clearLog();

        LOGGER.info("【CommonTaskJob】【clearLog】Job执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

}
