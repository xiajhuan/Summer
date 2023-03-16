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

package me.xiajhuan.summer.admin.common.locale.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;

import java.io.Serializable;

/**
 * 国际化名称 Excel
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class LocaleInternationalNameExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    @ExcelProperty(value = "表名", index = 0)
    private String tableName;

    /**
     * 字段名
     */
    @ExcelProperty(value = "字段名", index = 1)
    private String fieldName;

    /**
     * 字段值
     */
    @ColumnWidth(50)
    @ExcelProperty(value = "字段值", index = 2)
    private String fieldValue;

    /**
     * 地区语言
     *
     * @see LocaleSupportEnum
     */
    @ExcelProperty(value = "地区语言", index = 3)
    private String locale;

}
