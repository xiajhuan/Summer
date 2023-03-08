package me.xiajhuan.summer.common.cache.server.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.common.cache.server.CacheServer;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.utils.SpringContextUtil;
import me.xiajhuan.summer.common.constant.CacheConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 堆内缓存，基于Hutool-cache
 *
 * @author xiajhuan
 * @date 2022/11/22
 * @see Cache
 * @see CacheUtil
 */
public class HeapCacheServer implements CacheServer {

    /**
     * 缓存容量（String）
     */
    private static int capacityString;

    /**
     * 缓存容量（Auto）
     */
    private static int capacityAuto;

    /**
     * 缓存容量（Hash）
     */
    private static int capacityHash;

    /**
     * 缓存容量（List）
     */
    private static int capacityList;

    //*******************单例处理开始********************

    private HeapCacheServer() {
        // 初始化缓存容量
        Setting setting = SpringContextUtil.getBean(SettingBeanConst.COMMON, Setting.class);
        capacityString = setting.getInt("heap.capacity.string", "Cache");
        capacityAuto = setting.getInt("heap.capacity.auto", "Cache");
        capacityHash = setting.getInt("heap.capacity.hash", "Cache");
        capacityList = setting.getInt("heap.capacity.list", "Cache");
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
    private static Cache<String, String> cacheString;

    /**
     * Auto缓存
     */
    private static Cache<String, Long> cacheAuto;

    /**
     * Hash缓存
     */
    private static Cache<String, Map<String, Object>> cacheHash;

    /**
     * List缓存
     */
    private static Cache<String, List<Object>> cacheList;

    /**
     * 初始化缓存，默认策略：FIFO
     *
     * @param type 值类型
     */
    public void initCache(String type) {
        switch (type) {
            case CacheConst.VALUE_STRING:
                if (cacheString == null) {
                    cacheString = CacheUtil.newFIFOCache(capacityString);
                }
                break;
            case CacheConst.VALUE_AUTO:
                if (cacheAuto == null) {
                    cacheAuto = CacheUtil.newFIFOCache(capacityAuto);
                }
                break;
            case CacheConst.VALUE_HASH:
                if (cacheHash == null) {
                    cacheHash = CacheUtil.newFIFOCache(capacityHash);
                }
                break;
            default:
                if (cacheList == null) {
                    cacheList = CacheUtil.newFIFOCache(capacityList);
                }
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
        delete(key, CacheConst.VALUE_STRING);
    }

    @Override
    public void delete(String key, String valueType) {
        switch (valueType) {
            case CacheConst.VALUE_STRING:
                cacheString.remove(key);
                break;
            case CacheConst.VALUE_AUTO:
                cacheAuto.remove(key);
                break;
            case CacheConst.VALUE_HASH:
                cacheHash.remove(key);
                break;
            case CacheConst.VALUE_LIST:
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
        initCache(CacheConst.VALUE_STRING);

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
        initCache(CacheConst.VALUE_AUTO);

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
        initCache(CacheConst.VALUE_HASH);

        if (ttl == -1) {
            cacheHash.put(key, hash);
        } else {
            cacheHash.put(key, hash, ttl);
        }
    }

    @Override
    public void setHashTtl(String key, String field, Object value, long ttl) {
        initCache(CacheConst.VALUE_HASH);

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
    public void setList(String key, List<Object> list) {
        setListTtl(key, list, -1);
    }

    @Override
    public void setListRPush(String key, Object element) {
        setListRPushTtl(key, element, -1);
    }

    @Override
    public void setListTtl(String key, List<Object> list, long ttl) {
        initCache(CacheConst.VALUE_LIST);

        if (ttl == -1) {
            cacheList.put(key, list);
        } else {
            cacheList.put(key, list, ttl);
        }
    }

    @Override
    public void setListRPushTtl(String key, Object element, long ttl) {
        initCache(CacheConst.VALUE_LIST);

        final List<Object> elementList;
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
    public List<Object> getList(String key) {
        if (hasKeyList(key)) {
            return cacheList.get(key);
        }
        return null;
    }

    @Override
    public Object getListElement(String key, int index) {
        if (hasKeyList(key) && index >= 0) {
            List<Object> elementList = getList(key);
            if (elementList != null && index < elementList.size()) {
                return elementList.get(index);
            }
        }
        return null;
    }

}
