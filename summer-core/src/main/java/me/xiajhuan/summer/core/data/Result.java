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

package me.xiajhuan.summer.core.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import me.xiajhuan.summer.core.exception.code.ErrorCode;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
@Setter
@Getter
@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Result() {
    }

    /**
     * 响应是否成功，成功：“1” 失败：“0”
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功（不带数据），默认消息：“操作成功”
     *
     * @return 响应结果
     */
    public static Result ofSuccess() {
        return ofSuccess(LocaleUtil.getI18nMessage(SuccessCode.OPERATION));
    }

    /**
     * 成功（带数据）（不带消息）
     *
     * @param data 响应数据
     * @param <T>  响应数据类型
     * @return 响应结果
     */
    public static <T> Result<T> ofSuccess(T data) {
        return ofSuccess(data, null);
    }

    /**
     * 成功（不带数据）（消息国际化）<br>
     * note：预留方法，需自定义成功消息的国际化编码，如：{@link SuccessCode#OPERATION}
     *
     * @param code  成功编码
     * @param param 消息填充参数
     * @return 响应结果
     */
    public static Result ofSuccess(int code, String... param) {
        return ofSuccess(null, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 成功（不带数据）（带消息）
     *
     * @param msg 消息
     * @return 响应结果
     */
    public static Result ofSuccess(String msg) {
        return ofSuccess(null, msg);
    }

    /**
     * 成功（带数据）（消息国际化）<br>
     * note：预留方法，需自定义成功消息的国际化编码，如：{@link SuccessCode#OPERATION}
     *
     * @param data  响应数据
     * @param code  成功编码
     * @param param 消息填充参数
     * @param <T>   响应数据类型
     * @return 响应结果
     */
    public static <T> Result<T> ofSuccess(T data, int code, String... param) {
        return ofSuccess(data, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 成功（带数据）（带消息）
     *
     * @param data 响应数据
     * @param msg  消息
     * @param <T>  响应数据类型
     * @return 响应结果
     */
    public static <T> Result<T> ofSuccess(T data, String msg) {
        Result<T> result = new Result();
        result.setCode("1");
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败（不带数据），默认消息：“操作失败”
     *
     * @return 响应结果
     */
    public static Result ofFail() {
        return ofFail(ErrorCode.OPERATION_FAILURE);
    }

    /**
     * 失败（不带数据）（消息国际化）
     *
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 响应结果
     */
    public static Result ofFail(int code, String... param) {
        return ofFail(null, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 失败（不带数据）（带消息）
     *
     * @param msg 消息
     * @return 响应结果
     */
    public static Result ofFail(String msg) {
        return ofFail(null, msg);
    }

    /**
     * 失败（带数据）（消息国际化）
     *
     * @param data  响应数据
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @param <T>   响应数据类型
     * @return 响应结果
     */
    public static <T> Result<T> ofFail(T data, int code, String... param) {
        return ofFail(data, LocaleUtil.getI18nMessage(code, param));
    }

    /**
     * 失败（带数据）（带消息）
     *
     * @param data 响应数据
     * @param msg  消息
     * @param <T>  响应数据类型
     * @return 响应结果
     */
    public static <T> Result<T> ofFail(T data, String msg) {
        Result<T> result = new Result();
        result.setCode("0");
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    /**
     * 成功代码
     */
    public static class SuccessCode {

        /**
         * 操作成功
         */
        public static final int OPERATION = 200;

    }

}
