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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Aop工具
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class AopUtil {

    /**
     * 获取切入点方法
     *
     * @param point {@link JoinPoint}
     * @return {@link Method}
     */
    public static Method getMethod(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getMethod();
    }

    /**
     * 获取切入点方法签名（全限定性类名）
     * <p>
     * 格式：Xxx.xxx(Xxx,Xxx)，
     * 全限定性类名例如：java.lang.String
     * </p>
     *
     * @param point {@link JoinPoint}
     * @return 切入点方法签名
     */
    public static String getMethodSignature(JoinPoint point) {
        Method method = getMethod(point);

        String paramType = Arrays.stream(method.getParameterTypes()).map(Class::getName)
                .collect(Collectors.joining(StrPool.COMMA));

        return StrUtil.format("{}.{}({})", point.getTarget().getClass().getName(),
                method.getName(), paramType);
    }

    /**
     * 获取切入点方法签名（简单类名）
     * <p>
     * 格式：Xxx.xxx(Xxx,Xxx)，
     * 简单类名例如：String
     * </p>
     *
     * @param point {@link JoinPoint}
     * @return 切入点方法签名
     */
    public static String getMethodSignatureSimple(JoinPoint point) {
        Method method = getMethod(point);

        String paramType = Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName)
                .collect(Collectors.joining(StrPool.COMMA));

        return StrUtil.format("{}.{}({})", point.getTarget().getClass().getSimpleName(),
                method.getName(), paramType);
    }

}
