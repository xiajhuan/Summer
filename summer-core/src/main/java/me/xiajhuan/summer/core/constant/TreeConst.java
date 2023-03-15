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

package me.xiajhuan.summer.core.constant;

/**
 * 树形结构常量
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
public class TreeConst {

    /**
     * Root节点ID
     */
    public static final Long ROOT = 0L;

    /**
     * 扩展属性
     */
    public static class Extra {

        /**
         * 菜单
         */
        public static final String[] MENU = {"url", "permissions", "type", "openMode", "icon", "createTime", "parentName"};

        /**
         * 部门
         */
        public static final String[] DEPT = {"createTime", "parentName"};

    }

}
