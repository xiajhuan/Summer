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

package me.xiajhuan.summer.system.common.quartz.config;

import cn.hutool.setting.Setting;
import cn.hutool.setting.dialect.Props;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.quartz.simpl.SimpleThreadPool;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import javax.annotation.Resource;

/**
 * Quartz配置
 *
 * @author xiajhuan
 * @date 2023/4/17
 * @see SchedulerFactoryBean
 * @see DynamicRoutingDataSource
 */
@Configuration
public class QuartzConfig {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 实例名称<br>
     * note：集群模式下每个实例必须使用相同的名称
     */
    private static final String INSTANCE_NAME = "SummerQuartzScheduler";

    /**
     * 实例ID<br>
     * note：集群模式下每个实例ID必须唯一，“AUTO”表示自动生成
     */
    private static final String INSTANCE_ID = "AUTO";

    /**
     * 线程池Class
     *
     * @see SimpleThreadPool
     */
    private static final String THREAD_POOL_CLASS = "org.quartz.simpl.SimpleThreadPool";

    /**
     * 持久化Class
     *
     * @see LocalDataSourceJobStore
     */
    private static final String PERSISTENCE_CLASS = "org.springframework.scheduling.quartz.LocalDataSourceJobStore";

    /**
     * 表前缀
     */
    private static final String TABLE_PREFIX = "QUARTZ_";

    /**
     * 加锁Sql<br>
     * note：{0}会被 {@link TABLE_PREFIX} 替换
     */
    private static final String LOCK_SQL = "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE";

    /**
     * 注册 {@link SchedulerFactoryBean}
     *
     * @return {@link SchedulerFactoryBean}
     */
    @Bean
    public SchedulerFactoryBean SchedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        // 数据源为“system”
        factory.setDataSource(dynamicRoutingDataSource.getDataSource(DataSourceConst.SYSTEM));

        // 自定义Quartz配置
        factory.setQuartzProperties(buildCustomQuartzProps());

        factory.setSchedulerName(INSTANCE_NAME);
        // 自动启动
        factory.setAutoStartup(true);
        // 延时启动（s）
        factory.setStartupDelay(20);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 启动时更新己存在的Task，这样就不用每次修改targetObject后删除QUARTZ_JOB_DETAILS表对应的记录了
        factory.setOverwriteExistingJobs(true);

        return factory;
    }

    /**
     * 构建自定义Quartz配置
     *
     * @return {@link Props}
     */
    private Props buildCustomQuartzProps() {
        Props props = Props.create();

        props.setProperty("org.quartz.scheduler.threadName", INSTANCE_NAME);

        props.setProperty("org.quartz.scheduler.instanceName", INSTANCE_NAME);
        props.setProperty("org.quartz.scheduler.instanceId", INSTANCE_ID);

        // 线程池
        props.setProperty("org.quartz.threadPool.class", THREAD_POOL_CLASS);
        props.setProperty("org.quartz.threadPool.threadCount",
                setting.getInt("quartz.schedule-thread-count", "Schedule", 16));
        props.setProperty("org.quartz.threadPool.threadPriority",
                setting.getInt("quartz.schedule-thread-priority", "Schedule", 5));
        props.setProperty("org.quartz.threadPool.threadNamePrefix", ThreadPoolConst.SCHEDULE_QUARTZ_PREFIX);

        // 持久化
        props.setProperty("org.quartz.jobStore.class", PERSISTENCE_CLASS);
        props.setProperty("org.quartz.jobStore.tablePrefix", TABLE_PREFIX);
        props.setProperty("org.quartz.jobStore.selectWithLockSQL", LOCK_SQL);

        // “错过触发”处理
        props.setProperty("org.quartz.jobStore.misfireThreshold",
                setting.getLong("quartz.misfire-threshold", "Schedule", 15000L));
        props.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime",
                setting.getInt("quartz.max-misfires-to-handle-at-time", "Schedule", 3));

        boolean isCluster = setting.getBool("quartz.is-cluster", "Schedule", true);
        if (isCluster) {
            // 集群
            props.setProperty("org.quartz.jobStore.isClustered", true);
            props.setProperty("org.quartz.jobStore.clusterCheckinInterval",
                    setting.getLong("quartz.cluster-checkin-interval", "Schedule", 20000L));
            int acquisitionMaxCount = setting.getInt("quartz.batch-trigger-acquisition-max-count", "Schedule", 1);
            if (acquisitionMaxCount > 1) {
                props.setProperty("org.quartz.scheduler.batchTriggerAcquisitionMaxCount", acquisitionMaxCount);
                // 避免多个Trigger产生混乱
                props.setProperty("org.quartz.jobStore.acquireTriggersWithinLock", true);
            }
        }

        return props;
    }

}
