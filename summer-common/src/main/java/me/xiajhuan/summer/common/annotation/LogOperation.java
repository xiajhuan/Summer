package me.xiajhuan.summer.common.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;

/**
 * 操作日志注解<br>
 * note：添加了 {@link AliasFor} 必须通过 {@link AnnotationUtils} 获取，才会生效
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {

    @AliasFor("name")
    String value() default StrUtil.EMPTY;

    /**
     * 操作名称
     */
    @AliasFor("value")
    String name() default StrUtil.EMPTY;

    /**
     * 是否保存请求参数，true：保存 false：不保存
     */
    boolean isSaveRequestData() default true;

}
