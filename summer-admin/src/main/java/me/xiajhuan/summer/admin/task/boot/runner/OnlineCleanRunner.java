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

package me.xiajhuan.summer.admin.task.boot.runner;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.constant.CacheConst;
import me.xiajhuan.summer.core.properties.ServerCacheProperties;
import me.xiajhuan.summer.system.monitor.service.MonitorOnlineService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（在线用户清理）
 *
 * @author xiajhuan
 * @date 2023/4/12
 * @see ApplicationRunner
 */
@Component
public class OnlineCleanRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private ServerCacheProperties serverCacheProperties;

    @Resource
    private MonitorOnlineService monitorOnlineService;

    @Override
    public void run(ApplicationArguments args) {
        if (CacheConst.Type.HEAP.equalsIgnoreCase(serverCacheProperties.getType())) {
            // 如果缓存类型为“HEAP”，则清理应用启动前遗留的在线用户
            if (monitorOnlineService.remove(null)) {
                LOGGER.info("清理遗留在线用户成功");
            }
        }
    }

}
