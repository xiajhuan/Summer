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
