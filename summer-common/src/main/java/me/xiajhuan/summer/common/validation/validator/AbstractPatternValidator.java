package me.xiajhuan.summer.common.validation.validator;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.validation.validator.base.BasePatternValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;

/**
 * 正则校验器基类<br>
 * 子类需实现 {@link AbstractPatternValidator#initialize(Annotation)}
 *
 * @author xiajhuan
 * @date 2023/3/4
 * @see ConstraintValidator
 */
public abstract class AbstractPatternValidator<A extends Annotation> extends BasePatternValidator implements ConstraintValidator<A, String> {

    @Override
    public abstract void initialize(A constraintAnnotation);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!required && StrUtil.isBlank(value)) {
            return true;
        }
        if (StrUtil.isBlank(value)) {
            return false;
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
