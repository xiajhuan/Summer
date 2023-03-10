package me.xiajhuan.summer.common.excel;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.utils.ConvertUtil;
import me.xiajhuan.summer.common.utils.SpringContextUtil;

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
public abstract class AbstractExcelParser<T, E> extends AnalysisEventListener<E> {

    protected static final Log LOGGER = LogFactory.get();

    /**
     * Excel数据解析后的最大批量操作数<br>
     * 最多每隔该数量的数据后清理List，方便GC
     */
    private static final int MAX_BATCH_NUM = SpringContextUtil.getBean(SettingBeanConst.COMMON, Setting.class)
            .getInt("parser.max-batch-num", "Excel", 2000);

    /**
     * Entity列表
     */
    protected List<T> entityList;

    /**
     * 初始化Entity列表
     */
    protected AbstractExcelParser() {
        entityList = new ArrayList<>(MAX_BATCH_NUM);
    }

    @Override
    public final void invoke(E data, AnalysisContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("解析到一条Excel数据：{}", JSONUtil.toJsonStr(data));
        }

        T entity = ConvertUtil.convert(data, currentEntityClass());
        entityList.add(entity);

        // 达到MAX_BATCH_NUM了，需要去处理一次数据，防止过多的数据在内存中，导致OOM
        if (entityList.size() >= MAX_BATCH_NUM) {
            // 数据前置处理
            handleParsedDataBefore();

            // 数据处理
            handleParsedData();

            // 处理完成后清理List
            entityList.clear();
        }
    }

    @Override
    public final void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要处理数据，确保最后遗留的数据也得到处理
        handleParsedDataBefore();

        handleParsedData();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("所有Excel数据解析完成！");
        }
    }

    /**
     * 当前EntityClass
     *
     * @return EntityClass
     */
    protected abstract Class<T> currentEntityClass();

    /**
     * 前置处理钩子，可重写该钩子个性化前置处理，如校验/过滤/去重等
     */
    protected void handleParsedDataBefore() {
    }

    /**
     * 数据处理，如保存至Db/缓存/发送消息中间件等
     */
    protected abstract void handleParsedData();

}
