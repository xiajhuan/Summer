package me.xiajhuan.summer.common.validation.validator.base;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 基础必填校验器（required）
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@NoArgsConstructor
@Setter
public class BaseRequiredValidator {

    /**
     * 是否必填，true：必填 false：非必填
     */
    protected boolean required;

}
