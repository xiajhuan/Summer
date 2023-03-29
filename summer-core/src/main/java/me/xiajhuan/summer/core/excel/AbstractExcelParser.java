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

package me.xiajhuan.summer.core.excel;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import me.xiajhuan.summer.core.utils.ValidationUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel数据解析基类<br>
 * note：此类只定义了Excel数据读取后的操作步骤
 *
 * @author xiajhuan
 * @date 2022/12/1
 * @see AnalysisEventListener
 */
public abstract class AbstractExcelParser<D, T> extends AnalysisEventListener<D> {

    protected static final Log LOGGER = LogFactory.get();

    /**
     * Excel数据解析后的最大批量操作数<br>
     * 最多每隔该数量的数据后清理List，方便GC
     */
    private static final int MAX_BATCH_NUM = SpringUtil.getBean(SettingBeanConst.CORE, Setting.class)
            .getInt("parser.max-batch-num", "Excel", 2000);

    /**
     * Entity类型列表
     */
    protected List<T> entityList;

    /**
     * 初始化 {@link entityList}
     */
    protected AbstractExcelParser() {
        entityList = new ArrayList<>(MAX_BATCH_NUM);
    }

    @Override
    public final void invoke(D data, AnalysisContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("解析到一条Excel数据：{}", JSONUtil.toJsonStr(data));
        }

        // dto前置处理
        handleDtoBefore(data, context);

        T entity = ConvertUtil.convert(data, currentEntityClass());
        entityList.add(entity);

        // 达到MAX_BATCH_NUM了，需要去处理一次数据，防止过多的数据在内存中，导致OOM
        if (entityList.size() >= MAX_BATCH_NUM) {
            // entityList前置处理
            handleEntityListBefore();

            // entityList处理
            handleEntityList();

            // entityList后置处理
            handleEntityListAfter();
        }
    }

    @Override
    public final void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要处理数据，确保最后遗留的数据也得到处理
        handleEntityListBefore();

        handleEntityList();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("所有Excel数据处理完成！");
        }
    }

    /**
     * 当前EntityClass
     *
     * @return EntityClass
     */
    protected abstract Class<T> currentEntityClass();

    //*******************模板方法钩子，执行顺序从上到下依次********************

    /**
     * Dto类型对象前置处理钩子，默认做字段校验，可重写该钩子个性化前置处理
     *
     * @param data    Dto类型对象
     * @param context {@link AnalysisContext}
     */
    protected void handleDtoBefore(D data, AnalysisContext context) {
        // 校验dto
        ValidationUtil.validate(data,
                Dict.of("code", ErrorCode.EXCEL_IMPORT_FAILURE_PREFIX,
                        "params", new String[]{String.valueOf(context.readRowHolder().getRowIndex())}),
                AddGroup.class);
    }

    /**
     * {@link entityList} 前置处理钩子，可重写该钩子个性化前置处理，如按字段过滤/去重等
     */
    protected void handleEntityListBefore() {
    }

    /**
     * {@link entityList} 处理，如保存至Db/缓存/发送消息中间件等
     */
    protected abstract void handleEntityList();

    /**
     * {@link entityList} 后置处理钩子，默认清理列表，可重写该钩子个性化后置处理
     */
    protected void handleEntityListAfter() {
        // 清理entityList
        entityList.clear();
    }

}
