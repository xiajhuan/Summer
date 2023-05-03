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

package me.xiajhuan.summer.core.cache.server.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.AbstractCache;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.CacheConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 堆内缓存，基于Hutool-Cache
 *
 * @author xiajhuan
 * @date 2022/11/22
 * @see AbstractCache
 * @see CacheUtil
 */
public class HeapCacheServer implements CacheServer {

    /**
     * 缓存容量（String）
     */
    private final int capacityString;

    /**
     * 缓存满后对象的移除策略（String）
     */
    private String strategyString;

    /**
     * 缓存容量（Auto）
     */
    private final int capacityAuto;

    /**
     * 缓存满后对象的移除策略（Auto）
     */
    private String strategyAuto;

    /**
     * 缓存容量（Hash）
     */
    private final int capacityHash;

    /**
     * 缓存满后对象的移除策略（Hash）
     */
    private String strategyHash;

    /**
     * 缓存容量（List）
     */
    private final int capacityList;

    /**
     * 缓存满后对象的移除策略（List）
     */
    private String strategyList;

    //*******************单例处理开始********************

    private HeapCacheServer() {
        // 初始化缓存容量和缓存满后对象的移除策略
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        // 默认移除策略
        String defaultStrategy = CacheConst.Strategy.LRU;

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
    private AbstractCache<String, String> cacheString;

    /**
     * Auto缓存
     */
    private AbstractCache<String, Long> cacheAuto;

    /**
     * Hash缓存
     */
    private AbstractCache<String, Map<String, Object>> cacheHash;

    /**
     * List缓存
     */
    private AbstractCache<String, List<String>> cacheList;

    /**
     * 初始化缓存
     *
     * @param type 值类型
     */
    @SuppressWarnings("unchecked")
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
     * 初始化缓存处理
     *
     * @param capacity 缓存容量
     * @param strategy 缓存满后对象的移除策略
     * @return {@link AbstractCache}
     */
    @SuppressWarnings("rawtypes")
    private AbstractCache initCacheInternal(int capacity, String strategy) {
        switch (strategy) {
            case CacheConst.Strategy.FIFO:
                return CacheUtil.newFIFOCache(capacity);
            case CacheConst.Strategy.LFU:
                return CacheUtil.newLFUCache(capacity);
            case CacheConst.Strategy.LRU:
                return CacheUtil.newLRUCache(capacity);
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的缓存移除策略【{}】", strategy));
        }
    }

    //*******************Key********************

    @Override
    public boolean hasString(String key) {
        return cacheString != null && cacheString.containsKey(key);
    }

    @Override
    public boolean hasAuto(String key) {
        return cacheAuto != null && cacheAuto.containsKey(key);
    }

    @Override
    public boolean hasHash(String key) {
        return cacheHash != null && cacheHash.containsKey(key);
    }

    @Override
    public boolean hasList(String key) {
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
        setString(key, value, -1L);
    }

    @Override
    public boolean setStringAbsent(String key, String value) {
        return setStringAbsent(key, value, -1L);
    }

    @Override
    public void setString(String key, String value, long ttl) {
        initCache(CacheConst.Value.STRING);

        if (ttl == -1L) {
            cacheString.put(key, value);
        } else {
            cacheString.put(key, value, ttl);
        }
    }

    @Override
    public boolean setStringAbsent(String key, String value, long ttl) {
        if (hasString(key)) {
            return false;
        }
        setString(key, value, ttl);
        return true;
    }

    @Override
    public String getString(String key) {
        if (hasString(key)) {
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
        increment(key, step, -1L);
    }

    @Override
    public void increment(String key, long ttl) {
        increment(key, 1, ttl);
    }

    @Override
    public void increment(String key, int step, long ttl) {
        initCache(CacheConst.Value.AUTO);

        if (hasAuto(key)) {
            if (ttl == -1L) {
                cacheAuto.put(key, cacheAuto.get(key) + step);
            } else {
                cacheAuto.put(key, cacheAuto.get(key) + step, ttl);
            }
        } else {
            if (ttl == -1L) {
                cacheAuto.put(key, (long) step);
            } else {
                cacheAuto.put(key, (long) step, ttl);
            }
        }
    }

    @Override
    public void decrement(String key) {
        increment(key, -1);
    }

    @Override
    public void decrement(String key, int step) {
        increment(key, -step);
    }

    @Override
    public void decrement(String key, long ttl) {
        increment(key, -1, ttl);
    }

    @Override
    public void decrement(String key, int step, long ttl) {
        increment(key, -step, ttl);
    }

    //*******************Value-Hash********************

    @Override
    public void setHash(String key, Map<String, Object> hash) {
        setHash(key, hash, -1L);
    }

    @Override
    public boolean setHashAbsent(String key, Map<String, Object> hash) {
        return setHashAbsent(key, hash, -1L);
    }

    @Override
    public void setHash(String key, String field, Object value) {
        setHash(key, field, value, -1L);
    }

    @Override
    public boolean setHashAbsent(String key, String field, Object value) {
        return setHashAbsent(key, field, value, -1L);
    }

    @Override
    public void setHash(String key, Map<String, Object> hash, long ttl) {
        initCache(CacheConst.Value.HASH);

        cacheHash(key, hash, ttl);
    }

    @Override
    public boolean setHashAbsent(String key, Map<String, Object> hash, long ttl) {
        if (hasHash(key)) {
            return false;
        }
        setHash(key, hash, ttl);
        return true;
    }

    @Override
    public void setHash(String key, String field, Object value, long ttl) {
        initCache(CacheConst.Value.HASH);

        Map<String, Object> hash = MapUtil.newHashMap(1);
        hash.put(field, value);

        cacheHash(key, hash, ttl);
    }

    @Override
    public boolean setHashAbsent(String key, String field, Object value, long ttl) {
        if (getHash(key, field) != null) {
            return false;
        }
        setHash(key, field, value, ttl);
        return true;
    }

    @Override
    public Map<String, Object> getHash(String key) {
        if (hasHash(key)) {
            return cacheHash.get(key);
        }
        return null;
    }

    @Override
    public Object getHash(String key, String field) {
        if (hasHash(key)) {
            Map<String, Object> map = cacheHash.get(key);
            if (MapUtil.isNotEmpty(map)) {
                return map.get(field);
            }
        }
        return null;
    }

    /**
     * 缓存Hash
     *
     * @param key  Key
     * @param hash Value（Hash）
     * @param ttl  过期时间（ms）
     */
    private void cacheHash(String key, Map<String, Object> hash, long ttl) {
        if (ttl == -1L) {
            cacheHash.put(key, hash);
        } else {
            cacheHash.put(key, hash, ttl);
        }
    }

    //*******************Value-List********************

    @Override
    public void setList(String key, List<String> list) {
        setList(key, list, -1L);
    }

    @Override
    public void setListAppend(String key, List<String> list) {
        setListAppend(key, list, -1L);
    }

    @Override
    public boolean setListAbsent(String key, List<String> list) {
        return setListAbsent(key, list, -1L);
    }

    @Override
    public void setListRPush(String key, String element) {
        setListRPush(key, element, -1L);
    }

    @Override
    public void setList(String key, List<String> list, long ttl) {
        initCache(CacheConst.Value.LIST);

        cacheList(key, list, ttl);
    }

    @Override
    public void setListAppend(String key, List<String> list, long ttl) {
        initCache(CacheConst.Value.LIST);

        final List<String> elementList;
        if (hasList(key)) {
            elementList = getList(key);
            elementList.addAll(list);
        } else {
            elementList = list;
        }

        cacheList(key, elementList, ttl);
    }

    @Override
    public boolean setListAbsent(String key, List<String> list, long ttl) {
        if (hasList(key)) {
            return false;
        }
        setList(key, list, ttl);
        return true;
    }

    @Override
    public void setListRPush(String key, String element, long ttl) {
        initCache(CacheConst.Value.LIST);

        final List<String> elementList;
        if (hasList(key)) {
            elementList = getList(key);
        } else {
            elementList = new ArrayList<>(1);
        }
        elementList.add(element);

        cacheList(key, elementList, ttl);
    }

    @Override
    public List<String> getList(String key) {
        if (hasList(key)) {
            return cacheList.get(key);
        }
        return null;
    }

    @Override
    public String getListElement(String key, int index) {
        if (hasList(key) && index >= 0) {
            List<String> elementList = getList(key);
            if (elementList != null && index < elementList.size()) {
                return elementList.get(index);
            }
        }
        return null;
    }

    /**
     * 缓存List
     *
     * @param key  Key
     * @param list Value（List）
     * @param ttl  过期时间（ms）
     */
    private void cacheList(String key, List<String> list, long ttl) {
        if (ttl == -1L) {
            cacheList.put(key, list);
        } else {
            cacheList.put(key, list, ttl);
        }
    }

}
