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

package me.xiajhuan.summer.system.common.runner;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.xiajhuan.summer.core.utils.SystemUtil;
import me.xiajhuan.summer.system.common.quartz.helper.QuartzHelper;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import me.xiajhuan.summer.system.schedule.service.ScheduleTaskService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * ApplicationRunner（定时任务初始化）
 *
 * @author xiajhuan
 * @date 2023/4/20
 * @see ApplicationRunner
 * @see Scheduler
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TaskInitRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private Scheduler scheduler;

    @Resource
    private ScheduleTaskService scheduleTaskService;

    @Override
    public void run(ApplicationArguments args) {
        // 所有任务
        LambdaQueryWrapper<ScheduleTaskEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ScheduleTaskEntity::getId, ScheduleTaskEntity::getBeanName, ScheduleTaskEntity::getJson,
                ScheduleTaskEntity::getCronExpression, ScheduleTaskEntity::getType, ScheduleTaskEntity::getStatus);
        List<ScheduleTaskEntity> entityList = scheduleTaskService.list(queryWrapper);

        if (entityList.size() > 0) {
            entityList.forEach(entity -> {
                CronTrigger cronTrigger = QuartzHelper.getCronTrigger(scheduler, entity.getId());
                if (cronTrigger == null) {
                    // 新增任务
                    QuartzHelper.addTask(scheduler, entity);
                } else {
                    // 修改任务
                    QuartzHelper.updateTask(scheduler, entity);
                }
            });
            LOGGER.info("【{}】定时任务初始化完毕", SystemUtil.getApplicationName());
        }
    }

}
