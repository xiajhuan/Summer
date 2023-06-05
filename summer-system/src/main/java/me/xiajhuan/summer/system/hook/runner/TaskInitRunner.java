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

package me.xiajhuan.summer.system.hook.runner;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.utils.SystemUtil;
import me.xiajhuan.summer.system.schedule.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（定时任务初始化完毕）
 *
 * @author xiajhuan
 * @date 2023/4/20
 * @see ApplicationRunner
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class TaskInitRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Value("${quartz-startup.auto}")
    private boolean auto;

    @Resource
    private ScheduleTaskService scheduleTaskService;

    @Override
    public void run(ApplicationArguments args) {
        if (auto && scheduleTaskService.initTask()) {
            LOGGER.info("【{}】定时任务初始化完毕", SystemUtil.getApplicationName());
        }
    }

}
