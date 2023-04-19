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

package me.xiajhuan.summer.core.runner;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.utils.SystemUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（服务启动完毕）
 *
 * @author xiajhuan
 * @date 2022/11/28
 * @see ApplicationRunner
 */
@Component
@Order
public class StartedUpRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private ConfigurableApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            LOGGER.info("  _   _   _   _   _   _   _   _");
            LOGGER.info(" / \\ / \\ / \\ / \\ / \\ / \\ / \\ / \\");
            LOGGER.info("( c | o | m | p | l | e | t | e )");
            LOGGER.info(" \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/");
            LOGGER.info("【{}】启动完毕，时间【{}】", SystemUtil.getApplicationName(), DateUtil.date());
        }
    }

}
