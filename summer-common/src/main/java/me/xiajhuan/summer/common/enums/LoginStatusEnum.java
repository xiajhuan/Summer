package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录状态枚举
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Getter
@AllArgsConstructor
public enum LoginStatusEnum {

    /**
     * 失败
     */
    FAIL(0, "失败"),

    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 账号已锁定
     */
    LOCK(2, "账号已锁定");

    private Integer value;

    private String desc;

}