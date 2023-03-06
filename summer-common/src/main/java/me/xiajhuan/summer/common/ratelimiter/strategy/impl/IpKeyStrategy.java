package me.xiajhuan.summer.common.ratelimiter.strategy.impl;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import me.xiajhuan.summer.common.utils.IpUtil;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * IP限流Key策略（Key为：接口签名#接口调用者IP），
 * 例如：/server/common/api/open/demo[POST]#192.168.1.100
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/4
 */
public class IpKeyStrategy implements KeyStrategy {

    //*******************单例处理开始********************

    private static volatile IpKeyStrategy instance = null;

    public static IpKeyStrategy getInstance() {
        if (instance == null) {
            synchronized (IpKeyStrategy.class) {
                if (instance == null) {
                    instance = new IpKeyStrategy();
                }
            }
        }
        return instance;
    }

    private IpKeyStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public String getRateLimiterKey(JoinPoint joinPoint, HttpServletRequest currentRequest, String currentUsername) {
        return StrUtil.format(StrTemplateConst.RATE_LIMITER_KEY, HttpContextUtil.getInterfaceSignature(currentRequest), IpUtil.getRequestOriginIp(currentRequest));
    }

    public String limitMsgTemplate() {
        return "，Key-IP【{}】";
    }

}
