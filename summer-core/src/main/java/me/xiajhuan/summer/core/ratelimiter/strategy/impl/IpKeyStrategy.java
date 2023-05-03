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

package me.xiajhuan.summer.core.ratelimiter.strategy.impl;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.core.utils.ServletUtil;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * ip限流Key策略，Key为：接口签名#调用者ip，
 * 例如：/summer/security/user/page[GET]#192.168.1.100
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
    public String getKey(JoinPoint point, HttpServletRequest request, String username) {
        return StrUtil.format(KEY_FORMAT, ServletUtil.getInterfaceSignature(request), ServletUtil.getClientIP(request));
    }

    @Override
    public String extraMsgTemplate() {
        return "，Key-IP【{}】";
    }

}
