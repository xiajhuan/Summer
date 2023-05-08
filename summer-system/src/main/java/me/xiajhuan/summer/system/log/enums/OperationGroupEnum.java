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

package me.xiajhuan.summer.system.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作分组枚举
 *
 * @author xiajhuan
 * @date 2022/12/3
 */
@Getter
@AllArgsConstructor
public enum OperationGroupEnum {

    /**
     * Common Crud
     */
    COMMON_CRUD(0, "Common Crud", "通用CRUD，包含：分页/列表/根据ID获取/新增/修改/删除"),

    /**
     * Excel Operation
     */
    EXCEL_OPERATION(1, "Excel Operation", "Excel操作，包含：Excel模板下载/Excel导入/Excel导出"),

    /**
     * Other Operation
     */
    OTHER_OPERATION(2, "Other Operation", "其他操作");

    private final int value;

    private final String name;

    private final String desc;

}