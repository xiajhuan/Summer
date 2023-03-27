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

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.ValidationException;
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
     * {@link Validator}
     */
    private static Validator validator;

    /**
     * 初始化 {@link validator}
     */
    static {
        validator = SpringContextUtil.getBean("getValidator", Validator.class);
    }

    /**
     * 校验
     *
     * @param dtoList Dto类型列表
     * @param group   校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>     Dto类型
     */
    public static <D> void validate(List<D> dtoList, Class<?>... group) {
        validate(dtoList, null, group);
    }

    /**
     * 校验
     *
     * @param dtoList    Dto类型列表
     * @param prefixDesc 消息前缀描述，必须包含“code”（{@link Integer}），如有消息填充参数，
     *                   则还应包含“params”（{@code String[]}） {@link ErrorCode}
     * @param group      校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>        Dto类型
     */
    public static <D> void validate(List<D> dtoList, Dict prefixDesc, Class<?>... group) {
        for (D dto : dtoList) {
            validate(dto, prefixDesc, group);
        }
    }

    /**
     * 校验
     *
     * @param dto   Dto类型对象
     * @param group 校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>   Dto类型
     */
    public static <D> void validate(D dto, Class<?>... group) {
        validate(dto, null, group);
    }

    /**
     * 校验
     *
     * @param dto        Dto类型对象
     * @param prefixDesc 消息前缀描述，必须包含“code”（{@link Integer}），如有消息填充参数，
     *                   则还应包含“params”（{@code String[]}） {@link ErrorCode}
     * @param group      校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>        Dto类型
     */
    public static <D> void validate(D dto, Dict prefixDesc, Class<?>... group) {
        String validateMsg = getValidateMsg(dto, group);
        if (validateMsg != null) {
            if (prefixDesc != null && prefixDesc.containsKey("code")) {
                // 获取消息前缀
                String prefix = LocaleUtil.getI18nMessage(prefixDesc.getInt("code"),
                        prefixDesc.get("params", new String[0]));
                throw ValidationException.of(StrUtil.format(prefix + StrPool.EMPTY_JSON, validateMsg));
            }
            throw ValidationException.of(validateMsg);
        }
    }

    /**
     * @param dto   Dto类型对象
     * @param group 校验分组 {@link AddGroup} {@link UpdateGroup} {@link DefaultGroup}
     * @param <D>   Dto类型
     * @return 校验失败消息或 {@code null}
     */
    private static <D> String getValidateMsg(D dto, Class<?>... group) {
        // 校验
        Set<ConstraintViolation<D>> constraintViolations = validator.validate(dto, group);

        if (constraintViolations.size() > 0) {
            StringBuilder message = StrUtil.builder();
            constraintViolations.forEach(c -> message.append(c.getPropertyPath())
                    .append("【").append(c.getMessage()).append("】")
                    .append(StrPool.COMMA));
            return message.substring(0, message.length() - 1);
        }
        return null;
    }

}
