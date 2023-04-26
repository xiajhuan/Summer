/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.system.extend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 行政区域级别枚举
 *
 * @author xiajhuan
 * @date 2023/4/26
 */
@Getter
@AllArgsConstructor
public enum RegionLevelEnum {

    /**
     * 省直辖市
     */
    PROVINCE(0, "省直辖市"),

    /**
     * 地级市
     */
    CITY(1, "地级市"),

    /**
     * 区县
     */
    COUNTY(2, "区县");

    private final int value;

    private final String name;

}