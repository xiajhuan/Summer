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

/**
 * 操作名称常量
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
public class OperationConst {

    /**
     * 分页<br>
     * GET Query
     */
    public static final String PAGE = "分页";

    /**
     * 列表<br>
     * GET Query
     */
    public static final String LIST = "列表";

    /**
     * 根据ID获取<br>
     * GET Query
     */
    public static final String GET_BY_ID = "根据ID获取";

    /**
     * 新增<br>
     * POST application/x-www-form-urlencoded
     */
    public static final String ADD = "新增";

    /**
     * 修改<br>
     * PUT application/x-www-form-urlencoded
     */
    public static final String UPDATE = "修改";

    /**
     * 删除<br>
     * DELETE application/x-www-form-urlencoded
     */
    public static final String DELETE = "删除";

    /**
     * Excel模板下载<br>
     * GET none
     */
    public static final String EXCEL_TEMPLATE = "Excel模板下载";

    /**
     * Excel导入<br>
     * POST multipart/form-data
     */
    public static final String EXCEL_IMPORT = "Excel导入";

    /**
     * Excel导出<br>
     * GET application/x-www-form-urlencoded
     */
    public static final String EXCEL_EXPORT = "Excel导出";

}
