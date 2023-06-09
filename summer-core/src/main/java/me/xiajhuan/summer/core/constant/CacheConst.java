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

import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.cache.impl.LRUCache;

/**
 * 缓存常量
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public class CacheConst {

    /**
     * 缓存类型
     */
    public static class Type {

        /**
         * Redis
         */
        public static final String REDIS = "REDIS";

        /**
         * 堆内缓存
         */
        public static final String HEAP = "HEAP";

    }

    /**
     * 缓存值类型
     */
    public static class Value {

        /**
         * String
         */
        public static final String STRING = "STRING";

        /**
         * Auto（自增/自减）
         */
        public static final String AUTO = "AUTO";

        /**
         * Hash
         */
        public static final String HASH = "HASH";

        /**
         * List
         */
        public static final String LIST = "LIST";

    }

    /**
     * 堆内缓存满后对象的移除策略
     */
    public static class Strategy {

        /**
         * 先入先出
         *
         * @see FIFOCache
         */
        public static final String FIFO = "FIFO";

        /**
         * 最少使用
         *
         * @see LFUCache
         */
        public static final String LFU = "LFU";

        /**
         * 最近最久未使用
         *
         * @see LRUCache
         */
        public static final String LRU = "LRU";

    }

}
