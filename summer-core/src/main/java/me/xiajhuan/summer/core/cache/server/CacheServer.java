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
     * @return 是否存在Key，true：是 false：否
     */
    boolean hasString(String key);

    /**
     * 判断是否存在key（Auto）
     *
     * @param key Key
     * @return 是否存在Key，true：是 false：否
     */
    boolean hasAuto(String key);

    /**
     * 判断是否存在key（Hash）
     *
     * @param key Key
     * @return 是否存在Key，true：是 false：否
     */
    boolean hasHash(String key);

    /**
     * 判断是否存在key（List）
     *
     * @param key Key
     * @return 是否存在Key，true：是 false：否
     */
    boolean hasList(String key);

    /**
     * 删除Key<br>
     * note：堆内缓存需指定值类型，不指定则默认为STRING
     *
     * @param key Key
     * @see CacheConst.Value#STRING
     */
    void delete(String key);

    /**
     * 删除Key
     *
     * @param key       Key
     * @param valueType Value类型
     * @see CacheConst.Value#STRING
     * @see CacheConst.Value#AUTO
     * @see CacheConst.Value#HASH
     * @see CacheConst.Value#LIST
     */
    void delete(String key, String valueType);

    //*******************Value-String********************

    /**
     * 设置值（String）
     *
     * @param key   Key
     * @param value Value（String）
     */
    void setString(String key, String value);

    /**
     * 设置值（String），不允许覆盖
     *
     * @param key   Key
     * @param value Value（String）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setStringAbsent(String key, String value);

    /**
     * 设置值（String），有过期时间
     *
     * @param key   Key
     * @param value Value（String）
     * @param ttl   过期时间（ms）
     */
    void setString(String key, String value, long ttl);

    /**
     * 设置值（String），有过期时间、禁止覆盖
     *
     * @param key   Key
     * @param value Value（String）
     * @param ttl   过期时间（ms）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setStringAbsent(String key, String value, long ttl);

    /**
     * 获取值（String）
     *
     * @param key Key
     * @return Value（String）或{@code null}
     */
    String getString(String key);

    //*******************Value-Auto********************

    /**
     * 自增，默认步长为1
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
     * 自增，默认步长为1、有过期时间
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
     * 自减，默认步长为1
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
     * 自减，默认步长为1、有过期时间
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
     * 设置值（Hash）
     *
     * @param key  Key
     * @param hash Value（Hash）
     */
    void setHash(String key, Map<String, Object> hash);

    /**
     * 设置值（Hash），不允许覆盖
     *
     * @param key  Key
     * @param hash Value（Hash）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setHashAbsent(String key, Map<String, Object> hash);

    /**
     * 设置值（Hash-Field）
     *
     * @param key   Key
     * @param field Field（String）
     * @param value Value（String）
     */
    void setHash(String key, String field, Object value);

    /**
     * 设置值（Hash-Field），不允许覆盖
     *
     * @param key   Key
     * @param field Field（String）
     * @param value Value（String）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setHashAbsent(String key, String field, Object value);

    /**
     * 设置值（Hash），有过期时间
     *
     * @param key  Key
     * @param hash Value（Hash）
     * @param ttl  过期时间（ms）
     */
    void setHash(String key, Map<String, Object> hash, long ttl);

    /**
     * 设置值（Hash），有过期时间、不允许覆盖
     *
     * @param key  Key
     * @param hash Value（Hash）
     * @param ttl  过期时间（ms）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setHashAbsent(String key, Map<String, Object> hash, long ttl);

    /**
     * 设置值（Hash-Field），有过期时间
     *
     * @param key   Key
     * @param field Field（String）
     * @param value Value（String）
     * @param ttl   过期时间（ms）
     */
    void setHash(String key, String field, Object value, long ttl);

    /**
     * 设置值（Hash-Field），有过期时间、不允许覆盖
     *
     * @param key   Key
     * @param field Field（String）
     * @param value Value（String）
     * @param ttl   过期时间（ms）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setHashAbsent(String key, String field, Object value, long ttl);

    /**
     * 获取值（Hash）
     *
     * @param key Key
     * @return Value（Hash）或{@code null}
     */
    Map<String, Object> getHash(String key);

    /**
     * 获取值（Hash-Field）
     *
     * @param key   Key
     * @param field Field（String）
     * @return Value（String）或{@code null}
     */
    Object getHash(String key, String field);

    //*******************Value-List********************

    /**
     * 设置值（List）
     *
     * @param key  Key
     * @param list Value（List）
     */
    void setList(String key, List<String> list);

    /**
     * 设置值（List），追加至末尾
     *
     * @param key  Key
     * @param list Value（List）
     */
    void setListAppend(String key, List<String> list);

    /**
     * 设置值（List），不允许覆盖
     *
     * @param key  Key
     * @param list Value（List）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setListAbsent(String key, List<String> list);

    /**
     * 设置值（List-Element），rPush（添加至末尾）
     *
     * @param key     Key
     * @param element Value（List-Element）
     */
    void setListRPush(String key, String element);

    /**
     * 设置值（List），有过期时间
     *
     * @param key  Key
     * @param list Value（List）
     * @param ttl  过期时间（ms）
     */
    void setList(String key, List<String> list, long ttl);

    /**
     * 设置值（List），有过期时间、追加至末尾
     *
     * @param key  Key
     * @param list Value（List）
     * @param ttl  过期时间（ms）
     */
    void setListAppend(String key, List<String> list, long ttl);

    /**
     * 设置值（List），有过期时间、不允许覆盖
     *
     * @param key  Key
     * @param list Value（List）
     * @param ttl  过期时间（ms）
     * @return 是否设置值成功，true：是 false：否
     */
    boolean setListAbsent(String key, List<String> list, long ttl);

    /**
     * 设置值（List-Element），rPush（添加至末尾）、有过期时间
     *
     * @param key     Key
     * @param element Value（List-Element）
     * @param ttl     过期时间（ms）
     */
    void setListRPush(String key, String element, long ttl);

    /**
     * 获取值（List）
     *
     * @param key Key
     * @return Value（List）或{@code null}
     */
    List<String> getList(String key);

    /**
     * 获取值（List-Element）
     *
     * @param key   Key
     * @param index 索引
     * @return Value（List-Element）或{@code null}
     */
    String getListElement(String key, int index);

}
