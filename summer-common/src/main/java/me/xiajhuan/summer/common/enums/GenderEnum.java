package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 男
     */
    MALE(0, "男"),

    /**
     * 女
     */
    FEMALE(1, "女"),

    /**
     * 保密
     */
    SECRET(2, "保密");

    private Integer value;

    private String desc;

}