package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录操作枚举
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Getter
@AllArgsConstructor
public enum LoginOperationEnum {

    /**
     * 用户登录
     */
    LOGIN(0, "用户登录"),

    /**
     * 用户退出
     */
    LOGOUT(1, "用户退出");

    private int value;

    private String name;

}
