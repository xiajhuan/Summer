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

package me.xiajhuan.summer.core.validation.validator.subClass;

import me.xiajhuan.summer.core.enums.LocaleSupportEnum;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import me.xiajhuan.summer.core.validation.annotation.Mobile;
import me.xiajhuan.summer.core.validation.validator.AbstractPatternValidator;

import java.util.Locale;
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
        // 获取地区语言
        Locale locale = LocaleUtil.getLocalePriority();
        switch (locale.toString()) {
            case "zh_CN":
                pattern = Pattern.compile(LocaleSupportEnum.ZH_CN.getMobileRegex());
                break;
            default:
                pattern = Pattern.compile(LocaleSupportEnum.EN_US.getMobileRegex());
        }
    }

}
