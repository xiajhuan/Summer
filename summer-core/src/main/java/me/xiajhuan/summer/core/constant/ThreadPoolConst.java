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

package me.xiajhuan.summer.core.constant;

/**
 * 线程池常量
 *
 * @author xiajhuan
 * @date 2022/11/25
 */
public class ThreadPoolConst {

    /**
     * Spring异步任务线程池Bean名称
     */
    public static final String ASYNC = "springAsyncThreadPool";

    /**
     * Spring异步任务线程池线程名前缀
     */
    public static final String ASYNC_PREFIX = "Async-Spring-";

    /**
     * Quartz定时任务线程池Bean名称
     */
    public static final String SCHEDULE = "quartzScheduleThreadPool";

    /**
     * Quartz定时任务线程池线程名前缀
     */
    public static final String SCHEDULE_PREFIX = "Schedule-Quartz-";

}
