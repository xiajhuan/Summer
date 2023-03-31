/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.config;

import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author xiajhuan
 * @date 2022/11/25
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 注册通用异步任务线程池
     *
     * @return {@link ThreadPoolTaskExecutor}
     */
    @Bean(ThreadPoolConst.Async.COMMON)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(@Qualifier(SettingConst.CORE) Setting setting) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(setting.getInt("core-pool-size", "Async", 8));
        executor.setAllowCoreThreadTimeOut(setting.getBool("allow-core-thread-timeout", "Async", false));
        executor.setMaxPoolSize(setting.getInt("max-pool-size", "Async", 32));
        executor.setQueueCapacity(setting.getInt("queue-capacity", "Async", 512));
        executor.setKeepAliveSeconds(setting.getInt("keep-alive-seconds", "Async", 30));
        executor.setThreadNamePrefix(ThreadPoolConst.Async.COMMON_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(setting.getBool("wait-for-tasks-to-complete-on-shutdown", "Async", true));
        executor.setAwaitTerminationSeconds(setting.getInt("await-termination-Seconds", "Async", 30));
        // 拒绝策略：由调用的线程处理任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
