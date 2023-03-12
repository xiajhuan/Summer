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

import javax.servlet.http.HttpServletRequest;

/**
 * 限流Key策略
 * <p>
 * note1：通过实现该接口可以个性化自己的限流Key策略
 * note2：所有策略Key必须以“接口签名#”作为前缀，接口签名见：
 * {@link HttpContextUtil#getInterfaceSignature(HttpServletRequest)}
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/4
 */
public interface KeyStrategy {

    /**
     * 获取限流key<br>
     * note：这里为了切面中代码通用必须包含3个参数（固定写法，即使可能用不到）
     *
     * @param joinPoint       {@link JoinPoint}
     * @param currentRequest  {@link HttpServletRequest}
     * @param currentUsername 当前用户名
     * @return 限流Key
     */
    String getRateLimiterKey(JoinPoint joinPoint, HttpServletRequest currentRequest, String currentUsername);

    /**
     * 限流日志附加信息模板钩子<br>
     * 格式：，Key-xxx【{}】，如：，Key-IP【{}】
     *
     * @return 附加信息模板
     */
    default String limitMsgTemplate() {
        return StrUtil.EMPTY;
    }

}
