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

import me.xiajhuan.summer.core.ratelimiter.strategy.KeyStrategy;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 配置限流Key策略，不会实例化，
 * 标识将使用core.setting下RateLimiter组下strategy.key配置的策略
 * </p>
 *
 * @author xiajhuan
 * @date 2023/3/14
 */
public class SettingKeyStrategy implements KeyStrategy {

    /**
     * 不允许实例化
     */
    private SettingKeyStrategy() {
    }

    @Override
    public String getKey(JoinPoint point, HttpServletRequest request, String username) {
        throw new UnsupportedOperationException("不允许调用配置策略类的方法");
    }

    @Override
    public String extraMsgTemplate() {
        throw new UnsupportedOperationException("不允许调用配置策略类的方法");
    }

}
