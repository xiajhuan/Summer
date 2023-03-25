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

package me.xiajhuan.summer.core.ratelimiter.strategy;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import me.xiajhuan.summer.core.ratelimiter.aspect.RateLimiterAspect;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流Key策略
 * <p>
 * note1：通过实现该接口覆写 {@link KeyStrategy#getKey} 可以个性化自己的限流Key策略
 * note2：所有Key必须以“接口签名#”作为前缀，接口签名见：
 * {@link HttpContextUtil#getInterfaceSignature(HttpServletRequest)}
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/4
 */
public interface KeyStrategy {

    /**
     * Key格式
     */
    String FORMAT = "{}#{}";

    /**
     * 获取限流key<br>
     * note：这里为了切面 {@link RateLimiterAspect} 代码通用必须包含3个参数（固定写法）
     *
     * @param point    {@link JoinPoint}
     * @param request  {@link HttpServletRequest}
     * @param username 用户名
     * @return 限流Key
     */
    default String getKey(JoinPoint point, HttpServletRequest request, String username) {
        return StrUtil.format(FORMAT, HttpContextUtil.getInterfaceSignature(request), StrUtil.EMPTY);
    }

    /**
     * 限流日志附加信息模板钩子<br>
     * 格式：，Key-xxx【{}】，如：，Key-IP【{}】
     *
     * @return 附加信息模板
     */
    default String extraMsgTemplate() {
        return StrUtil.EMPTY;
    }

}
