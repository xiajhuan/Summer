package me.xiajhuan.summer.common.utils;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 反射工具
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class ReflectUtil {

    /**
     * 获取切入点方法
     *
     * @param joinPoint {@link JoinPoint}
     * @return {@link Method}
     */
    public static Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    /**
     * 获取切入点方法签名（全限定性类名）
     * <p>
     * 格式：Xxx.xxx(Xxx,Xxx)，
     * 例如：(java.lang.String,java.lang.String)
     * </p>
     *
     * @param joinPoint {@link JoinPoint}
     * @return 切入点方法签名
     */
    public static String getMethodSignature(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);

        String paramTypeStr = Arrays.stream(method.getParameterTypes()).map(p -> p.getName())
                .collect(Collectors.joining(StrPool.COMMA, StrUtil.EMPTY, StrUtil.EMPTY));

        // 拼接格式：Xxx.xxx(Xxx,Xxx)
        return StrUtil.format("{}.{}({})", joinPoint.getTarget().getClass().getName(),
                method.getName(), paramTypeStr);
    }

    /**
     * 获取切入点方法签名（简单类名）
     * <p>
     * 格式：Xxx.xxx(Xxx,Xxx)，
     * 例如：(String,String)
     * </p>
     *
     * @param joinPoint {@link JoinPoint}
     * @return 切入点方法签名
     */
    public static String getMethodSignatureSimple(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);

        String paramTypeStr = Arrays.stream(method.getParameterTypes()).map(p -> p.getSimpleName())
                .collect(Collectors.joining(StrPool.COMMA, StrUtil.EMPTY, StrUtil.EMPTY));

        // 拼接格式：Xxx.xxx(Xxx,Xxx)
        return StrUtil.format("{}.{}({})", joinPoint.getTarget().getClass().getSimpleName(),
                method.getName(), paramTypeStr);
    }

}
