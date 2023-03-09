package me.xiajhuan.summer.common.schedule.config;

import me.xiajhuan.summer.common.constant.ThreadPoolConst;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 定时任务线程池配置
 *
 * @author xiajhuan
 * @date 2022/11/22
 * @see SchedulingConfigurer
 */
@Configuration
@EnableScheduling
public class ScheduleThreadPoolConfig implements SchedulingConfigurer {

    @Resource(name = ThreadPoolConst.SCHEDULE_JOB_POOL)
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(scheduledExecutorService);
    }

}
