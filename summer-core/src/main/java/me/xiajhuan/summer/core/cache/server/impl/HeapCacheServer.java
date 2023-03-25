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

package me.xiajhuan.summer.core.cache.server.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.AbstractCache;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.cache.impl.LRUCache;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.utils.SpringContextUtil;
import me.xiajhuan.summer.core.constant.CacheConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 堆内缓存，基于Hutool-Cache
 *
 * @author xiajhuan
 * @date 2022/11/22
 * @see CacheUtil
 * @see AbstractCache
 * @see FIFOCache
 * @see LFUCache
 * @see LRUCache
 */
public class HeapCacheServer implements CacheServer {

    /**
     * 缓存容量（String）
     */
    private static int capacityString;

    /**
     * 缓存满后对象的移除策略（String）
     */
    private static String strategyString;

    /**
     * 缓存容量（Auto）
     */
    private static int capacityAuto;

    /**
     * 缓存满后对象的移除策略（Auto）
     */
    private static String strategyAuto;

    /**
     * 缓存容量（Hash）
     */
    private static int capacityHash;

    /**
     * 缓存满后对象的移除策略（Hash）
     */
    private static String strategyHash;

    /**
     * 缓存容量（List）
     */
    private static int capacityList;

    /**
     * 缓存满后对象的移除策略（List）
     */
    private static String strategyList;

    //*******************单例处理开始********************

    private HeapCacheServer() {
        // 初始化缓存容量和缓存满后对象的移除策略
        Setting setting = SpringContextUtil.getBean(SettingBeanConst.CORE, Setting.class);
        // 默认移除策略
        String defaultStrategy = CacheConst.Heap.LRU;

        capacityString = setting.getInt("heap-string.capacity", "Cache", 1000);
        strategyString = setting.getByGroupWithLog("heap-string.remove-strategy", "Cache");
        if (StrUtil.isBlank(strategyString)) {
            strategyString = defaultStrategy;
        }

        capacityAuto = setting.getInt("heap-auto.capacity", "Cache", 1000);
        strategyAuto = setting.getByGroupWithLog("heap-auto.remove-strategy", "Cache");
        if (StrUtil.isBlank(strategyAuto)) {
            strategyAuto = defaultStrategy;
        }

        capacityHash = setting.getInt("heap-hash.capacity", "Cache", 1000);
        strategyHash = setting.getByGroupWithLog("heap-hash.remove-strategy", "Cache");
        if (StrUtil.isBlank(strategyHash)) {
            strategyHash = defaultStrategy;
        }

        capacityList = setting.getInt("heap-list.capacity", "Cache", 1000);
        strategyList = setting.getByGroupWithLog("heap-list.remove-strategy", "Cache");
        if (StrUtil.isBlank(strategyList)) {
            strategyList = defaultStrategy;
        }
    }

    private static volatile HeapCacheServer instance = null;

    public static HeapCacheServer getInstance() {
        if (instance == null) {
            synchronized (HeapCacheServer.class) {
                if (instance == null) {
                    instance = new HeapCacheServer();
                }
            }
        }
        return instance;
    }

    //*******************单例处理结束********************

    /**
     * String缓存
     */
    private static AbstractCache<String, String> cacheString;

    /**
     * Auto缓存
     */
    private static AbstractCache<String, Long> cacheAuto;

    /**
     * Hash缓存
     */
    private static AbstractCache<String, Map<String, Object>> cacheHash;

    /**
     * List缓存
     */
    private static AbstractCache<String, List<String>> cacheList;

    /**
     * 初始化缓存
     *
     * @param type 值类型
     */
    private void initCache(String type) {
        switch (type) {
            case CacheConst.Value.STRING:
                if (cacheString == null) {
                    cacheString = initCacheInternal(capacityString, strategyString);
                }
                break;
            case CacheConst.Value.AUTO:
                if (cacheAuto == null) {
                    cacheAuto = initCacheInternal(capacityAuto, strategyAuto);
                }
                break;
            case CacheConst.Value.HASH:
                if (cacheHash == null) {
                    cacheHash = initCacheInternal(capacityHash, strategyHash);
                }
                break;
            case CacheConst.Value.LIST:
                if (cacheList == null) {
                    cacheList = initCacheInternal(capacityList, strategyList);
                }
                break;
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的缓存值类型【{}】", type));
        }
    }

    /**
     * 根据配置初始化缓存
     *
     * @param capacity 缓存容量
     * @param strategy 缓存满后对象的移除策略
     * @return {@link AbstractCache}
     */
    private AbstractCache initCacheInternal(int capacity, String strategy) {
        switch (strategy) {
            case CacheConst.Heap.FIFO:
                return CacheUtil.newFIFOCache(capacity);
            case CacheConst.Heap.LFU:
                return CacheUtil.newLFUCache(capacity);
            case CacheConst.Heap.LRU:
                return CacheUtil.newLRUCache(capacity);
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的缓存移除策略【{}】", strategy));
        }
    }

    //*******************Key********************

    @Override
    public boolean hasKeyString(String key) {
        return cacheString != null && cacheString.containsKey(key);
    }

    @Override
    public boolean hasKeyAuto(String key) {
        return cacheAuto != null && cacheAuto.containsKey(key);
    }

    @Override
    public boolean hasKeyHash(String key) {
        return cacheHash != null && cacheHash.containsKey(key);
    }

    @Override
    public boolean hasKeyList(String key) {
        return cacheList != null && cacheList.containsKey(key);
    }

    @Override
    public void delete(String key) {
        delete(key, CacheConst.Value.STRING);
    }

    @Override
    public void delete(String key, String valueType) {
        switch (valueType) {
            case CacheConst.Value.STRING:
                cacheString.remove(key);
                break;
            case CacheConst.Value.AUTO:
                cacheAuto.remove(key);
                break;
            case CacheConst.Value.HASH:
                cacheHash.remove(key);
                break;
            case CacheConst.Value.LIST:
                cacheList.remove(key);
                break;
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的缓存值类型【{}】", valueType));
        }
    }

    //*******************Value-String********************

    @Override
    public void setString(String key, String value) {
        setStringTtl(key, value, -1);
    }

    @Override
    public void setStringTtl(String key, String value, long ttl) {
        initCache(CacheConst.Value.STRING);

        if (ttl == -1) {
            cacheString.put(key, value);
        } else {
            cacheString.put(key, value, ttl);
        }
    }

    @Override
    public String getString(String key) {
        if (hasKeyString(key)) {
            return cacheString.get(key);
        }
        return null;
    }

    //*******************Value-Auto********************

    @Override
    public void increment(String key) {
        increment(key, 1);
    }

    @Override
    public void increment(String key, int step) {
        increment(key, step, -1);
    }

    @Override
    public void increment(String key, long ttl) {
        increment(key, 1, ttl);
    }

    @Override
    public void increment(String key, int step, long ttl) {
        initCache(CacheConst.Value.AUTO);

        if (hasKeyAuto(key)) {
            if (ttl == -1) {
                cacheAuto.put(key, cacheAuto.get(key).longValue() + step);
            } else {
                cacheAuto.put(key, cacheAuto.get(key).longValue() + step, ttl);
            }
        } else {
            if (ttl == -1) {
                cacheAuto.put(key, Long.valueOf(step));
            } else {
                cacheAuto.put(key, Long.valueOf(step), ttl);
            }
        }
    }

    @Override
    public void decrement(String key) {
        increment(key, -1);
    }

    @Override
    public void decrement(String key, int step) {
        increment(key, 0 - step);
    }

    @Override
    public void decrement(String key, long ttl) {
        increment(key, -1, ttl);
    }

    @Override
    public void decrement(String key, int step, long ttl) {
        increment(key, 0 - step, ttl);
    }

    //*******************Value-Hash********************

    @Override
    public void setHash(String key, Map<String, Object> hash) {
        setHashTtl(key, hash, -1);
    }

    @Override
    public void setHash(String key, String field, Object value) {
        setHashTtl(key, field, value, -1);
    }

    @Override
    public void setHashTtl(String key, Map<String, Object> hash, long ttl) {
        initCache(CacheConst.Value.HASH);

        if (ttl == -1) {
            cacheHash.put(key, hash);
        } else {
            cacheHash.put(key, hash, ttl);
        }
    }

    @Override
    public void setHashTtl(String key, String field, Object value, long ttl) {
        initCache(CacheConst.Value.HASH);

        Map<String, Object> hash = MapUtil.newHashMap(1);
        hash.put(field, value);
        if (ttl == -1) {
            cacheHash.put(key, hash);
        } else {
            cacheHash.put(key, hash, ttl);
        }
    }

    @Override
    public Map<String, Object> getHash(String key) {
        if (hasKeyHash(key)) {
            return cacheHash.get(key);
        }
        return null;
    }

    @Override
    public Object getHash(String key, String field) {
        if (hasKeyHash(key)) {
            Map<String, Object> map = cacheHash.get(key);
            if (MapUtil.isNotEmpty(map)) {
                return map.get(field);
            }
        }
        return null;
    }

    //*******************Value-List********************

    @Override
    public void setList(String key, List<String> list) {
        setListTtl(key, list, -1);
    }

    @Override
    public void setListRPush(String key, String element) {
        setListRPushTtl(key, element, -1);
    }

    @Override
    public void setListTtl(String key, List<String> list, long ttl) {
        initCache(CacheConst.Value.LIST);

        if (ttl == -1) {
            cacheList.put(key, list);
        } else {
            cacheList.put(key, list, ttl);
        }
    }

    @Override
    public void setListRPushTtl(String key, String element, long ttl) {
        initCache(CacheConst.Value.LIST);

        final List<String> elementList;
        if (hasKeyList(key)) {
            elementList = getList(key);
        } else {
            elementList = new ArrayList<>(1);
        }
        elementList.add(element);
        if (ttl == -1) {
            cacheList.put(key, elementList);
        } else {
            cacheList.put(key, elementList, ttl);
        }
    }

    @Override
    public List<String> getList(String key) {
        if (hasKeyList(key)) {
            return cacheList.get(key);
        }
        return null;
    }

    @Override
    public String getListElement(String key, int index) {
        if (hasKeyList(key) && index >= 0) {
            List<String> elementList = getList(key);
            if (elementList != null && index < elementList.size()) {
                return elementList.get(index);
            }
        }
        return null;
    }

}
