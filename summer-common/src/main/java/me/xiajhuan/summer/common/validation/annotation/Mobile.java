package me.xiajhuan.summer.common.validation.annotation;

import me.xiajhuan.summer.common.enums.RegionMobileEnum;
import me.xiajhuan.summer.common.validation.validator.subClass.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号校验注解
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Mobile.List.class)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    String message() default "手机号格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否必填，true：必填 false：非必填
     */
    boolean required() default false;

    /**
     * 号码所在地区
     */
    RegionMobileEnum region() default RegionMobileEnum.ZH_CN;

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Mobile[] value();
    }

}
