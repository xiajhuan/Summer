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
