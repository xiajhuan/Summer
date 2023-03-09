package me.xiajhuan.summer.common.log.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import static me.xiajhuan.summer.common.enums.LoginStatusEnum.*;

/**
 * Excel登录状态 Converter
 *
 * @author xiajhuan
 * @date 2023/3/3
 * @see Converter
 */
public class LoginStatusConverter implements Converter<Integer> {

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
            case 2:
                return new WriteCellData(LOCK.getDesc());
            default:
                return new WriteCellData("未知登录状态");
        }
    }

}