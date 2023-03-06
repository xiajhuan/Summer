package me.xiajhuan.summer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 地区电话号码正则枚举
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@Getter
@AllArgsConstructor
public enum RegionMobileEnum {

    /**
     * ZH_CN
     */
    ZH_CN("/^(\\+?0?86\\-?)?1[345789]\\d{9}$/"),

    /**
     * EN_US
     */
    EN_US("/^(\\+?0?86\\-?)?1[345789]\\d{9}$/");

    private String value;

}
