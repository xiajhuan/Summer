package me.xiajhuan.summer.common.ratelimiter.strategy.impl;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 默认限流Key策略（Key为：接口签名#），
 * 例如：/server/common/api/open/demo[POST]#
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class DefaultKeyStrategy implements KeyStrategy {

    //*******************单例处理开始********************

    private static volatile DefaultKeyStrategy instance = null;

    public static DefaultKeyStrategy getInstance() {
        if (instance == null) {
            synchronized (DefaultKeyStrategy.class) {
                if (instance == null) {
                    instance = new DefaultKeyStrategy();
                }
            }
        }
        return instance;
    }

    private DefaultKeyStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public String getRateLimiterKey(JoinPoint joinPoint, HttpServletRequest currentRequest, String currentUsername) {
        return StrUtil.format(StrTemplateConst.RATE_LIMITER_KEY, HttpContextUtil.getInterfaceSignature(currentRequest), StrUtil.EMPTY);
    }

}
