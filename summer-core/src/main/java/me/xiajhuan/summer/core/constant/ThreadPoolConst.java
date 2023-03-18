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

package me.xiajhuan.summer.core.constant;

/**
 * 线程池常量
 *
 * @author xiajhuan
 * @date 2022/11/25
 */
public class ThreadPoolConst {

    /**
     * 异步任务
     */
    public static class Async {

        /**
         * 通用异步任务线程池Bean名称
         */
        public static final String COMMON = "commonAsyncThreadPool";

        /**
         * 通用异步任务线程池线程名前缀
         */
        public static final String COMMON_PREFIX = "Common-Async-Thread-";

    }

    /**
     * 定时任务
     */
    public static class Schedule {

        /**
         * 基本定时任务线程池线程名前缀
         */
        public static final String BASE_PREFIX = "Base-Schedule-Thread-";

    }

}
