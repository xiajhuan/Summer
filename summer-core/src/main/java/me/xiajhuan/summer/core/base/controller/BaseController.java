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

package me.xiajhuan.summer.core.base.controller;

import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.properties.BatchLimitProperties;
import me.xiajhuan.summer.core.utils.AssertUtil;

import javax.annotation.Resource;

/**
 * 基本 Controller基类
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
public abstract class BaseController {

    @Resource
    private BatchLimitProperties batchLimitProperties;

    /**
     * 校验最大导出数
     *
     * @param count 导出数
     */
    protected void validateMaxExport(long count) {
        long excelMaxExport = batchLimitProperties.getExcelMaxExport();
        AssertUtil.checkBetween(count, 0L, excelMaxExport,
                () -> ValidationException.of(ErrorCode.EXCEL_EXPORT_MAXIMUM_LIMIT, String.valueOf(excelMaxExport)));
    }

}
