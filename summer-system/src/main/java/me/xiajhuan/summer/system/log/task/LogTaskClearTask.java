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

package me.xiajhuan.summer.system.log.task;

import me.xiajhuan.summer.system.schedule.task.QuartzTask;
import me.xiajhuan.summer.system.log.service.LogTaskService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务日志清理
 *
 * @author xiajhuan
 * @date 2023/4/20
 */
@Component
public class LogTaskClearTask implements QuartzTask {

    @Resource
    private LogTaskService logTaskService;

    @Override
    public void execute(String json) {
        logTaskService.clear();
    }

}
