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

package me.xiajhuan.summer.system.common.task;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.Setter;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.constant.TimeUnitConst;

import java.util.Date;

/**
 * 系统定时任务 基类
 *
 * @author xiajhuan
 * @date 2023/4/15
 */
@Setter
public class SystemTask {

    protected static final Log LOGGER = LogFactory.get();

    /**
     * 系统定时任务锁，防止多节点部署时同一任务重复执行
     */
    private static final String LOCK_KEY = "TASK_LOCK_{}";

    /**
     * 是否是Redis缓存，note：<br>
     * 只有缓存类型为REDIS时才会在任务执行前获取锁
     */
    private boolean isRedisCache;

    /**
     * 获取锁，默认10min后过期
     *
     * @param methodSignature 方法签名，格式：简单类名#方法名
     * @return 是否获取到锁，true：是 false：否
     */
    protected boolean acquireLock(String methodSignature) {
        return acquireLock(methodSignature, 10 * TimeUnitConst.MINUTE);
    }

    /**
     * 获取锁，note：
     * <pre>
     *   1.锁过期时间必须小于任务执行间隔，否则会阻止任务下一次正常执行
     *   2.锁过期时间必须大于第一个节点和最后一个节点启动的时间间隔，否则无法阻止任务重复执行
     * </pre>
     *
     * @param methodSignature 方法签名，格式：简单类名#方法名
     * @param ttl             锁过期时间（ms）
     * @return 是否获取到锁，true：是 false：否
     */
    protected boolean acquireLock(String methodSignature, long ttl) {
        if (isRedisCache) {
            return CacheServerFactory.getRedisCacheServer()
                    .setStringAbsent(StrUtil.format(LOCK_KEY, methodSignature), StrUtil.EMPTY, ttl);
        }
        return true;
    }

    /**
     * 开始消息
     *
     * @param methodSignature 方法签名
     * @param now             当前时间
     * @return 开始消息
     */
    protected String startMsg(String methodSignature, Date now) {
        return StrUtil.format("【system】【{}】开始执行：{}", methodSignature, now);
    }

    /**
     * 结束消息
     *
     * @param methodSignature 方法签名
     * @param now             当前时间
     * @param cost            耗时（ms）
     * @return 结束消息
     */
    protected String endMsg(String methodSignature, Date now, long cost) {
        return StrUtil.format("【system】【{}】执行结束：{}，耗时【{}】ms", methodSignature, now, cost);
    }

}
