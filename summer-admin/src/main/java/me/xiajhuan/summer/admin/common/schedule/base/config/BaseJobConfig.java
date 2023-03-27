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

package me.xiajhuan.summer.admin.common.schedule.base.config;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.admin.common.schedule.base.job.BusinessJob;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.admin.common.log.service.LogErrorService;
import me.xiajhuan.summer.admin.common.log.service.LogLoginService;
import me.xiajhuan.summer.admin.common.log.service.LogOperationService;
import me.xiajhuan.summer.admin.common.schedule.base.job.CommonJob;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 基本定时任务配置
 * <pre>
 *  1.该定时任务无法提供对外暴露的接口，故不支持线上动态调整
 *  2.主要初衷是为一些基本的定时调度需求提供轻量级实现，例如：
 *      2.1 随机启动时缓存基本数据
 *      2.2 定期清理日志
 *      2.3 固定的业务批处理
 *  3.可通过common.setting下Schedule组进行配置
 *  4.如需线上动态调整并记录详细执行日志，请使用quartz包下基于quartz的实现
 * </pre>
 *
 * @author xiajhuan
 * @date 2022/11/24
 * @see EnableScheduling
 * @see SchedulingConfigurer
 * @see ScheduledExecutorService
 * @see ScheduledTaskRegistrar
 */
@Configuration
@EnableScheduling
public class BaseJobConfig implements SchedulingConfigurer {

    private static final Log LOGGER = LogFactory.get();

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    /**
     * 是否开启基本定时任务
     */
    private boolean enableBaseSchedule;

    /**
     * 初始化 {@link enableBaseSchedule}
     */
    @PostConstruct
    private void init() {
        enableBaseSchedule = setting.getBool("enable-base", "Schedule", false);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // note：这里只是设置自己的定时任务线程池，为null时如果有定时任务会使用Spring默认的线程池执行
        taskRegistrar.setScheduler(buildScheduledExecutorService());
    }

    /**
     * 构建定时任务线程池
     *
     * @return {@link ScheduledExecutorService}
     */
    private ScheduledExecutorService buildScheduledExecutorService() {
        if (enableBaseSchedule) {
            return Executors.newScheduledThreadPool(setting.getInt("base.core-pool-size", "Schedule", 2),
                    ThreadUtil.newNamedThreadFactory(ThreadPoolConst.Schedule.BASE_PREFIX, false));
        }
        return null;
    }

    //*******************CommonJob********************

    @Resource
    private LogOperationService logOperationService;

    @Resource
    private LogErrorService logErrorService;

    @Resource
    private LogLoginService logLoginService;

    /**
     * 注册通用模块基本定时任务
     *
     * @return 通用模块基本定时任务
     */
    @Bean
    public CommonJob commonJob() {
        if (enableBaseSchedule && setting.getBool("base.common", "Schedule", true)) {
            LOGGER.info("【{}】基本定时任务【CommonJob】装载完毕", applicationName);
            return new CommonJob().setLogOperationService(logOperationService)
                    .setLogErrorService(logErrorService)
                    .setLogLoginService(logLoginService);
        }
        return null;
    }

    //*******************BusinessJob********************

    /**
     * 注册业务模块基本定时任务
     *
     * @return 业务模块基本定时任务
     */
    @Bean
    public BusinessJob businessJob() {
        if (enableBaseSchedule && setting.getBool("base.business", "Schedule", true)) {
            LOGGER.info("【{}】基本定时任务【BusinessJob】装载完毕", applicationName);
            return new BusinessJob();
        }
        return null;
    }

}
