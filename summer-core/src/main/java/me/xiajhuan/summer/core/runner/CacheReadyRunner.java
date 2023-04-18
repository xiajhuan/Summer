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

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.properties.ServerCacheProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（缓存设施加载完毕）
 *
 * @author xiajhuan
 * @date 2022/11/28
 * @see ApplicationRunner
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class CacheReadyRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private ServerCacheProperties serverCacheProperties;

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("【{}】缓存设施加载完毕，缓存类型【{}】", applicationName, serverCacheProperties.getType());
    }

}
