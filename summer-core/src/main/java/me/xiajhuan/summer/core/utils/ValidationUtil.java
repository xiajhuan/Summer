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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * 校验工具
 *
 * @author xiajhuan
 * @date 2022/3/26
 */
public class ValidationUtil {

    /**
     * 构造ValidationUtil
     */
    private ValidationUtil() {
    }

    /**
     * {@link Validator}
     */
    private static Validator validator;

    /**
     * 初始化 {@link validator}
     */
    static {
        validator = SpringUtil.getBean("getValidator", Validator.class);
    }

    /**
     * 校验List<Dto>
     *
     * @param dtoList Dto类型列表
     * @param group   校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>     Dto类型
     */
    public static <D> void validate(List<D> dtoList, Class<?>... group) {
        validate(dtoList, Dict.create(), group);
    }

    /**
     * 校验List<Dto>，带消息前缀
     *
     * @param dtoList    Dto类型列表
     * @param prefixDesc 消息前缀描述，必须包含“code”（{@link ErrorCode}），如有消息填充参数，
     *                   则还应包含“params”（{@code String[]}）
     * @param group      校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>        Dto类型
     */
    public static <D> void validate(List<D> dtoList, Dict prefixDesc, Class<?>... group) {
        dtoList.forEach(dto -> validate(dto, prefixDesc, group));
    }

    /**
     * 校验Dto
     *
     * @param dto   Dto类型对象
     * @param group 校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>   Dto类型
     */
    public static <D> void validate(D dto, Class<?>... group) {
        validate(dto, Dict.create(), group);
    }

    /**
     * 校验Dto，带消息前缀
     *
     * @param dto        Dto类型对象
     * @param prefixDesc 消息前缀描述，必须包含“code”（{@link ErrorCode}），如有消息填充参数，
     *                   则还应包含“params”（{@code String[]}）
     * @param group      校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>        Dto类型
     */
    public static <D> void validate(D dto, Dict prefixDesc, Class<?>... group) {
        validateInternal(dto, null, prefixDesc, group);
    }

    /**
     * 校验Dto属性
     *
     * @param dto       Dto类型对象
     * @param fieldName 属性名
     * @param group     校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>       Dto类型
     */
    public static <D> void validate(D dto, String fieldName, Class<?>... group) {
        validate(dto, fieldName, Dict.create(), group);
    }

    /**
     * 校验Dto属性，带消息前缀
     *
     * @param dto        Dto类型对象
     * @param fieldName  属性名
     * @param prefixDesc 消息前缀描述，必须包含“code”（{@link ErrorCode}），如有消息填充参数，
     *                   则还应包含“params”（{@code String[]}）
     * @param group      校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>        Dto类型
     */
    public static <D> void validate(D dto, String fieldName, Dict prefixDesc, Class<?>... group) {
        validateInternal(dto, fieldName, prefixDesc, group);
    }

    /**
     * 校验
     *
     * @param dto        Dto类型对象
     * @param fieldName  属性名
     * @param prefixDesc 消息前缀描述，必须包含“code”（{@link ErrorCode}），如有消息填充参数，
     *                   则还应包含“params”（{@code String[]}）
     * @param group      校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>        Dto类型
     */
    private static <D> void validateInternal(D dto, String fieldName, Dict prefixDesc, Class<?>... group) {
        // 校验
        final Set<ConstraintViolation<D>> constraintViolations;
        if (fieldName != null) {
            constraintViolations = validator.validateProperty(dto, fieldName, group);
        } else {
            constraintViolations = validator.validate(dto, group);
        }

        // 校验消息
        String validateMsg = null;
        if (constraintViolations.size() > 0) {
            StringBuilder message = StrUtil.builder();
            constraintViolations.forEach(c -> message.append(c.getPropertyPath())
                    .append("【").append(c.getMessage()).append("】")
                    .append(StrPool.COMMA));
            validateMsg = message.substring(0, message.length() - 1);
        }

        if (validateMsg != null) {
            if (prefixDesc.containsKey("code")) {
                // 获取消息前缀
                String prefixMsg = LocaleUtil.getI18nMessage(prefixDesc.getInt("code"),
                        prefixDesc.get("params", new String[0]));
                throw ValidationException.of(StrUtil.format(prefixMsg + StrPool.EMPTY_JSON, validateMsg));
            }
            throw ValidationException.of(validateMsg);
        }
    }

}
