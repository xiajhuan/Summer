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
 * 基本限流Key策略（Key为：接口签名#），
 * 例如：/summer/security/user/page[GET]#
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class BaseKeyStrategy implements KeyStrategy {

    //*******************单例处理开始********************

    private static volatile BaseKeyStrategy instance = null;

    public static BaseKeyStrategy getInstance() {
        if (instance == null) {
            synchronized (BaseKeyStrategy.class) {
                if (instance == null) {
                    instance = new BaseKeyStrategy();
                }
            }
        }
        return instance;
    }

    private BaseKeyStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public String getKey(JoinPoint point, HttpServletRequest request, String username) {
        return StrUtil.format(KEY_FORMAT, ServletUtil.getInterfaceSignature(request), StrUtil.EMPTY);
    }

    @Override
    public String extraMsgTemplate() {
        return StrUtil.EMPTY;
    }

}
