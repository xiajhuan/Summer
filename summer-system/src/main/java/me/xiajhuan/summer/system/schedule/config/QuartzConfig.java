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

package me.xiajhuan.summer.system.schedule.config;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.dialect.Props;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import me.xiajhuan.summer.core.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Value;
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
 * @see DynamicRoutingDataSource
 * @see SchedulerFactoryBean
 * @see Props
 */
@Configuration
public class QuartzConfig {

    @Value("${quartz-startup.auto}")
    private boolean isAuto;

    @Value("${quartz-startup.delay}")
    private int delay;

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

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
     * note：{0}会被”表前缀“替换
     */
    private static final String LOCK_SQL = "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE";

    /**
     * 实例名称<br>
     * note：集群模式下每个实例必须使用相同名称
     */
    private static final String INSTANCE_NAME = "Business";

    /**
     * 注册{@link SchedulerFactoryBean}
     *
     * @return {@link SchedulerFactoryBean}
     */
    @Bean
    public SchedulerFactoryBean SchedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        // 数据源
        factory.setDataSource(dynamicRoutingDataSource.getDataSource(DataSourceConst.SYSTEM));

        // 自定义Quartz属性配置
        factory.setQuartzProperties(buildCustomProps());

        // 应用关闭时是否等待所有任务执行完
        factory.setWaitForJobsToCompleteOnShutdown(
                setting.getBool("wait-for-jobs-to-complete-on-shutdown", "Schedule", true));

        // 将Spring的applicationContext加入到Quartz的schedulerContext中
        factory.setApplicationContextSchedulerContextKey("springContext");

        // 启动时更新己存在Task，这样就不用每次修改schedule_task表记录后删除QUARTZ_JOB_DETAILS表对应记录了
        factory.setOverwriteExistingJobs(true);

        if (isAuto) {
            // 自动启动
            factory.setAutoStartup(true);

            // 延迟启动（s）
            factory.setStartupDelay(delay);
        } else {
            // 不自动启动
            factory.setAutoStartup(false);
        }

        return factory;
    }

    /**
     * 构建自定义属性配置
     *
     * @return {@link Props}
     */
    private Props buildCustomProps() {
        Props props = Props.create();

        props.setProperty("org.quartz.scheduler.instanceName", INSTANCE_NAME);
        props.setProperty("org.quartz.scheduler.instanceId", getInstanceId());

        // 线程池
        props.setProperty("org.quartz.threadPool.class", THREAD_POOL_CLASS);
        props.setProperty("org.quartz.threadPool.threadCount",
                setting.getInt("thread-count", "Schedule", 16));
        props.setProperty("org.quartz.threadPool.threadPriority",
                setting.getInt("thread-priority", "Schedule", 5));
        props.setProperty("org.quartz.threadPool.threadNamePrefix", ThreadPoolConst.SCHEDULE_QUARTZ_PREFIX);

        // 持久化
        props.setProperty("org.quartz.jobStore.class", PERSISTENCE_CLASS);
        props.setProperty("org.quartz.jobStore.tablePrefix", TABLE_PREFIX);
        props.setProperty("org.quartz.jobStore.selectWithLockSQL", LOCK_SQL);

        // “错过触发”处理
        props.setProperty("org.quartz.jobStore.misfireThreshold",
                setting.getLong("misfire-threshold", "Schedule", 15000L));
        props.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime",
                setting.getInt("max-misfires-to-handle-at-time", "Schedule", 1));

        boolean isCluster = setting.getBool("is-cluster", "Schedule", true);
        if (isCluster) {
            // 集群
            props.setProperty("org.quartz.jobStore.isClustered", true);
            props.setProperty("org.quartz.jobStore.clusterCheckinInterval",
                    setting.getLong("cluster-checkin-interval", "Schedule", 20000L));
            int acquisitionMaxCount = setting.getInt("batch-trigger-acquisition-max-count", "Schedule", 1);
            if (acquisitionMaxCount > 1) {
                props.setProperty("org.quartz.scheduler.batchTriggerAcquisitionMaxCount", acquisitionMaxCount);
                // 避免多个Trigger产生混乱
                props.setProperty("org.quartz.jobStore.acquireTriggersWithinLock", true);
            }
        }

        return props;
    }

    /**
     * 获取实例ID<br>
     * note：集群模式下每个实例ID必须唯一
     *
     * @return 实例ID
     */
    private String getInstanceId() {
        String ip = SystemUtil.getIp();
        String port = SystemUtil.getPort();
        if (ip != null && port != null) {
            return StrUtil.subAfter(ip, StrPool.DOT, true) + StrPool.COLON + port;
        } else {
            return UUID.fastUUID().toString(true);
        }
    }

}
