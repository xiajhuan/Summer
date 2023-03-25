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

package me.xiajhuan.summer.core.data;

import lombok.Data;

import java.io.Serializable;

import me.xiajhuan.summer.core.constant.SortConst;

/**
 * 分页排序参数
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
@Data
public class PageAndSort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码，默认1
     */
    private int pageNum = 1;

    /**
     * 每页记录数，默认10
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序规则
     *
     * @see SortConst.Order
     */
    private String order;

}
