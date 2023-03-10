package me.xiajhuan.summer.common.config;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.constant.ThreadPoolConst;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
     * 注册异步任务线程池
     *
     * @return {@link ThreadPoolTaskExecutor}
     */
    @Bean(ThreadPoolConst.ASYNC_TASK_POOL)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix(ThreadPoolConst.ASYNC_TASK_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 注册定时任务线程池
     *
     * @return {@link ScheduledExecutorService}
     */
    @Bean(ThreadPoolConst.SCHEDULE_JOB_POOL)
    public ScheduledExecutorService scheduledExecutorService(@Qualifier(SettingBeanConst.COMMON) Setting setting) {
        return Executors.newScheduledThreadPool(setting.getInt("pool.core-size", "Schedule", 1),
                ThreadUtil.newNamedThreadFactory(ThreadPoolConst.SCHEDULE_JOB_PREFIX, false));
    }

}
