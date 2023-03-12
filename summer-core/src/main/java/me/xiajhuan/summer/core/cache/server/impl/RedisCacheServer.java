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

import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.utils.SpringContextUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public class RedisCacheServer implements CacheServer {

    /**
     * {@link RedisTemplate}
     */
    private static RedisTemplate redisTemplate;

    //*******************单例处理开始********************

    private RedisCacheServer() {
        redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
    }

    private static volatile RedisCacheServer instance = null;

    public static RedisCacheServer getInstance() {
        if (instance == null) {
            synchronized (RedisCacheServer.class) {
                if (instance == null) {
                    instance = new RedisCacheServer();
                }
            }
        }
        return instance;
    }

    //*******************单例处理结束********************

    //*******************Key********************

    @Override
    public boolean hasKeyString(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean hasKeyAuto(String key) {
        return hasKeyString(key);
    }

    @Override
    public boolean hasKeyHash(String key) {
        return hasKeyString(key);
    }

    @Override
    public boolean hasKeyList(String key) {
        return hasKeyString(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(String key, String valueType) {
        delete(key);
    }

    /**
     * 设置过期时间
     *
     * @param key Key
     * @param ttl 过期时间（ms）
     */
    private void expire(String key, long ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.MILLISECONDS);
    }

    //*******************Value-String********************

    @Override
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setStringTtl(String key, String value, long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getString(String key) {
        if (hasKeyString(key)) {
            return String.valueOf(redisTemplate.opsForValue().get(key));
        }
        return null;
    }

    //*******************Value-Auto********************

    @Override
    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public void increment(String key, int step) {
        redisTemplate.opsForValue().increment(key, step);
    }

    @Override
    public void increment(String key, long ttl) {
        increment(key);
        expire(key, ttl);
    }

    @Override
    public void increment(String key, int step, long ttl) {
        increment(key, step);
        expire(key, step);
    }

    @Override
    public void decrement(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public void decrement(String key, int step) {
        redisTemplate.opsForValue().decrement(key, step);
    }

    @Override
    public void decrement(String key, long ttl) {
        decrement(key);
        expire(key, ttl);
    }

    @Override
    public void decrement(String key, int step, long ttl) {
        decrement(key, step);
        expire(key, ttl);
    }

    //*******************Value-Hash********************

    @Override
    public void setHash(String key, Map<String, Object> hash) {
        redisTemplate.opsForHash().putAll(key, hash);
    }

    @Override
    public void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public void setHashTtl(String key, Map<String, Object> hash, long ttl) {
        setHash(key, hash);
        expire(key, ttl);
    }

    @Override
    public void setHashTtl(String key, String field, Object value, long ttl) {
        setHash(key, field, value);
        expire(key, ttl);
    }

    @Override
    public Map<String, Object> getHash(String key) {
        if (hasKeyHash(key)) {
            return redisTemplate.opsForHash().entries(key);
        }
        return null;
    }

    @Override
    public Object getHash(String key, String field) {
        if (redisTemplate.opsForHash().hasKey(key, field)) {
            return redisTemplate.opsForHash().get(key, field);
        }
        return null;
    }

    //*******************Value-List********************

    @Override
    public void setList(String key, List<Object> list) {
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    @Override
    public void setListRPush(String key, Object element) {
        redisTemplate.opsForList().rightPush(key, element);
    }

    @Override
    public void setListTtl(String key, List<Object> list, long ttl) {
        setList(key, list);
        expire(key, ttl);
    }

    @Override
    public void setListRPushTtl(String key, Object element, long ttl) {
        setListRPush(key, element);
        expire(key, ttl);
    }

    @Override
    public List<Object> getList(String key) {
        if (hasKeyList(key)) {
            return redisTemplate.opsForList().range(key, 0, -1);
        }
        return null;
    }

    @Override
    public Object getListElement(String key, int index) {
        if (hasKeyList(key) && index >= 0 && index < redisTemplate.opsForList().size(key)) {
            return redisTemplate.opsForList().index(key, index);
        }
        return null;
    }

}
