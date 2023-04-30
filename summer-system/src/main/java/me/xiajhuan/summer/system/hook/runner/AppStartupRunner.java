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
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import me.xiajhuan.summer.core.utils.SystemUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（应用启动完毕）
 *
 * @author xiajhuan
 * @date 2022/11/28
 * @see ApplicationRunner
 * @see DynamicDataSourceContextHolder
 */
@Component
@Order
public class AppStartupRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private ConfigurableApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            LOGGER.info("  _   _   _   _   _");
            LOGGER.info(" / \\ / \\ / \\ / \\ / \\");
            LOGGER.info("( R | E | A | D | Y )");
            LOGGER.info(" \\_/ \\_/ \\_/ \\_/ \\_/");
            LOGGER.info("【{}】应用启动完毕", SystemUtil.getApplicationName());
        }

        // 强制清空本地线程，防止内存泄漏
        DynamicDataSourceContextHolder.clear();
    }

}
