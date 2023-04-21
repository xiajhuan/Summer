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

package me.xiajhuan.summer.system.hook.listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.utils.SystemUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ApplicationListener（应用关闭）
 *
 * @author xiajhuan
 * @date 2023/4/21
 * @see ApplicationListener
 * @see ContextClosedEvent
 */
@Component
@Order
public class AppShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private static final Log LOGGER = LogFactory.get();

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("  _   _   _   _   _   _   _");
        LOGGER.info(" / \\ / \\ / \\ / \\ / \\ / \\ / \\");
        LOGGER.info("( G | O | O | D | B | Y | E )");
        LOGGER.info(" \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/");
        LOGGER.info("【{}】应用关闭中...", SystemUtil.getApplicationName());
    }

}
