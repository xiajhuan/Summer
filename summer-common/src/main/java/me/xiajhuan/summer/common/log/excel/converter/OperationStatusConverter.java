package me.xiajhuan.summer.common.log.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import static me.xiajhuan.summer.common.enums.OperationStatusEnum.*;

/**
 * Excel操作状态 Converter
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public class OperationStatusConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        switch (value) {
            case 0:
                return new WriteCellData(FAIL.getDesc());
            case 1:
                return new WriteCellData(SUCCESS.getDesc());
            default:
                return new WriteCellData("未知操作状态");
        }
    }

}