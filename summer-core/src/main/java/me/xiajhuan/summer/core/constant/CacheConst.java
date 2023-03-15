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
 * 缓存常量
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public class CacheConst {

    //*******************缓存类型********************

    /**
     * 缓存类型：堆内缓存
     */
    public static final String HEAP = "HEAP";

    /**
     * 缓存类型：Redis
     */
    public static final String REDIS = "REDIS";

    //*******************缓存值类型********************

    /**
     * 缓存值类型：String
     */
    public static final String VALUE_STRING = "STRING";

    /**
     * 缓存值类型：Auto
     */
    public static final String VALUE_AUTO = "AUTO";

    /**
     * 缓存值类型：Hash
     */
    public static final String VALUE_HASH = "HASH";

    /**
     * 缓存值类型：List
     */
    public static final String VALUE_LIST = "LIST";

    /**
     * 堆内缓存
     */
    public static class Heap {

        //*******************缓存满后对象的移除策略********************

        /**
         * 先入先出
         */
        public static final String FIFO = "FIFO";

        /**
         * 最少使用
         */
        public static final String LFU = "LFU";

        /**
         * 最近最久未使用
         */
        public static final String LRU = "LRU";

    }

}
