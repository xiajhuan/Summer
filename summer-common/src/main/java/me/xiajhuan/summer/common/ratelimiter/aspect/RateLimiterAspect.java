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

package me.xiajhuan.summer.common.ratelimiter.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.enums.DefaultUserEnum;
import me.xiajhuan.summer.common.exception.BusinessException;
import me.xiajhuan.summer.common.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.common.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.LoadBalanceStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.StrategyFactory;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import me.xiajhuan.summer.common.utils.ReflectUtil;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 限流切面，基于Guava-RateLimiter
 *
 * @author xiajhuan
 * @date 2022/12/1
 * @see com.google.common.util.concurrent.RateLimiter
 */
@Aspect
@Component
public class RateLimiterAspect {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 限流规则缓存
     * <p>
     * Key：限流策略Key
     * Value：{@link com.google.common.util.concurrent.RateLimiter}
     * </p>
     */
    private static final ConcurrentMap<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    /**
     * 切入点
     */
    @Pointcut("@annotation(me.xiajhuan.summer.common.ratelimiter.annotation.RateLimiter)")
    public void pointcut() {
    }

    /**
     * 前置通知
     *
     * @param point {@link JoinPoint}
     * @throws BusinessException 业务异常
     */
    @Before("pointcut()")
    public void before(JoinPoint point) throws BusinessException {
        // 获取切入点方法上的RateLimiter注解
        RateLimiter rateLimiter = AnnotationUtils.findAnnotation(ReflectUtil.getMethod(point), RateLimiter.class);

        if (rateLimiter != null && rateLimiter.qps() > RateLimiter.NOT_LIMITED) {
            // 请求
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

            //*******************限流Key获取********************

            // 限流key策略Class
            Class<? extends KeyStrategy> keyStrategyClass = rateLimiter.keyStrategy();
            // 限流key策略实例
            final KeyStrategy keyStrategy;
            // 限流Key
            String rateLimiterKey;
            // 附加信息模板
            String msgTemplate = null;

            try {
                // 获取限流key策略实例
                keyStrategy = StrategyFactory.getInstance().getKeyStrategy(keyStrategyClass);

                rateLimiterKey = keyStrategy.getRateLimiterKey(point, request, SecurityUtil.getCurrentUsername(DefaultUserEnum.THIRD_PART.getValue()));

                msgTemplate = keyStrategy.limitMsgTemplate();
            } catch (Exception e) {
                LOGGER.error(e, "key-Class【{}】获取Key失败，自动切换为默认Key策略，请参照【DefaultKeyStrategy】编写", keyStrategyClass.getSimpleName());

                rateLimiterKey = StrUtil.format(StrTemplateConst.RATE_LIMITER_KEY, HttpContextUtil.getInterfaceSignature(request), StrUtil.EMPTY);
            }

            //*******************实际Qps获取********************

            // 设置的Qps
            double setQps = rateLimiter.qps();
            // 服务节点数
            int nodeNum = rateLimiter.nodeNum();
            // 限流负载均衡策略Class
            Class<? extends LoadBalanceStrategy> loadBalanceStrategyClass = rateLimiter.loadBalanceStrategy();
            // 限流负载均衡策略实例
            final LoadBalanceStrategy loadBalanceStrategy;
            // 实际的Qps
            double realQps;

            try {
                // 获取限流负载均衡策略实例
                loadBalanceStrategy = StrategyFactory.getInstance().getLoadBalanceStrategy(loadBalanceStrategyClass);

                realQps = loadBalanceStrategy.calRealQpsBySetQpsAndNodeNum(setQps, nodeNum);
            } catch (Exception e) {
                LOGGER.error(e, "LoadBalance-Class【{}】获取realQps失败，自动切换为默认负载均衡策略，请参照【DefaultLoadBalanceStrategy】编写", loadBalanceStrategyClass.getSimpleName());

                realQps = BigDecimal.valueOf(setQps / nodeNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            //*******************限流处理********************

            if (RATE_LIMITER_CACHE.get(rateLimiterKey) == null) {
                // 初始化QPS
                RATE_LIMITER_CACHE.put(rateLimiterKey, com.google.common.util.concurrent.RateLimiter.create(realQps));
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("接口【{}[{}]】设置的QPS为：【{}】，当前服务节点实际的QPS为：【{}】，key-Class【{}】，LoadBalance-Class【{}】{}",
                        request.getRequestURI(), request.getMethod(), setQps, RATE_LIMITER_CACHE.get(rateLimiterKey).getRate(),
                        keyStrategyClass.getSimpleName(), loadBalanceStrategyClass.getSimpleName(),
                        StrUtil.isNotBlank(msgTemplate) ? StrUtil.format(msgTemplate, StrUtil.subAfter(rateLimiterKey, "#", true)) : StrUtil.EMPTY);
            }

            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(rateLimiterKey) != null && !RATE_LIMITER_CACHE.get(rateLimiterKey).tryAcquire(rateLimiter.timeout(), TimeUnit.MILLISECONDS)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("接口【{}[{}]】限流成功，key-Class【{}】，LoadBalance-Class【{}】{}",
                            request.getRequestURI(), request.getMethod(), keyStrategyClass.getSimpleName(), loadBalanceStrategyClass.getSimpleName(),
                            StrUtil.isNotBlank(msgTemplate) ? StrUtil.format(msgTemplate, StrUtil.subAfter(rateLimiterKey, "#", true)) : StrUtil.EMPTY);
                }

                throw BusinessException.of("服务器繁忙，请稍后再试~");
            }
        }
    }

}
