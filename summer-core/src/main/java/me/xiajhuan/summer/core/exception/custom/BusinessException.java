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
 * 业务异常<br>
 * note："admin"模块专用，"core/system"模块请使用{@link SystemException}
 *
 * @author xiajhuan
 * @date 2022/11/26
 */
public class BusinessException extends RuntimeException {

    /**
     * 构造私有化<br>
     * note：只有作为原始异常抛出时使用，不要丢弃原始异常！
     *
     * @param message 消息
     */
    private BusinessException(String message) {
        super(message);
    }

    /**
     * 构造私有化<br>
     * note：捕获其他异常需要再抛出时使用，包装原始异常
     *
     * @param message 消息
     * @param cause   原始异常
     */
    private BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构建业务异常（作为原始异常），默认消息为”服务器内部异常“
     *
     * @return 业务异常
     */
    public static BusinessException of() {
        return of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 构建业务异常（包装原始异常），默认消息为”服务器内部异常“
     *
     * @param cause 原始异常
     * @return 业务异常
     */
    public static BusinessException of(Throwable cause) {
        return of(cause, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 构建业务异常（作为原始异常），消息国际化
     *
     * @param code  错误编码，参考{@link ErrorCode}
     * @param param 消息填充参数
     * @return 业务异常
     */
    public static BusinessException of(int code, String... param) {
        return of(LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 构建业务异常（包装原始异常），消息国际化
     *
     * @param cause 原始异常
     * @param code  错误编码，参考{@link ErrorCode}
     * @param param 消息填充参数
     * @return 业务异常
     */
    public static BusinessException of(Throwable cause, int code, String... param) {
        return of(cause, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 构建业务异常（作为原始异常）
     *
     * @param message 消息
     * @return 业务异常
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    /**
     * 构建业务异常（包装原始异常）
     *
     * @param cause   原始异常
     * @param message 消息
     * @return 业务异常
     */
    public static BusinessException of(Throwable cause, String message) {
        return new BusinessException(message, cause);
    }

}
