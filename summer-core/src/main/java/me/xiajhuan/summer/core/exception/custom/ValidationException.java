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

package me.xiajhuan.summer.core.exception.custom;

import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.utils.LocaleUtil;

/**
 * 校验异常
 *
 * @author xiajhuan
 * @date 2023/3/26
 */
public class ValidationException extends RuntimeException {

    /**
     * 构造校验异常
     *
     * @param message 消息
     */
    private ValidationException(String message) {
        super(message);
    }

    /**
     * 构建校验异常，消息国际化
     *
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 校验异常
     */
    public static ValidationException of(int code, String... param) {
        return of(LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 构建校验异常
     *
     * @param message 消息
     * @return 校验异常
     */
    public static ValidationException of(String message) {
        return new ValidationException(message);
    }

}
