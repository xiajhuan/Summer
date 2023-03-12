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

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.admin.common.schedule.base.job.BusinessTaskJob;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.admin.common.log.service.LogErrorService;
import me.xiajhuan.summer.admin.common.log.service.LogLoginService;
import me.xiajhuan.summer.admin.common.log.service.LogOperationService;
import me.xiajhuan.summer.admin.common.schedule.base.job.CommonTaskJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 启用定时任务
 * <pre>
 *  1.该定时任务无法提供对外暴露的接口，故不支持线上动态调整
 *  2.主要初衷是为一些基本的定时调度需求提供轻量级实现，例如：
 *      2.1 随机启动时缓存基本数据
 *      2.2 定期清理日志
 *      2.3 固定的业务批处理操作
 *  3.可通过common.setting下Schedule组进行配置
 *  4.有需要前台展示或涉及动态调整的需求请使用quartz包下基于Quartz的实现
 * </pre>
 *
 * @author xiajhuan
 * @date 2022/11/24
 */
@Configuration
public class JobEnableConfig {

    private static final Log LOGGER = LogFactory.get();

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    @Resource
    private LogOperationService logOperationService;

    @Resource
    private LogErrorService logErrorService;

    @Resource
    private LogLoginService logLoginService;

    /**
     * 注册通用模块定时任务
     *
     * @return 通用模块定时任务
     */
    @Bean
    public CommonTaskJob commonTaskJob() {
        if (setting.getBool("enable-schedule", "Schedule")
                && setting.getBool("job.common", "Schedule")) {
            LOGGER.info("【{}】定时任务【CommonTaskJob】装载完毕", applicationName);
            return new CommonTaskJob().setLogOperationService(logOperationService)
                    .setLogErrorService(logErrorService)
                    .setLogLoginService(logLoginService);
        }
        return null;
    }

    /**
     * 注册业务模块定时任务
     *
     * @return 业务模块定时任务
     */
    @Bean
    public BusinessTaskJob businessTaskJob() {
        if (setting.getBool("enable-schedule", "Schedule")
                && setting.getBool("job.business", "Schedule")) {
            LOGGER.info("【{}】定时任务【BusinessTaskJob】装载完毕", applicationName);
            return new BusinessTaskJob();
        }
        return null;
    }

}
