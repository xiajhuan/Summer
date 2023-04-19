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

package me.xiajhuan.summer.system.common.quartz.task;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.util.Date;

/**
 * 业务定时任务 基类
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
public abstract class BusinessTask {

    protected static final Log LOGGER = LogFactory.get();

    /**
     * 执行定时任务
     *
     * @param json Json格式参数
     */
    protected abstract void run(String json);

    /**
     * 开始消息
     *
     * @param taskName 定时任务名称
     * @param now      当前时间
     * @return 开始消息
     */
    protected String startMsg(String taskName, Date now) {
        return StrUtil.format("【Business】【{}】开始执行：{}", taskName, now);
    }

    /**
     * 结束消息
     *
     * @param taskName 定时任务名称
     * @param now      当前时间
     * @param cost     耗时（ms）
     * @return 结束消息
     */
    protected String endMsg(String taskName, Date now, long cost) {
        return StrUtil.format("【Business】【{}】执行结束：{}，耗时【{}】ms", taskName, now, cost);
    }

}
