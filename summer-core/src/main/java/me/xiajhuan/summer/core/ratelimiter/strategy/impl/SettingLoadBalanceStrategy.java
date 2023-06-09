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

package me.xiajhuan.summer.core.ratelimiter.strategy.impl;

import me.xiajhuan.summer.core.ratelimiter.strategy.LoadBalanceStrategy;

/**
 * <p>
 * 配置限流负载均衡策略，不会实例化，
 * 标识将使用core.setting下RateLimiter组下strategy.load-balance配置的策略
 * </p>
 *
 * @author xiajhuan
 * @date 2023/3/14
 */
public class SettingLoadBalanceStrategy implements LoadBalanceStrategy {

    /**
     * 不允许实例化
     */
    private SettingLoadBalanceStrategy() {
    }

    @Override
    public double calRealQps(double setQps, int nodeNum) {
        throw new UnsupportedOperationException("不允许调用配置策略类的方法");
    }

}
