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

package me.xiajhuan.summer.core.ratelimiter.strategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 限流策略工厂
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class StrategyFactory {

    /**
     * 不允许实例化
     */
    private StrategyFactory() {
    }

    /**
     * 获取限流Key策略实例
     *
     * @param keyStrategyClass 限流key策略Class
     * @return 限流key策略实例
     * @throws NoSuchMethodException     如果没有相应方法
     * @throws InvocationTargetException 如果执行方法出现问题
     * @throws IllegalAccessException    如果没有访问权限
     */
    public static KeyStrategy getKeyStrategy(Class<? extends KeyStrategy> keyStrategyClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = keyStrategyClass.getMethod("getInstance");
        // 执行静态方法
        return (KeyStrategy) method.invoke(null);
    }

    /**
     * 获取限流负载均衡策略实例
     *
     * @param loadBalanceStrategy 限流负载均衡策略Class
     * @return 限流负载均衡策略实例
     * @throws NoSuchMethodException     如果没有相应方法
     * @throws InvocationTargetException 如果执行方法出现问题
     * @throws IllegalAccessException    如果没有访问权限
     */
    public static LoadBalanceStrategy getLoadBalanceStrategy(Class<? extends LoadBalanceStrategy> loadBalanceStrategy) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = loadBalanceStrategy.getMethod("getInstance");
        // 执行静态方法
        return (LoadBalanceStrategy) method.invoke(null);
    }

}
