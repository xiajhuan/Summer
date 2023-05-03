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

package me.xiajhuan.summer.core.ratelimiter.aspect;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.NonLoggedUserEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.LoadBalanceStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.StrategyFactory;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.SettingKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.SettingLoadBalanceStrategy;
import me.xiajhuan.summer.core.utils.JoinPointUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.core.utils.ServletUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 限流切面，基于Guava-RateLimiter
 *
 * @author xiajhuan
 * @date 2022/12/1
 * @see LFUCache
 * @see com.google.common.util.concurrent.RateLimiter#create(double)
 * @see com.google.common.util.concurrent.RateLimiter#tryAcquire(long, TimeUnit)
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@SuppressWarnings("all")
public class RateLimiterAspect {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingConst.CORE)
    private Setting setting;

    /**
     * 策略类全限定性类名格式
     */
    private static final String STRATEGY_CLASS_FORMAT = "me.xiajhuan.summer.core.ratelimiter.strategy.impl.{}";

    /**
     * 限流规则缓存，Key：限流策略Key Value：{@link com.google.common.util.concurrent.RateLimiter}<br>
     * note：超过缓存容量后会清除访问计数最小的规则
     */
    private LFUCache<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE;

    /**
     * 默认提示消息
     */
    private int defaultMsg;

    /**
     * 默认限流key策略Class
     */
    private Class<? extends KeyStrategy> defaultKeyStrategy;

    /**
     * 默认限流负载均衡策略Class
     */
    private Class<? extends LoadBalanceStrategy> defaultLoadBalanceStrategy;

    /**
     * 默认节点数
     */
    private int defaultNodeNum;

    /**
     * 默认尝试获取令牌的超时时长（ms）
     */
    private long defaultTimeout;

    /**
     * 初始化
     *
     * @throws ClassNotFoundException 如果类找不到
     */
    @PostConstruct
    private void init() throws ClassNotFoundException {
        RATE_LIMITER_CACHE = CacheUtil.newLFUCache(setting.getInt("key-num", "RateLimiter", 20000));

        defaultMsg = setting.getInt("msg", "RateLimiter", ErrorCode.FREQUENT_OPERATION);

        String keySetting = setting.getByGroupWithLog("strategy.key", "RateLimiter");
        if (StrUtil.isBlank(keySetting)) {
            // 没有配置则默认为：BaseKeyStrategy
            keySetting = "BaseKeyStrategy";
        }
        defaultKeyStrategy = (Class<? extends KeyStrategy>) Class.forName(StrUtil.format(STRATEGY_CLASS_FORMAT, keySetting));

        String loadBalanceSetting = setting.getByGroupWithLog("strategy.load-balance", "RateLimiter");
        if (StrUtil.isBlank(loadBalanceSetting)) {
            // 没有配置则默认为：BaseLoadBalanceStrategy
            loadBalanceSetting = "BaseLoadBalanceStrategy";
        }
        defaultLoadBalanceStrategy = (Class<? extends LoadBalanceStrategy>) Class.forName(StrUtil.format(STRATEGY_CLASS_FORMAT, loadBalanceSetting));

        defaultNodeNum = setting.getInt("node-num", "RateLimiter", 1);

        defaultTimeout = setting.getLong("timeout", "RateLimiter", 0L);
    }

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
     */
    @Before("pointcut()")
    public void before(JoinPoint point) {
        // 获取切入点方法上的RateLimiter注解
        Method method = JoinPointUtil.getMethod(point);
        if (method == null) {
            return;
        }
        RateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, RateLimiter.class);

        if (rateLimiter != null && rateLimiter.qps() > RateLimiter.NOT_LIMITED) {
            // 请求
            HttpServletRequest request = ServletUtil.getHttpRequest();
            if (request == null) {
                return;
            }

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
            // 附加消息模板
            String msgTemplate = null;

            try {
                // 获取限流key策略实例
                keyStrategy = StrategyFactory.getKeyStrategy(keyStrategyClass);

                rateLimiterKey = keyStrategy.getKey(point, request, SecurityUtil.getCurrentUsername(NonLoggedUserEnum.THIRD_PART.getValue()));

                msgTemplate = keyStrategy.extraMsgTemplate();
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOGGER.error(e, "key-Class【{}】获取Key失败，自动切换为基本Key策略，请参考【BaseKeyStrategy】编写", keyStrategyClass.getSimpleName());

                rateLimiterKey = StrUtil.format(KeyStrategy.KEY_FORMAT, ServletUtil.getInterfaceSignature(request), StrUtil.EMPTY);
                msgTemplate = StrUtil.EMPTY;
            }

            //*******************实际Qps获取********************

            // 设置的Qps
            double setQps = rateLimiter.qps();

            // 节点数
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
                loadBalanceStrategy = StrategyFactory.getLoadBalanceStrategy(loadBalanceStrategyClass);

                realQps = loadBalanceStrategy.calRealQps(setQps, nodeNum);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOGGER.error(e, "LoadBalance-Class【{}】获取realQps失败，自动切换为基本负载均衡策略，请参考【BaseLoadBalanceStrategy】编写", loadBalanceStrategyClass.getSimpleName());

                realQps = BigDecimal.valueOf(setQps / nodeNum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            //*******************限流处理********************

            com.google.common.util.concurrent.RateLimiter limiter = RATE_LIMITER_CACHE.get(rateLimiterKey);
            if (limiter == null) {
                // 初始化Qps
                limiter = com.google.common.util.concurrent.RateLimiter.create(realQps);
                RATE_LIMITER_CACHE.put(rateLimiterKey, limiter);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("接口【{}[{}]】设置Qps为：【{}】，当前节点实际Qps为：【{}】，key-Class【{}】，LoadBalance-Class【{}】{}",
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
            if (!limiter.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("接口【{}[{}]】限流成功，key-Class【{}】，LoadBalance-Class【{}】{}",
                            request.getRequestURI(), request.getMethod(), keyStrategyClass.getSimpleName(), loadBalanceStrategyClass.getSimpleName(),
                            StrUtil.isNotBlank(msgTemplate) ? StrUtil.format(msgTemplate, StrUtil.subAfter(rateLimiterKey, "#", true)) : StrUtil.EMPTY);
                }

                // 注解中设置的提示消息
                int message = rateLimiter.msg();
                if (message < 0) {
                    // 没有设置或设置的值小于0则使用配置中的提示消息
                    message = defaultMsg;
                }
                throw SystemException.of(message);
            }
        }
    }

}
