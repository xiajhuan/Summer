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

package me.xiajhuan.summer.core.ratelimiter.annotation;

import me.xiajhuan.summer.core.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.LoadBalanceStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.SettingKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.SettingLoadBalanceStrategy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;

/**
 * 限流注解<br>
 * note：添加了 {@link AliasFor} 必须通过 {@link AnnotationUtils} 获取，才会生效
 * <p>
 * keyStrategy/loadBalanceStrategy/nodeNum/timeout的生效优先级为：
 * 注解中设置的 > core.setting中配置的
 * </p>
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
     * query per second<br>
     * note：必须大于0才能生效
     */
    @AliasFor("value")
    double qps() default NOT_LIMITED;

    /**
     * 限流key策略Class
     *
     * @see KeyStrategy
     */
    Class<? extends KeyStrategy> keyStrategy() default SettingKeyStrategy.class;

    /**
     * 限流负载均衡策略Class
     *
     * @see LoadBalanceStrategy
     */
    Class<? extends LoadBalanceStrategy> loadBalanceStrategy() default SettingLoadBalanceStrategy.class;

    /**
     * 服务节点数
     */
    int nodeNum() default 0;

    /**
     * 尝试获取令牌的超时时长（ms）<br>
     * note：为0，则只尝试获取一次，大于0时如果获取不到则自旋至超时
     */
    long timeout() default -1L;

}
