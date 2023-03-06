package me.xiajhuan.summer.common.ratelimiter.annotation;

import me.xiajhuan.summer.common.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.LoadBalanceStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.DefaultLoadBalanceStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.DefaultKeyStrategy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;

/**
 * 限流注解<br>
 * note：添加了 {@link AliasFor} 必须通过 {@link AnnotationUtils} 获取，才会生效
 *
 * @author xiajhuan
 * @date 2022/12/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    int NOT_LIMITED = 0;

    @AliasFor("qps")
    double value() default NOT_LIMITED;

    /**
     * query per second
     */
    @AliasFor("value")
    double qps() default NOT_LIMITED;

    /**
     * 限流key策略Class，默认策略 {@link DefaultKeyStrategy}
     */
    Class<? extends KeyStrategy> keyStrategy() default DefaultKeyStrategy.class;

    /**
     * 限流负载均衡策略Class，默认策略 {@link DefaultLoadBalanceStrategy}
     */
    Class<? extends LoadBalanceStrategy> loadBalanceStrategy() default DefaultLoadBalanceStrategy.class;

    /**
     * 服务节点数
     */
    int nodeNum() default 1;

    /**
     * 尝试获取令牌的超时时长（ms）<br>
     * note：默认为0，则只尝试获取一次，大于0时如果获取不到则自旋至超时
     */
    long timeout() default 0;

}
