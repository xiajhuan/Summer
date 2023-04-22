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
 * 系统异常<br>
 * note："core/system"模块专用，"admin"模块请使用 {@link BusinessException}
 *
 * @author xiajhuan
 * @date 2023/4/22
 */
public class SystemException extends RuntimeException {

    /**
     * 构造系统异常<br>
     * note：只有作为原始异常抛出时使用，不要丢弃原始异常！
     *
     * @param message 消息
     */
    private SystemException(String message) {
        super(message);
    }

    /**
     * 构造系统异常<br>
     * note：捕获其他异常需要再抛出时使用，包装原始异常
     *
     * @param message 消息
     * @param cause   原始异常
     */
    private SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构建系统异常（作为原始异常），默认消息：”服务器内部异常“
     *
     * @return 系统异常
     */
    public static SystemException of() {
        return of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 构建系统异常（包装原始异常），默认消息：”服务器内部异常“
     *
     * @param cause 原始异常
     * @return 系统异常
     */
    public static SystemException of(Throwable cause) {
        return of(cause, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 构建系统异常（作为原始异常），消息国际化
     *
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 系统异常
     */
    public static SystemException of(int code, String... param) {
        return of(LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 构建系统异常（包装原始异常），消息国际化
     *
     * @param cause 原始异常
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 系统异常
     */
    public static SystemException of(Throwable cause, int code, String... param) {
        return of(cause, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 构建系统异常（作为原始异常）
     *
     * @param message 消息
     * @return 系统异常
     */
    public static SystemException of(String message) {
        return new SystemException(message);
    }

    /**
     * 构建系统异常（包装原始异常）
     *
     * @param cause   原始异常
     * @param message 消息
     * @return 系统异常
     */
    public static SystemException of(Throwable cause, String message) {
        return new SystemException(message, cause);
    }

}
