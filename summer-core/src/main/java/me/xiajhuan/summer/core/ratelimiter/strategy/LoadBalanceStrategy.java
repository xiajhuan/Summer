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

import java.math.BigDecimal;

/**
 * 限流负载均衡策略<br>
 * note：通过实现该接口覆写 {@link LoadBalanceStrategy#calRealQps} 可以个性化自己的限流负载均衡策略
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public interface LoadBalanceStrategy {

    /**
     * 计算当前服务节点实际的Qps
     *
     * @param setQps  设置的Qps
     * @param nodeNum 节点数
     * @return 实际的Qps
     */
    default double calRealQps(double setQps, int nodeNum) {
        // Polling Strategy -> realQps = setQps / nodeNum
        return BigDecimal.valueOf(setQps / nodeNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
