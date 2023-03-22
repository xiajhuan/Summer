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

package me.xiajhuan.summer.admin.common.locale.excel.parser;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.admin.common.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.admin.common.locale.excel.LocaleInternationalNameExcel;
import me.xiajhuan.summer.core.excel.subClass.ExcelDbParser;

/**
 * 国际化名称Excel数据解析（保存到Db）
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
public class LocaleInternationalNameExcelDbParser extends ExcelDbParser<LocaleInternationalNameEntity, LocaleInternationalNameExcel> {

    /**
     * 构造LocaleInternationalNameExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     */
    private LocaleInternationalNameExcelDbParser(IService<LocaleInternationalNameEntity> service, Class<LocaleInternationalNameEntity> currentEntityClass) {
        super(service, currentEntityClass);
    }

    /**
     * 构建LocaleInternationalNameExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     * @return LocaleInternationalNameExcelDbParser
     */
    public static LocaleInternationalNameExcelDbParser of(IService<LocaleInternationalNameEntity> service, Class<LocaleInternationalNameEntity> currentEntityClass) {
        return new LocaleInternationalNameExcelDbParser(service, currentEntityClass);
    }

    @Override
    protected void handleParsedDataBefore() {

    }

}