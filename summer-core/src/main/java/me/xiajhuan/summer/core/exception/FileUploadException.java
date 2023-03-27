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

package me.xiajhuan.summer.core.exception;

import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.utils.LocaleUtil;

/**
 * 文件上传异常
 *
 * @author xiajhuan
 * @date 2023/3/26
 */
public class FileUploadException extends RuntimeException {

    /**
     * 构造文件上传异常
     *
     * @param message 消息
     * @param cause   原始异常
     */
    private FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构建文件上传异常，默认消息：”文件上传失败“
     *
     * @param cause 原始异常
     * @return 文件上传异常
     */
    public static FileUploadException of(Throwable cause) {
        return of(cause, ErrorCode.FILE_UPLOAD_FAILURE);
    }

    /**
     * 构建文件上传异常，消息国际化
     *
     * @param cause 原始异常
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 文件上传异常
     */
    public static FileUploadException of(Throwable cause, int code, String... param) {
        return of(cause, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 构建文件上传异常
     *
     * @param cause   原始异常
     * @param message 消息
     * @return 文件上传异常
     */
    public static FileUploadException of(Throwable cause, String message) {
        return new FileUploadException(message, cause);
    }

}
