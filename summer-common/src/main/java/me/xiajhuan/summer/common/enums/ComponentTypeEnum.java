package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 组件类型枚举
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Getter
@AllArgsConstructor
public enum ComponentTypeEnum {

    /**
     * 菜单
     */
    MENU(0, "菜单"),

    /**
     * 按钮
     */
    BUTTON(1, "按钮");

    private Integer value;

    private String desc;

}