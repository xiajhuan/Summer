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
 * 数据权限枚举
 *
 * @author xiajhuan
 * @date 2023/3/6
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

    /**
     * 全部
     */
    ALL(0, "全部", "所有的数据"),

    /**
     * 基于角色
     */
    ROLE_BASED(1, "基于角色", "用户所有角色关联的所有部门的数据"),

    /**
     * 本部门
     */
    DEPT_SELF(2, "本部门", "用户本部门的数据"),

    /**
     * 本部门及以下
     */
    DEPT_AND_CHILD(3, "本部门及以下", "用户本部门及本部门下子部门的数据"),

    /**
     * 仅本人
     */
    SELF(4, "仅本人", "用户自己的数据");

    private Integer value;

    private String name;

    private String desc;

}
