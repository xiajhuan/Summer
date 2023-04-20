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

package me.xiajhuan.summer.admin.task.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import me.xiajhuan.summer.system.common.quartz.task.ApiTask;
import org.springframework.stereotype.Component;

/**
 * Demo api定时任务
 *
 * @author xiajhuan
 * @date 2023/4/20
 */
@Component("apiDemo")
public class DemoTask extends ApiTask {

    @Override
    protected void run(String json) {
        String name = "Demo";

        TimeInterval timer = DateUtil.timer();
        LOGGER.info(startMsg(name));

        LOGGER.info("xxx接口调用中...");

        LOGGER.info(endMsg(name, timer.interval()));
    }

}
