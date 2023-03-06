package me.xiajhuan.summer.common.ratelimiter.strategy.impl;


import me.xiajhuan.summer.common.ratelimiter.strategy.LoadBalanceStrategy;

import java.math.BigDecimal;

/**
 * 默认限流负载均衡策略（轮询）
 * <p>
 * realQps = setQps / nodeNum
 * note：最好能够整除，否则会存在精度问题，默认四舍五入保留2位小数
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class DefaultLoadBalanceStrategy implements LoadBalanceStrategy {

    //*******************单例处理开始********************

    private static volatile DefaultLoadBalanceStrategy instance = null;

    public static DefaultLoadBalanceStrategy getInstance() {
        if (instance == null) {
            synchronized (DefaultLoadBalanceStrategy.class) {
                if (instance == null) {
                    instance = new DefaultLoadBalanceStrategy();
                }
            }
        }
        return instance;
    }

    private DefaultLoadBalanceStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public double calRealQpsBySetQpsAndNodeNum(double setQps, int nodeNum) {
        // Polling Strategy -> realQps = setQps / nodeNum
        return BigDecimal.valueOf(setQps / nodeNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
