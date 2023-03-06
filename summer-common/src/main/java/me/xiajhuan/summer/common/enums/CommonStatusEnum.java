package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum {

    /**
     * 停用
     */
    DISABLE(0, "停用"),

    /**
     * 正常
     */
    ENABLE(1, "正常");

    private Integer value;

    private String desc;

}