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

package me.xiajhuan.summer.system.common.schedule.config;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import me.xiajhuan.summer.system.common.schedule.task.LogTask;
import me.xiajhuan.summer.system.log.service.LogErrorService;
import me.xiajhuan.summer.system.log.service.LogLoginService;
import me.xiajhuan.summer.system.log.service.LogOperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 系统定时任务配置，note：
 * <pre>
 *  1.该定时任务无法提供对外暴露的接口，故不支持线上动态调整
 *  2.主要初衷是为一些系统层面的定时调度需求提供轻量级支持，例如：
 *      2.1 随机启动时缓存数据
 *      2.2 定期清理日志
 *      2.3 固定的系统批处理操作
 *  3.可通过system.setting下的Schedule组进行配置
 *  4.如需线上动态调整或业务层面的定时调度需求，请使用quartz模块下基于quartz的实现
 * </pre>
 *
 * @author xiajhuan
 * @date 2022/11/24
 * @see SchedulingConfigurer
 * @see ScheduledExecutorService
 */
@Configuration
public class SystemTaskConfig implements SchedulingConfigurer {

    private static final Log LOGGER = LogFactory.get();

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    /**
     * 是否开启系统定时任务
     */
    private boolean enableSystemTask;

    /**
     * 初始化 {@link enableSystemTask}
     */
    @PostConstruct
    private void init() {
        enableSystemTask = setting.getBool("enable-system-task", "Schedule", true);
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
        if (enableSystemTask) {
            return Executors.newScheduledThreadPool(setting.getInt("system.core-pool-size", "Schedule", 3),
                    ThreadUtil.newNamedThreadFactory(ThreadPoolConst.Schedule.SYSTEM_PREFIX, false));
        }
        return null;
    }

    //*******************Task********************

    @Resource
    private LogOperationService logOperationService;

    @Resource
    private LogErrorService logErrorService;

    @Resource
    private LogLoginService logLoginService;

    /**
     * 注册日志定时任务
     *
     * @return 日志定时任务
     */
    @Bean
    public LogTask logTask() {
        if (enableSystemTask && setting.getBool("system.log-task", "Schedule", true)) {
            LOGGER.info("【{}】系统定时任务【LogTask】装载完毕", applicationName);
            return new LogTask().setLogOperationService(logOperationService)
                    .setLogErrorService(logErrorService)
                    .setLogLoginService(logLoginService);
        }
        return null;
    }

}
