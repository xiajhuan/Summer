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

package me.xiajhuan.summer.core.constant;

/**
 * 字符串模板常量
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
public class StrTemplateConst {

    /**
     * 响应媒体格式
     */
    public static final String MEDIA_TYPE = "{};{}";

    /**
     * 接口操作名称
     */
    public static final String OPERATION_NAME = "【{}】{}";

    /**
     * 限流Key
     */
    public static final String RATE_LIMITER_KEY = "{}#{}";

    /**
     * 限流策略Class全限定性类名
     */
    public static final String RATE_LIMITER_STRATEGY_CLASS = "me.xiajhuan.summer.core.ratelimiter.strategy.impl.{}";

    /**
     * 匹配失败消息
     */
    public static final String MATCHES_FAIL_MSG = "{} matches {} failed!";

}
