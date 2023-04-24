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

package me.xiajhuan.summer.system.locale.excel.parser;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.excel.parser.subClass.ExcelDbParser;
import me.xiajhuan.summer.system.locale.dto.LocaleNameDto;
import me.xiajhuan.summer.system.locale.entity.LocaleNameEntity;
import me.xiajhuan.summer.system.locale.service.LocaleNameService;

import java.util.stream.Collectors;

/**
 * 国际化名称 Excel数据解析（保存到Db）
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
public class LocaleNameExcelDbParser extends ExcelDbParser<LocaleNameDto, LocaleNameEntity> {

    /**
     * 国际化名称 Service
     */
    private final LocaleNameService localeNameService;

    /**
     * 构造LocaleNameExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     */
    private LocaleNameExcelDbParser(IService<LocaleNameEntity> service, Class<LocaleNameEntity> currentEntityClass) {
        super(service, currentEntityClass);

        localeNameService = SpringUtil.getBean("localeNameServiceImpl", LocaleNameService.class);
    }

    /**
     * 构建LocaleNameExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     * @return LocaleNameExcelDbParser
     */
    public static LocaleNameExcelDbParser of(IService<LocaleNameEntity> service, Class<LocaleNameEntity> currentEntityClass) {
        return new LocaleNameExcelDbParser(service, currentEntityClass);
    }

    @Override
    protected void handleEntityListBefore() {
        // 去重/过滤
        entityList = entityList.stream().distinct()
                .filter(entity -> !localeNameService.exist(entity.getLineId(), entity.getLocale()))
                .collect(Collectors.toList());
    }

}
