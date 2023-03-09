package me.xiajhuan.summer.common.log.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import static me.xiajhuan.summer.common.enums.OperationGroupEnum.*;

/**
 * Excel操作分组 Converter
 *
 * @author xiajhuan
 * @date 2023/3/3
 * @see Converter
 */
public class OperationGroupConverter implements Converter<Integer> {

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
                return new WriteCellData(COMMON_CRUD.getDesc());
            case 1:
                return new WriteCellData(EXCEL_OPERATION.getDesc());
            case 2:
                return new WriteCellData(OTHER_OPERATION.getDesc());
            default:
                return new WriteCellData("未知操作分组");
        }
    }

}