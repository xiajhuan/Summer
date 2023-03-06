package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    /**
     * 超级管理员用户
     */
    SUPER_ADMIN(0, "超级管理员用户"),

    /**
     * 普通用户
     */
    GENERAL(1, "普通用户");

    private int value;

    private String name;

}
