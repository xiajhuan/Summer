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

package me.xiajhuan.summer.core.enums;

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
     * 通用CRUD，包含：分页/列表/根据ID获取/新增/修改/删除
     */
    COMMON_CRUD(0, "Common Crud"),

    /**
     * Excel操作，包含：Excel模板下载/Excel导入/Excel导出
     */
    EXCEL_OPERATION(1, "Excel Operation"),

    /**
     * 其他操作
     */
    OTHER_OPERATION(2, "Other Operation");

    private Integer value;

    private String name;

}