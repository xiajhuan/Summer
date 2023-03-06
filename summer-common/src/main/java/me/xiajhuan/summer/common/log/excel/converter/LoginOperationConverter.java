package me.xiajhuan.summer.common.log.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import static me.xiajhuan.summer.common.enums.LoginOperationEnum.*;

/**
 * Excel登录操作 Converter
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public class LoginOperationConverter implements Converter<Integer> {

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
                return new WriteCellData(LOGIN.getName());
            case 1:
                return new WriteCellData(LOGOUT.getName());
            default:
                return new WriteCellData("未知登录操作");
        }
    }

}