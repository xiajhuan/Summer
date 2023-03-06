package me.xiajhuan.summer.common.cache.factory;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.cache.properties.ServerCacheProperties;
import me.xiajhuan.summer.common.cache.server.CacheServer;
import me.xiajhuan.summer.common.cache.server.impl.HeapCacheServer;
import me.xiajhuan.summer.common.cache.server.impl.RedisCacheServer;
import me.xiajhuan.summer.common.constant.CacheConst;
import me.xiajhuan.summer.common.utils.SpringContextUtil;

/**
 * 缓存服务工厂
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public class CacheServerFactory {

    //*******************单例处理开始********************

    private CacheServerFactory() {
    }

    private static volatile CacheServerFactory factory = null;

    public static CacheServerFactory getInstance() {
        if (factory == null) {
            synchronized (CacheServerFactory.class) {
                if (factory == null) {
                    factory = new CacheServerFactory();
                }
            }
        }
        return factory;
    }

    //*******************单例处理结束********************

    /**
     * 获取缓存服务
     *
     * @return 缓存服务
     */
    public CacheServer getCacheServer() {
        String cacheType = SpringContextUtil.getBean("serverCacheProperties", ServerCacheProperties.class).getType();

        if (cacheType.equals(CacheConst.HEAP)) {
            return HeapCacheServer.getInstance();
        } else if (cacheType.equals(CacheConst.REDIS)) {
            return RedisCacheServer.getInstance();
        } else {
            throw new IllegalArgumentException(StrUtil.format("获取缓存服务失败，不支持的缓存类型【{}】", cacheType));
        }
    }

}
