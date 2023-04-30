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

package me.xiajhuan.summer.core.cache.factory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.cache.server.impl.HeapCacheServer;
import me.xiajhuan.summer.core.cache.server.impl.RedisCacheServer;
import me.xiajhuan.summer.core.constant.CacheConst;
import me.xiajhuan.summer.core.properties.ApplicationProperties;

/**
 * 缓存服务工厂
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public class CacheServerFactory {

    /**
     * 构造CacheServerFactory（不允许实例化）
     */
    private CacheServerFactory() {
    }

    /**
     * 缓存类型
     */
    private static String CACHE_TYPE;

    /**
     * 初始化 {@link CACHE_TYPE}
     */
    static {
        CACHE_TYPE = SpringUtil.getBean("applicationProperties", ApplicationProperties.class)
                .getCache().getType();
        if (StrUtil.isBlank(CACHE_TYPE)) {
            // 没有配置则默认为：REDIS
            CACHE_TYPE = CacheConst.Type.REDIS;
        }
    }

    /**
     * 获取缓存服务
     *
     * @return 缓存服务
     */
    public static CacheServer getCacheServer() {
        if (CACHE_TYPE.equalsIgnoreCase(CacheConst.Type.REDIS)) {
            return RedisCacheServer.getInstance();
        } else if (CACHE_TYPE.equalsIgnoreCase(CacheConst.Type.HEAP)) {
            return HeapCacheServer.getInstance();
        } else {
            throw new IllegalArgumentException(StrUtil.format("不支持的缓存类型【{}】", CACHE_TYPE));
        }
    }

    /**
     * 获取Redis缓存服务，note：
     * <pre>
     *   1.当缓存类型为“REDIS”时，若有分级缓存需求，可以通过该方法获取Redis缓存服务
     *   2.当没有分级缓存需求时，推荐使用 {@link CacheServerFactory#getCacheServer()}，
     *     这将便于在缓存类型切换时保证代码通用
     *   3.当缓存类型不为“REDIS”时将抛出 {@link UnsupportedOperationException}
     * </pre>
     *
     * @return Redis缓存服务
     */
    public static RedisCacheServer getRedisCacheServer() {
        if (CACHE_TYPE.equalsIgnoreCase(CacheConst.Type.REDIS)) {
            return RedisCacheServer.getInstance();
        }
        throw new UnsupportedOperationException("缓存类型必须为REDIS才能调用!");
    }

    /**
     * 获取堆内缓存服务，note：
     * <pre>
     *   1.当缓存类型为“REDIS”时，若有分级缓存需求，可以通过该方法获取堆内缓存服务
     *   2.当缓存类型为“HEAP”时，推荐使用 {@link CacheServerFactory#getCacheServer()}，
     *     这将便于在缓存类型切换时保证代码通用
     * </pre>
     *
     * @return 堆内缓存服务
     */
    public static HeapCacheServer getHeapCacheServer() {
        return HeapCacheServer.getInstance();
    }

}
