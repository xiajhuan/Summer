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

package me.xiajhuan.summer.core.excel.parser.subClass;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.excel.parser.AbstractExcelParser;

/**
 * Excel解析（保存到Db），note：
 * <pre>
 *   想个性化前/后置处理，请自定义Parser继承当前类，可覆写：
 *   {@link AbstractExcelParser#handleDtoBefore(Object, AnalysisContext)}，
 *   {@link AbstractExcelParser#handleEntityListBefore()}，
 *   {@link AbstractExcelParser#handleEntityListAfter()}
 * </pre>
 *
 * @author xiajhuan
 * @date 2022/12/1
 */
public class DbExcelParser<D, T> extends AbstractExcelParser<D, T> {

    /**
     * Service
     */
    protected IService<T> service;

    /**
     * 当前EntityClass
     */
    protected Class<T> currentEntityClass;

    /**
     * 构造保护化
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     */
    protected DbExcelParser(IService<T> service, Class<T> currentEntityClass) {
        this.service = service;
        this.currentEntityClass = currentEntityClass;
    }

    /**
     * 构建DbExcelParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     * @param <T>                Entity类型
     * @return DbExcelParser
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> DbExcelParser build(IService<T> service, Class<T> currentEntityClass) {
        return new DbExcelParser(service, currentEntityClass);
    }

    @Override
    protected Class<T> currentEntityClass() {
        return currentEntityClass;
    }

    @Override
    protected void handleEntityList() {
        LOGGER.info("解析到【{}】条Excel数据，开始保存到Db！", entityList.size());

        service.saveBatch(entityList);

        LOGGER.info("保存到Db成功！");
    }

}