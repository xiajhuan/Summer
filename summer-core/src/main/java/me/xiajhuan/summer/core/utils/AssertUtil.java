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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.exception.BusinessException;
import me.xiajhuan.summer.core.exception.ErrorCode;

import java.util.Iterator;
import java.util.Map;

/**
 * 非空断言工具
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
public class AssertUtil {

    /**
     * <p>
     * 默认参数Key，用于填充断言失败时的业务异常消息，
     * 例如：”default不能为空“
     * </p>
     *
     * @see ErrorCode#NOT_NULL
     */
    private static final String DEFAULT_PARAM_KEY = "default";

    /**
     * 判空类型：blank<br>
     * 支持 {@link CharSequence}
     */
    private static final String BLANK = "blank";

    /**
     * 判空类型：empty<br>
     * 支持 {@link CharSequence}/{@link Iterable}/{@link Iterator}/{@code Array}/{@link Map}
     */
    private static final String EMPTY = "empty";

    /**
     * 判空类型：null<br>
     * 支持基本类型外的所有类型<br>
     * note：如果是字符串则判断不为：{@code null}、“null”、“undefined”
     */
    private static final String NULL = "null";

    /**
     * 非空断言（Blank），默认参数key：default
     *
     * @param paramValue 参数值
     * @throws BusinessException 业务异常
     */
    public static void isNotBlank(CharSequence paramValue) throws BusinessException {
        isNotBlank(DEFAULT_PARAM_KEY, paramValue);
    }

    /**
     * 非空断言（Blank）
     *
     * @param paramKey   参数Key
     * @param paramValue 参数值
     * @throws BusinessException 业务异常
     */
    public static void isNotBlank(String paramKey, CharSequence paramValue) throws BusinessException {
        paramValueAssert(paramKey, paramValue, BLANK);
    }

    /**
     * 非空断言（Empty），默认参数key：default
     *
     * @param paramValue 参数值
     * @throws BusinessException 业务异常
     */
    public static void isNotEmpty(Object paramValue) throws BusinessException {
        isNotEmpty(DEFAULT_PARAM_KEY, paramValue);
    }

    /**
     * 非空断言（Empty）
     *
     * @param paramKey   参数Key
     * @param paramValue 参数值
     * @throws BusinessException 业务异常
     */
    public static void isNotEmpty(String paramKey, Object paramValue) throws BusinessException {
        paramValueAssert(paramKey, paramValue, EMPTY);
    }

    /**
     * 非空断言（Null），默认参数key：default
     *
     * @param paramValue 参数值
     * @throws BusinessException 业务异常
     */
    public static void isNotNull(Object paramValue) throws BusinessException {
        isNotNull(DEFAULT_PARAM_KEY, paramValue);
    }

    /**
     * 非空断言（Null）
     *
     * @param paramKey   参数Key
     * @param paramValue 参数值
     * @throws BusinessException 业务异常
     */
    public static void isNotNull(String paramKey, Object paramValue) throws BusinessException {
        paramValueAssert(paramKey, paramValue, NULL);
    }

    /**
     * 参数值断言
     *
     * @param paramKey   参数Key
     * @param paramValue 参数值
     * @param judgeType  判空类型
     * @throws BusinessException 业务异常
     */
    private static void paramValueAssert(String paramKey, Object paramValue, String judgeType) throws BusinessException {
        switch (judgeType) {
            case BLANK:
                if (paramValue instanceof CharSequence) {
                    if (StrUtil.isBlank((CharSequence) paramValue)) {
                        throwBusinessException(paramKey);
                    }
                } else {
                    throwUnsupportedOperationException(paramValue.getClass().getName(), judgeType);
                }
                break;
            case EMPTY:
                if (paramValue instanceof CharSequence
                        || paramValue instanceof Iterable
                        || paramValue instanceof Iterator
                        || ArrayUtil.isArray(paramValue)
                        || paramValue instanceof Map) {
                    if (ObjectUtil.isEmpty(paramValue)) {
                        throwBusinessException(paramKey);
                    }
                } else {
                    throwUnsupportedOperationException(paramValue.getClass().getName(), judgeType);
                }
                break;
            default:
                if (paramValue == null) {
                    throwBusinessException(paramKey);
                }
        }
    }

    /**
     * 抛出 {@link UnsupportedOperationException}
     *
     * @param paramType 参数类型
     * @param judgeType 判空类型
     */
    private static void throwUnsupportedOperationException(String paramType, String judgeType) {
        throw new UnsupportedOperationException(StrUtil.format("类型【{}】不支持{}判空", paramType, judgeType));
    }

    /**
     * 抛出业务异常
     *
     * @param paramKey 参数Key
     * @throws BusinessException 业务异常
     */
    private static void throwBusinessException(String paramKey) throws BusinessException {
        throw BusinessException.of(ErrorCode.NOT_NULL, paramKey);
    }

}