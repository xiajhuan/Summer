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
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 切入点工具
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class JoinPointUtil {

    /**
     * 获取切入点方法
     *
     * @param point {@link JoinPoint}
     * @return {@link Method} 或 {@code null}
     */
    public static Method getMethod(JoinPoint point) {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            return ((MethodSignature) signature).getMethod();
        }
        return null;
    }

    /**
     * 获取切入点方法签名
     * <p>
     * 格式：Xxx.xxx(Xxx,Xxx)，简单类名例如：(String,Integer)，
     * 全限定性类名例如：(java.lang.String,java.lang.Integer)
     * </p>
     *
     * @param point    {@link JoinPoint}
     * @param isSimple 是否是简单类名，true：是 false：不是
     * @return 切入点方法签名 或 {@code null}
     */
    public static String getMethodSignature(JoinPoint point, boolean isSimple) {
        Method method = getMethod(point);

        if (method != null) {
            final Stream<Class> paramStream = Arrays.stream(method.getParameterTypes());
            String paramType = isSimple ?
                    paramStream.map(Class::getSimpleName).collect(Collectors.joining(StrPool.COMMA)) :
                    paramStream.map(Class::getName).collect(Collectors.joining(StrPool.COMMA));

            return StrUtil.format("{}.{}({})", point.getTarget().getClass().getName(),
                    method.getName(), paramType);
        }
        return null;
    }

}
