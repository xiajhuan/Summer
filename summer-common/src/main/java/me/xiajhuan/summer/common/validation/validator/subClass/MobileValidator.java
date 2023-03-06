package me.xiajhuan.summer.common.validation.validator.subClass;

import me.xiajhuan.summer.common.validation.annotation.Mobile;
import me.xiajhuan.summer.common.validation.validator.AbstractPatternValidator;

import java.util.regex.Pattern;

/**
 * 手机号校验器
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
public class MobileValidator extends AbstractPatternValidator<Mobile> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
        required = constraintAnnotation.required();
        pattern = Pattern.compile(constraintAnnotation.region().getValue());
    }

}
