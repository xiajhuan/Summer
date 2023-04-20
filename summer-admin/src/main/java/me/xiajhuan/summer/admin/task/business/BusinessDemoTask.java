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

package me.xiajhuan.summer.admin.task.business;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.system.schedule.quartz.task.QuartzTask;
import org.springframework.stereotype.Component;

/**
 * BusinessDemo
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
@Component
public class BusinessDemoTask implements QuartzTask {

    private static final Log LOGGER = LogFactory.get();

    @Override
    public void execute(String json) {
        LOGGER.info("任务参数：{}", json);
        LOGGER.info("xxx业务处理中...");
    }

}
