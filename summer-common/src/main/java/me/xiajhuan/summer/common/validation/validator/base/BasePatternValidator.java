package me.xiajhuan.summer.common.validation.validator.base;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * 基础正则校验器（required，pattern）
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@NoArgsConstructor
@Setter
public class BasePatternValidator extends BaseRequiredValidator {

    /**
     * {@link Pattern}
     */
    protected Pattern pattern;

}
