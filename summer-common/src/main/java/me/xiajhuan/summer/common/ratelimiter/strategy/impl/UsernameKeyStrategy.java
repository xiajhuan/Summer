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

package me.xiajhuan.summer.common.ratelimiter.strategy.impl;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * 当前用户名限流Key策略（Key为：接口签名#接口调用者username（当前登录用户名或thirdPart（第三方用户）））<br>
 * <pre>
 * 例如：
 *     /server/common/api/open/demo[POST]#16042
 *     /server/common/api/open/demo[POST]#thirdPart
 * </pre>
 *
 * @author xiajhuan
 * @date 2022/12/4
 */
public class UsernameKeyStrategy implements KeyStrategy {

    //*******************单例处理开始********************

    private static volatile UsernameKeyStrategy instance = null;

    public static UsernameKeyStrategy getInstance() {
        if (instance == null) {
            synchronized (UsernameKeyStrategy.class) {
                if (instance == null) {
                    instance = new UsernameKeyStrategy();
                }
            }
        }
        return instance;
    }

    private UsernameKeyStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public String getRateLimiterKey(JoinPoint joinPoint, HttpServletRequest currentRequest, String currentUsername) {
        return StrUtil.format(StrTemplateConst.RATE_LIMITER_KEY, HttpContextUtil.getInterfaceSignature(currentRequest), currentUsername);
    }

    @Override
    public String limitMsgTemplate() {
        return "，Key-Username【{}】";
    }

}
