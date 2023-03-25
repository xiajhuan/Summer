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
 * 排序常量
 *
 * @author xiajhuan
 * @date 2022/12/3
 */
public class SortConst {

    /**
     * 字段
     */
    public static class Field {

        /**
         * 默认
         */
        public static final String DEFAULT = "createTime";

    }

    /**
     * 规则
     */
    public static class Order {

        /**
         * 默认
         */
        public static final String DEFAULT = "descend";

        /**
         * 升序
         */
        public static final String[] ASC = {"asc", "ascend", "ascending"};

        /**
         * 降序
         */
        public static final String[] DESC = {"desc", "descend", "descending"};

    }

}
