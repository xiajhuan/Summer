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

/**
 * 限流负载均衡策略<br>
 * note：通过实现该接口可以个性化自己的限流负载均衡策略
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public interface LoadBalanceStrategy {

    /**
     * 计算当前节点实际的Qps
     *
     * @param setQps  设置的Qps
     * @param nodeNum 节点数
     * @return 实际的Qps
     */
    double calRealQpsBySetQpsAndNodeNum(double setQps, int nodeNum);

}
