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

package me.xiajhuan.summer.core.cache.server;

import me.xiajhuan.summer.core.constant.CacheConst;

import java.util.List;
import java.util.Map;

/**
 * 缓存服务
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public interface CacheServer {

    //*******************Key********************

    /**
     * 判断是否存在key（String）
     *
     * @param key Key
     * @return 是否存在Key，true：存在 false：不存在
     */
    boolean hasKeyString(String key);

    /**
     * 判断是否存在key（Auto）
     *
     * @param key Key
     * @return 是否存在Key，true：存在 false：不存在
     */
    boolean hasKeyAuto(String key);

    /**
     * 判断是否存在key（Hash）
     *
     * @param key Key
     * @return 是否存在Key，true：存在 false：不存在
     */
    boolean hasKeyHash(String key);

    /**
     * 判断是否存在key（List）
     *
     * @param key Key
     * @return 是否存在Key，true：存在 false：不存在
     */
    boolean hasKeyList(String key);

    /**
     * 删除Key<br>
     * note：堆内缓存需指定值类型，不指定则默认STRING
     *
     * @param key Key
     * @see CacheConst#VALUE_STRING
     */
    void delete(String key);

    /**
     * 删除Key
     *
     * @param key       Key
     * @param valueType Value类型
     * @see CacheConst#VALUE_STRING
     * @see CacheConst#VALUE_AUTO
     * @see CacheConst#VALUE_HASH
     * @see CacheConst#VALUE_LIST
     */
    void delete(String key, String valueType);

    //*******************Value-String********************

    /**
     * 设值（String）
     *
     * @param key   Key
     * @param value Value（String）
     */
    void setString(String key, String value);

    /**
     * 设值（String）,有过期时间
     *
     * @param key   Key
     * @param value Value（String）
     * @param ttl   过期时间（ms）
     */
    void setStringTtl(String key, String value, long ttl);

    /**
     * 获取值（String）
     *
     * @param key Key
     * @return Value（String）
     */
    String getString(String key);

    //*******************Value-Auto********************

    /**
     * 自增，默认步长为：1
     *
     * @param key Key
     */
    void increment(String key);

    /**
     * 自增
     *
     * @param key  Key
     * @param step 步长
     */
    void increment(String key, int step);

    /**
     * 自增，默认步长为：1，有过期时间
     *
     * @param key Key
     * @param ttl 过期时间（ms）
     */
    void increment(String key, long ttl);

    /**
     * 自增，有过期时间
     *
     * @param key  Key
     * @param step 步长
     * @param ttl  过期时间（ms）
     */
    void increment(String key, int step, long ttl);

    /**
     * 自减，默认步长为：1
     *
     * @param key Key
     */
    void decrement(String key);

    /**
     * 自减
     *
     * @param key  Key
     * @param step 步长
     */
    void decrement(String key, int step);

    /**
     * 自减，默认步长为：1，有过期时间
     *
     * @param key Key
     * @param ttl 过期时间（ms）
     */
    void decrement(String key, long ttl);

    /**
     * 自减，有过期时间
     *
     * @param key  Key
     * @param step 步长
     * @param ttl  过期时间（ms）
     */
    void decrement(String key, int step, long ttl);

    //*******************Value-Hash********************

    /**
     * 设值（Hash）
     *
     * @param key  Key
     * @param hash Value（Hash）
     */
    void setHash(String key, Map<String, Object> hash);

    /**
     * 设值（Field->Hash）
     *
     * @param key   Key
     * @param field Field（String）
     * @param value Value（String）
     */
    void setHash(String key, String field, Object value);

    /**
     * 设值（Hash）,有过期时间
     *
     * @param key  Key
     * @param hash Value（Hash）
     * @param ttl  过期时间（ms）
     */
    void setHashTtl(String key, Map<String, Object> hash, long ttl);

    /**
     * 设值（Field->Hash）,有过期时间
     *
     * @param key   Key
     * @param field Field（String）
     * @param value Value（String）
     * @param ttl   过期时间（ms）
     */
    void setHashTtl(String key, String field, Object value, long ttl);

    /**
     * 获取值（Hash）
     *
     * @param key Key
     * @return Value（Hash）
     */
    Map<String, Object> getHash(String key);

    /**
     * 获取值（Hash-Value）
     *
     * @param key   Key
     * @param field Field（String）
     * @return Value（String）
     */
    Object getHash(String key, String field);

    //*******************Value-List********************

    /**
     * 设值（List）
     *
     * @param key  Key
     * @param list Value（List）
     */
    void setList(String key, List<Object> list);

    /**
     * 设值（Value（List-Element）->rPush（往列表末尾添加））
     *
     * @param key     Key
     * @param element Value（List-Element）
     */
    void setListRPush(String key, Object element);

    /**
     * 设值（List）,有过期时间
     *
     * @param key  Key
     * @param list Value（List）
     * @param ttl  过期时间（ms）
     */
    void setListTtl(String key, List<Object> list, long ttl);

    /**
     * 设值（Value（List-Element）->rPush（往列表末尾添加））,有过期时间
     *
     * @param key     Key
     * @param element Value（List-Element）
     * @param ttl     过期时间（ms）
     */
    void setListRPushTtl(String key, Object element, long ttl);

    /**
     * 获取值（List）
     *
     * @param key Key
     * @return Value（List）
     */
    List<Object> getList(String key);

    /**
     * 获取值Value（List-Element）（根据索引下标）
     *
     * @param key   Key
     * @param index 索引下标
     * @return Value（List-Element）
     */
    Object getListElement(String key, int index);

}
