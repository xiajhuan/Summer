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
import me.xiajhuan.summer.core.enums.TaskTypeEnum;

/**
 * 业务定时任务 基类
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
public abstract class BusinessTask {

    protected static final Log LOGGER = LogFactory.get();

    /**
     * 执行
     *
     * @param json Json格式参数
     */
    protected abstract void run(String json);

    /**
     * 开始消息
     *
     * @param name 名称
     * @return 开始消息
     */
    protected String startMsg(String name) {
        return StrUtil.format("【{}】【{}】开始执行...",
                TaskTypeEnum.BUSINESS.getName(), name);
    }

    /**
     * 结束消息
     *
     * @param name 名称
     * @param cost 耗时（ms）
     * @return 结束消息
     */
    protected String endMsg(String name, long cost) {
        return StrUtil.format("【{}】【{}】执行结束，耗时【{}】ms",
                TaskTypeEnum.BUSINESS.getName(), name, cost);
    }

}
