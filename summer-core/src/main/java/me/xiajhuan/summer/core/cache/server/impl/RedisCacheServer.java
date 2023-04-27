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

import cn.hutool.extra.spring.SpringUtil;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存
 *
 * @author xiajhuan
 * @date 2022/11/22
 * @see RedisTemplate
 */
@SuppressWarnings("all")
public class RedisCacheServer implements CacheServer {

    /**
     * {@link RedisTemplate}
     */
    private static RedisTemplate redisTemplate;

    //*******************单例处理开始********************

    private RedisCacheServer() {
        redisTemplate = SpringUtil.getBean("redisTemplate", RedisTemplate.class);
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
    public boolean hasString(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean hasAuto(String key) {
        return hasString(key);
    }

    @Override
    public boolean hasHash(String key) {
        return hasString(key);
    }

    @Override
    public boolean hasList(String key) {
        return hasString(key);
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
        if (ttl > 0L) {
            redisTemplate.expire(key, ttl, TimeUnit.MILLISECONDS);
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
        if (ttl == -1L) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public boolean setStringAbsent(String key, String value, long ttl) {
        if (ttl == -1L) {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } else {
            return redisTemplate.opsForValue().setIfAbsent(key, value, ttl, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public String getString(String key) {
        if (hasString(key)) {
            return String.valueOf(redisTemplate.opsForValue().get(key));
        }
        return null;
    }

    //*******************Value-Auto********************

    @Override
    public void increment(String key) {
        increment(key, -1L);
    }

    @Override
    public void increment(String key, int step) {
        increment(key, step, -1L);
    }

    @Override
    public void increment(String key, long ttl) {
        redisTemplate.opsForValue().increment(key);
        expire(key, ttl);
    }

    @Override
    public void increment(String key, int step, long ttl) {
        redisTemplate.opsForValue().increment(key, step);
        expire(key, ttl);
    }

    @Override
    public void decrement(String key) {
        decrement(key, -1L);
    }

    @Override
    public void decrement(String key, int step) {
        decrement(key, step, -1L);
    }

    @Override
    public void decrement(String key, long ttl) {
        redisTemplate.opsForValue().decrement(key);
        expire(key, ttl);
    }

    @Override
    public void decrement(String key, int step, long ttl) {
        redisTemplate.opsForValue().decrement(key, step);
        expire(key, ttl);
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
        redisTemplate.opsForHash().putAll(key, hash);
        expire(key, ttl);
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
        redisTemplate.opsForHash().put(key, field, value);
        expire(key, ttl);
    }

    @Override
    public boolean setHashAbsent(String key, String field, Object value, long ttl) {
        boolean flag = redisTemplate.opsForHash().putIfAbsent(key, field, value);
        if (flag) {
            expire(key, ttl);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getHash(String key) {
        if (hasHash(key)) {
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
        delete(key);
        setListAppend(key, list, ttl);
    }

    @Override
    public void setListAppend(String key, List<String> list, long ttl) {
        redisTemplate.opsForList().rightPushAll(key, list);
        expire(key, ttl);
    }

    @Override
    public boolean setListAbsent(String key, List<String> list, long ttl) {
        if (hasList(key)) {
            return false;
        }
        setListAppend(key, list, ttl);
        return true;
    }

    @Override
    public void setListRPush(String key, String element, long ttl) {
        redisTemplate.opsForList().rightPush(key, element);
        expire(key, ttl);
    }

    @Override
    public List<String> getList(String key) {
        if (hasList(key)) {
            return redisTemplate.opsForList().range(key, 0L, -1L);
        }
        return null;
    }

    @Override
    public String getListElement(String key, int index) {
        if (hasList(key) && index >= 0 && index < redisTemplate.opsForList().size(key)) {
            return String.valueOf(redisTemplate.opsForList().index(key, index));
        }
        return null;
    }

}
