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
import me.xiajhuan.summer.core.constant.CacheConst;
import me.xiajhuan.summer.core.utils.SystemUtil;
import me.xiajhuan.summer.system.monitor.service.MonitorOnlineService;
import me.xiajhuan.summer.system.security.service.SecurityDeptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（缓存服务加载完毕）
 *
 * @author xiajhuan
 * @date 2023/4/20
 * @see ApplicationRunner
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class CacheReadyRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Value("${application.cache.type}")
    private String cacheType;

    @Resource
    private SecurityDeptService securityDeptService;

    @Resource
    private MonitorOnlineService monitorOnlineService;

    /**
     * 应用名称
     */
    private final String applicationName = SystemUtil.getApplicationName();

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("【{}】缓存服务加载完毕，类型【{}】", applicationName, cacheType);

        // 缓存类型为HEAP时清理遗留在线用户
        if (CacheConst.Type.HEAP.equalsIgnoreCase(cacheType)
                && monitorOnlineService.remove(null)) {
            LOGGER.info("【{}】清理遗留在线用户成功", applicationName);
        }

        // 缓存数据
        cacheData();
    }

    /**
     * 缓存数据
     */
    private void cacheData() {
        // 缓存部门数据
        if (securityDeptService.cacheAll()) {
            LOGGER.info("【{}】缓存部门数据成功", applicationName);
        }
    }

}
