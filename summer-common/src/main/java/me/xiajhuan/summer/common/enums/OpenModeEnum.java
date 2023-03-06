package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 打开方式枚举
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Getter
@AllArgsConstructor
public enum OpenModeEnum {

    /**
     * 内部
     */
    INNER(0, "内部"),

    /**
     * 外部
     */
    OUTER(1, "外部");

    private Integer value;

    private String desc;

}