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

package me.xiajhuan.summer.core.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import static me.xiajhuan.summer.core.enums.StatusEnum.DISABLE;
import static me.xiajhuan.summer.core.enums.StatusEnum.ENABLE;

/**
 * 状态 Converter
 *
 * @author xiajhuan
 * @date 2023/4/11
 * @see Converter
 */
public class StatusConverter implements Converter<Integer> {

    @Override
    public Class<Integer> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        switch (cellData.getStringValue()) {
            case "停用":
                return DISABLE.getValue();
            case "正常":
                return ENABLE.getValue();
            default:
                return -1;
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public WriteCellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        switch (value) {
            case 0:
                return new WriteCellData(DISABLE.getName());
            case 1:
                return new WriteCellData(ENABLE.getName());
            default:
                return new WriteCellData("未知状态");
        }
    }

}