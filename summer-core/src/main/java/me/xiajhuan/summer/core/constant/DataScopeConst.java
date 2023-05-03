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

package me.xiajhuan.summer.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限常量
 *
 * @author xiajhuan
 * @date 2023/3/6
 */
public class DataScopeConst {

    /**
     * 记录字段
     */
    public static class Recorder {

        /**
         * 部门ID
         */
        public static final String DEPT_ID = "dept_id";

        /**
         * 用户名
         */
        public static final String USERNAME = "create_by";

    }

    /**
     * 类型枚举
     */
    @Getter
    @AllArgsConstructor
    public enum Type {

        /**
         * 全部
         */
        ALL(0, "全部", "所有数据"),

        /**
         * 基于角色
         */
        ROLE_BASED(1, "基于角色", "用户所有角色关联的所有部门数据"),

        /**
         * 本部门
         */
        DEPT_SELF(2, "本部门", "用户本部门数据"),

        /**
         * 本部门及以下
         */
        DEPT_AND_CHILD(3, "本部门及以下", "用户本部门及本部门下子部门数据"),

        /**
         * 仅本人
         */
        SELF(4, "仅本人", "用户自己的数据");

        private final int value;

        private final String name;

        private final String desc;

    }

}
