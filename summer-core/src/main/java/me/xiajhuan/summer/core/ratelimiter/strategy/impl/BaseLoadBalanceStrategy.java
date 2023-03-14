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

package me.xiajhuan.summer.core.ratelimiter.strategy.impl;

import me.xiajhuan.summer.core.ratelimiter.strategy.LoadBalanceStrategy;

/**
 * 基本限流负载均衡策略（轮询）
 * <p>
 * realQps = setQps / nodeNum
 * note：最好能够整除，否则会存在精度问题，默认四舍五入保留2位小数
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class BaseLoadBalanceStrategy implements LoadBalanceStrategy {

    //*******************单例处理开始********************

    private static volatile BaseLoadBalanceStrategy instance = null;

    public static BaseLoadBalanceStrategy getInstance() {
        if (instance == null) {
            synchronized (BaseLoadBalanceStrategy.class) {
                if (instance == null) {
                    instance = new BaseLoadBalanceStrategy();
                }
            }
        }
        return instance;
    }

    private BaseLoadBalanceStrategy() {
    }

    //*******************单例处理结束********************

}
