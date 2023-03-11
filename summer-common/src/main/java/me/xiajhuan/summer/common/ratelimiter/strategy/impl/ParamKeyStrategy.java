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
 * <p>
 * 参数限流Key策略（Key为：接口签名#接口方法参数）
 * 例如：/server/common/api/open/demo[POST]#id=10001
 * </p>
 * note：入参数据量特别大时不建议使用
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class ParamKeyStrategy implements KeyStrategy {

    //*******************单例处理开始********************

    private static volatile ParamKeyStrategy instance = null;

    public static ParamKeyStrategy getInstance() {
        if (instance == null) {
            synchronized (ParamKeyStrategy.class) {
                if (instance == null) {
                    instance = new ParamKeyStrategy();
                }
            }
        }
        return instance;
    }

    private ParamKeyStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public String getRateLimiterKey(JoinPoint joinPoint, HttpServletRequest currentRequest, String currentUsername) {
        return StrUtil.format(StrTemplateConst.RATE_LIMITER_KEY, HttpContextUtil.getInterfaceSignature(currentRequest), HttpContextUtil.getParamValues(joinPoint, currentRequest));
    }

    public String limitMsgTemplate() {
        return "，Key-ParamValues【{}】";
    }

}
