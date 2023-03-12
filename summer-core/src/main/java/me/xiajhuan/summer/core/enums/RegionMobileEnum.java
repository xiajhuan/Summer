/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.enums;

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
    EN_US("/^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$/");

    private String value;

}
