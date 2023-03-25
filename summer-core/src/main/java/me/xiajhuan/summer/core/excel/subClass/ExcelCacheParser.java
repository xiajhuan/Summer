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

package me.xiajhuan.summer.core.excel.subClass;

import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.key.CoreCacheKey;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.constant.TimeUnitConst;
import me.xiajhuan.summer.core.excel.AbstractExcelParser;
import me.xiajhuan.summer.core.utils.SpringContextUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel数据解析（缓存）
 * <p>
 * note1：通常情况下不建议将Excel数据缓存，如确有需求，应注意防止OOM
 * note2：想个性化前置处理，请自定义Parser继承当前类覆写 {@link AbstractExcelParser#handleParsedDataBefore}
 * </p>
 *
 * @author xiajhuan
 * @date 2022/12/1
 */
public class ExcelCacheParser<D, T> extends AbstractExcelParser<D, T> {

    /**
     * 缓存过期时间（h）
     */
    private static final Long CACHE_TTL = SpringContextUtil.getBean(SettingBeanConst.CORE, Setting.class)
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
    protected void handleParsedData() {
        LOGGER.info("解析到【{}】条Excel数据，开始缓存！", entityList.size());

        List<String> excelList = entityList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
        CacheServerFactory.getCacheServer()
                .setListTtl(CoreCacheKey.excelData(currentEntityClass.getSimpleName()), excelList, CACHE_TTL);

        LOGGER.info("缓存成功！");
    }

}