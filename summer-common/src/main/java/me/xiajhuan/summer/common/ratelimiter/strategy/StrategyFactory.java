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

package me.xiajhuan.summer.common.ratelimiter.strategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 限流策略工厂
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class StrategyFactory {

    //*******************单例处理开始********************

    private StrategyFactory() {
    }

    private static volatile StrategyFactory factory = null;

    public static StrategyFactory getInstance() {
        if (factory == null) {
            synchronized (StrategyFactory.class) {
                if (factory == null) {
                    factory = new StrategyFactory();
                }
            }
        }
        return factory;
    }

    //*******************单例处理结束********************

    /**
     * 获取限流Key策略实例
     *
     * @param keyStrategyClass 限流key策略Class
     * @return 限流key策略实例
     * @throws NoSuchMethodException     没有方法异常
     * @throws InvocationTargetException 执行目标方法异常
     * @throws IllegalAccessException    非法访问异常
     */
    public KeyStrategy getKeyStrategy(Class<? extends KeyStrategy> keyStrategyClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = keyStrategyClass.getMethod("getInstance");
        // 执行静态方法
        return (KeyStrategy) method.invoke(null);
    }

    /**
     * 获取限流负载均衡策略实例
     *
     * @param loadBalanceStrategy 限流负载均衡策略Class
     * @return 限流负载均衡策略实例
     * @throws NoSuchMethodException     没有方法异常
     * @throws InvocationTargetException 执行目标方法异常
     * @throws IllegalAccessException    非法访问异常
     */
    public LoadBalanceStrategy getLoadBalanceStrategy(Class<? extends LoadBalanceStrategy> loadBalanceStrategy) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = loadBalanceStrategy.getMethod("getInstance");
        // 执行静态方法
        return (LoadBalanceStrategy) method.invoke(null);
    }

}
