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

package me.xiajhuan.summer.core.ratelimiter.aspect;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.constant.StrTemplateConst;
import me.xiajhuan.summer.core.enums.DefaultUserEnum;
import me.xiajhuan.summer.core.exception.BusinessException;
import me.xiajhuan.summer.core.exception.ErrorCode;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.LoadBalanceStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.StrategyFactory;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.SettingKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.SettingLoadBalanceStrategy;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import me.xiajhuan.summer.core.utils.ReflectUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 限流切面，基于Guava-RateLimiter
 *
 * @author xiajhuan
 * @date 2022/12/1
 * @see com.google.common.util.concurrent.RateLimiter#create(double)
 * @see com.google.common.util.concurrent.RateLimiter#tryAcquire(long, TimeUnit)
 */
@Aspect
@Component
public class RateLimiterAspect {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingBeanConst.CORE)
    private Setting setting;

    /**
     * 默认限流key策略Class
     */
    private static Class<? extends KeyStrategy> defaultKeyStrategy = null;

    /**
     * 默认限流负载均衡策略Class
     */
    private static Class<? extends LoadBalanceStrategy> defaultLoadBalanceStrategy = null;

    /**
     * 默认服务节点数
     */
    private static int defaultNodeNum;

    /**
     * 默认尝试获取令牌的超时时长（ms）
     */
    private static long defaultTimeout;

    /**
     * 初始化 {@link defaultKeyStrategy} {@link defaultLoadBalanceStrategy} <br>
     * {@link defaultNodeNum} {@link defaultTimeout}
     */
    @PostConstruct
    private void init() throws ClassNotFoundException {
        String keySetting = setting.getByGroupWithLog("strategy.key", "RateLimiter");
        if (StrUtil.isBlank(keySetting)) {
            // 没有配置则默认为：BaseKeyStrategy
            keySetting = "BaseKeyStrategy";
        }
        defaultKeyStrategy = (Class<? extends KeyStrategy>) Class.forName(StrUtil.format(StrTemplateConst.RATE_LIMITER_STRATEGY_CLASS, keySetting));

        String loadBalanceSetting = setting.getByGroupWithLog("strategy.load-balance", "RateLimiter");
        if (StrUtil.isBlank(loadBalanceSetting)) {
            // 没有配置则默认为：BaseLoadBalanceStrategy
            loadBalanceSetting = "BaseLoadBalanceStrategy";
        }
        defaultLoadBalanceStrategy = (Class<? extends LoadBalanceStrategy>) Class.forName(StrUtil.format(StrTemplateConst.RATE_LIMITER_STRATEGY_CLASS, loadBalanceSetting));

        defaultNodeNum = setting.getInt("node-num", "RateLimiter", 1);

        defaultTimeout = setting.getLong("timeout", "RateLimiter", 0L);
    }

    /**
     * 限流规则缓存
     * <p>
     * Key：限流策略Key
     * Value：{@link com.google.common.util.concurrent.RateLimiter}
     * </p>
     */
    private static final ConcurrentMap<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE = MapUtil.newConcurrentHashMap();

    /**
     * 切入点
     */
    @Pointcut("@annotation(me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter)")
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
            // 注解中设置的策略
            Class<? extends KeyStrategy> keyStrategyClass = rateLimiter.keyStrategy();
            if (keyStrategyClass == SettingKeyStrategy.class) {
                // 没有设置或设置为SettingKeyStrategy则使用配置中的策略
                keyStrategyClass = defaultKeyStrategy;
            }

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
                LOGGER.error(e, "key-Class【{}】获取Key失败，自动切换为基本Key策略，请参照【IpKeyStrategy】编写", keyStrategyClass.getSimpleName());

                rateLimiterKey = StrUtil.format(StrTemplateConst.RATE_LIMITER_KEY, HttpContextUtil.getInterfaceSignature(request), StrUtil.EMPTY);
            }

            //*******************实际Qps获取********************

            // 设置的Qps
            double setQps = rateLimiter.qps();

            // 服务节点数
            // 注解中设置的节点数
            int nodeNum = rateLimiter.nodeNum();
            if (nodeNum <= 0) {
                // 没有设置或设置的值小于等于0则使用配置中的节点数
                nodeNum = defaultNodeNum;
            }

            // 限流负载均衡策略Class
            // 注解中设置的策略
            Class<? extends LoadBalanceStrategy> loadBalanceStrategyClass = rateLimiter.loadBalanceStrategy();
            if (loadBalanceStrategyClass == SettingLoadBalanceStrategy.class) {
                // 没有设置或设置为SettingLoadBalanceStrategy则使用配置中的策略
                loadBalanceStrategyClass = defaultLoadBalanceStrategy;
            }

            // 限流负载均衡策略实例
            final LoadBalanceStrategy loadBalanceStrategy;
            // 实际的Qps
            double realQps;

            try {
                // 获取限流负载均衡策略实例
                loadBalanceStrategy = StrategyFactory.getInstance().getLoadBalanceStrategy(loadBalanceStrategyClass);

                realQps = loadBalanceStrategy.calRealQpsBySetQpsAndNodeNum(setQps, nodeNum);
            } catch (Exception e) {
                LOGGER.error(e, "LoadBalance-Class【{}】获取realQps失败，自动切换为基本负载均衡策略，请参照【BaseLoadBalanceStrategy】编写", loadBalanceStrategyClass.getSimpleName());

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
            // 注解中设置的超时时长
            long timeout = rateLimiter.timeout();
            if (timeout < 0L) {
                // 没有设置或设置的值小于0则使用配置中的超时时长
                timeout = defaultTimeout;
            }
            if (RATE_LIMITER_CACHE.get(rateLimiterKey) != null && !RATE_LIMITER_CACHE.get(rateLimiterKey).tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("接口【{}[{}]】限流成功，key-Class【{}】，LoadBalance-Class【{}】{}",
                            request.getRequestURI(), request.getMethod(), keyStrategyClass.getSimpleName(), loadBalanceStrategyClass.getSimpleName(),
                            StrUtil.isNotBlank(msgTemplate) ? StrUtil.format(msgTemplate, StrUtil.subAfter(rateLimiterKey, "#", true)) : StrUtil.EMPTY);
                }

                throw BusinessException.of(ErrorCode.SERVER_BUSY);
            }
        }
    }

}
