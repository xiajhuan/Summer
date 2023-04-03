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

package me.xiajhuan.summer.core.excel.subClass;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.alibaba.excel.context.AnalysisContext;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.key.CoreCacheKey;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.TimeUnitConst;
import me.xiajhuan.summer.core.excel.AbstractExcelParser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel数据解析（缓存），note：
 * <pre>
 *   1.通常情况下不建议将Excel数据缓存，如确有需求，应注意防止OOM！
 *   2.想个性化前/后置处理，请自定义Parser继承当前类，可覆写：
 *     {@link AbstractExcelParser#handleDtoBefore(Object, AnalysisContext)}，
 *     {@link AbstractExcelParser#handleEntityListBefore()}，
 *     {@link AbstractExcelParser#handleEntityListAfter()}
 * </pre>
 *
 * @author xiajhuan
 * @date 2022/12/1
 */
public class ExcelCacheParser<D, T> extends AbstractExcelParser<D, T> {

    /**
     * 缓存过期时间（h）
     */
    private static final Long CACHE_TTL = SpringUtil.getBean(SettingConst.CORE, Setting.class)
            .getInt("parser.cache-ttl", "Excel", 24) * TimeUnitConst.HOUR;

    /**
     * 当前EntityClass
     */
    protected Class<T> currentEntityClass;

    /**
     * 构造ExcelCacheParser
     *
     * @param currentEntityClass 当前EntityClass
     */
    protected ExcelCacheParser(Class<T> currentEntityClass) {
        this.currentEntityClass = currentEntityClass;
    }

    /**
     * 构建ExcelCacheParser
     *
     * @param currentEntityClass 当前EntityClass
     * @param <T>                Entity类型
     * @return ExcelCacheParser
     */
    public static <T> ExcelCacheParser build(Class<T> currentEntityClass) {
        return new ExcelCacheParser(currentEntityClass);
    }

    @Override
    protected Class<T> currentEntityClass() {
        return currentEntityClass;
    }

    @Override
    protected void handleEntityList() {
        LOGGER.info("解析到【{}】条Excel数据，开始缓存！", entityList.size());

        List<String> excelList = entityList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
        CacheServerFactory.getCacheServer()
                .setList(CoreCacheKey.excelData(currentEntityClass.getSimpleName()), excelList, CACHE_TTL);

        LOGGER.info("缓存成功！");
    }

}