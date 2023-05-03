/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.validation.validator;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

/**
 * 正则校验器基类<br>
 * 子类需实现{@link AbstractPatternValidator#initialize(Annotation)}
 *
 * @author xiajhuan
 * @date 2023/3/4
 * @see ConstraintValidator
 * @see Pattern
 */
public abstract class AbstractPatternValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    /**
     * 正则匹配器
     */
    protected Pattern pattern;

    @Override
    public abstract void initialize(A constraintAnnotation);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isNotBlank(value)) {
            // 不为空则做正则校验
            return pattern.matcher(value).matches();
        }
        return true;
    }

}
