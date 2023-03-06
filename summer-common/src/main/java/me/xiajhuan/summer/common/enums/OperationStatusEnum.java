package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作状态枚举
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Getter
@AllArgsConstructor
public enum OperationStatusEnum {

    /**
     * 失败
     */
    FAIL(0, "失败"),

    /**
     * 成功
     */
    SUCCESS(1, "成功");

    private Integer value;

    private String desc;

}