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

package me.xiajhuan.summer.core.boot.runner;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import me.xiajhuan.summer.core.constant.CacheConst;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.properties.ServerCacheProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（在线用户清理）
 *
 * @author xiajhuan
 * @date 2023/4/12
 * @see ApplicationRunner
 * @see DynamicDataSourceContextHolder
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class OnlineCleanRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private ServerCacheProperties serverCacheProperties;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        if (CacheConst.Type.HEAP.equalsIgnoreCase(serverCacheProperties.getType())) {
            // 如果缓存类型为“HEAP”，则清理应用启动前遗留的在线用户
            // 设置当前线程的数据源为“system”
            DynamicDataSourceContextHolder.push(DataSourceConst.SYSTEM);
            if (SqlHelper.retBool(jdbcTemplate.update("DELETE FROM monitor_online"))) {
                LOGGER.info("清理遗留在线用户成功");
            }
            // 强制清空本地线程，防止内存泄漏
            DynamicDataSourceContextHolder.clear();
        }
    }

}
