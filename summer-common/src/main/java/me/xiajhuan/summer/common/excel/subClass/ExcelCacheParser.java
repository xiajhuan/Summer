package me.xiajhuan.summer.common.excel.subClass;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.common.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.common.constant.CacheConst;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.excel.AbstractExcelParser;
import me.xiajhuan.summer.common.utils.SpringContextUtil;

import java.util.List;

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
public class ExcelCacheParser<T, E> extends AbstractExcelParser<T, E> {

    /**
     * 缓存过期时间（ms）
     */
    private static final Long CACHE_TTL = SpringContextUtil.getBean(SettingBeanConst.COMMON, Setting.class)
            .getLong("parser.cache.ttl", "Excel", 86400000L);

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

        CacheServerFactory.getInstance().getCacheServer()
                .setListTtl(StrUtil.format(CacheConst.EXCEL_DATA, currentEntityClass.getSimpleName()), (List<Object>) entityList, CACHE_TTL);

        LOGGER.info("缓存成功！");
    }

}