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

package me.xiajhuan.summer.core.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author xiajhuan
 * @date 2022/11/25
 * @see ThreadPoolTaskExecutor
 */
@Configuration
public class ThreadPoolConfig {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingConst.CORE)
    private Setting setting;

    /**
     * 注册Spring异步任务线程池
     *
     * @return {@link ThreadPoolTaskExecutor}
     */
    @Bean(ThreadPoolConst.ASYNC)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(setting.getInt("async.core-pool-size", "Thread", 8));
        executor.setAllowCoreThreadTimeOut(setting.getBool("async.allow-core-thread-timeout", "Thread", false));
        executor.setMaxPoolSize(setting.getInt("async.max-pool-size", "Thread", 32));
        executor.setQueueCapacity(setting.getInt("async.queue-capacity", "Thread", 512));
        executor.setKeepAliveSeconds(setting.getInt("async.keep-alive-seconds", "Thread", 30));
        executor.setThreadNamePrefix(ThreadPoolConst.ASYNC_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(setting.getBool("async.wait-for-tasks-to-complete-on-shutdown", "Thread", true));
        executor.setAwaitTerminationSeconds(setting.getInt("async.await-termination-Seconds", "Thread", 30));
        // 拒绝策略：由调用的线程处理任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 注册Quartz定时任务线程池
     *
     * @return {@link SimpleThreadPoolTaskExecutor}
     */
    @Bean(ThreadPoolConst.SCHEDULE)
    public SimpleThreadPoolTaskExecutor simpleThreadPoolTaskExecutor() {
        SimpleThreadPoolTaskExecutor executor = new SimpleThreadPoolTaskExecutor();
        executor.setInstanceId("AUTO");
        executor.setInstanceName("SummerScheduler");
        executor.setThreadCount(setting.getInt("schedule.thread-count", "Thread", 16));
        executor.setThreadPriority(setting.getInt("schedule.thread-priority", "Thread", 5));
        executor.setThreadNamePrefix(ThreadPoolConst.SCHEDULE_PREFIX);
        executor.setWaitForJobsToCompleteOnShutdown(setting.getBool("schedule.wait-for-jobs-to-complete-on-shutdown", "Thread", true));
        return executor;
    }

    /**
     * Spring异步任务异常配置
     *
     * @see AsyncConfigurer
     */
    @Configuration
    public static class AsyncExceptionConfig implements AsyncConfigurer {

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return new CustomAsyncExceptionHandler();
        }

    }

    /**
     * 自定义异步任务异常处理
     *
     * @see AsyncUncaughtExceptionHandler
     */
    public static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable e, Method method, Object... param) {
            LOGGER.error(e, "异步任务【{}】执行异常, 参数【{}】", StrUtil.format("{}.{}",
                    method.getDeclaringClass().getSimpleName(), method.getName()), ArrayUtil.toString(param));
        }

    }

}
