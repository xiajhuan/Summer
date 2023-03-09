package me.xiajhuan.summer.common.log.sql;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.utils.SpringContextUtil;

import java.util.Arrays;

/**
 * p6spy sql输出格式
 *
 * @author xiajhuan
 * @date 2022/11/26
 * @see MessageFormattingStrategy
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {

    /**
     * p6spy不详细打印的Sql语句替换数组，例如：["INSERT:log_operation","INSERT:log_error"]
     */
    private static String[] excludeReplaceSqlArray = null;

    /**
     * 初始化 {@link excludeReplaceSqlArray}
     */
    static {
        String excludeReplaceSql = SpringContextUtil.getBean(SettingBeanConst.COMMON, Setting.class)
                .getByGroup("sql.exclude-replace", "Log");

        if (StrUtil.isNotBlank(excludeReplaceSql)) {
            excludeReplaceSqlArray = excludeReplaceSql.split(StrPool.COMMA);
        }
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String replaceStr = getExcludeAndReplaceSql(sql);
        if (StrUtil.isNotBlank(sql)) {
            if (StrUtil.isNotBlank(replaceStr)) {
                return replaceStr;
            }
            return StrUtil.format("{} | 耗时 {} ms | SQL 语句：\n{};",
                    DateUtil.formatDateTime(DateUtil.date()), elapsed, sql.replaceAll("[\\s]+", StrUtil.SPACE));
        }
        return StrUtil.EMPTY;
    }

    /**
     * <p>
     * 判断Sql是否是已排除替换的Sql，
     * 若是则返回替换的字符串，若不是则返回空串
     * </p>
     *
     * @param sql Sql
     * @return 替换后的Sql或空串
     */
    private String getExcludeAndReplaceSql(String sql) {
        if (ArrayUtil.isNotEmpty(excludeReplaceSqlArray)) {
            return Arrays.stream(excludeReplaceSqlArray).filter(exclude -> {
                if (exclude.indexOf(StrPool.COLON) > 0) {
                    String[] operationAndTable = exclude.split(StrPool.COLON);
                    if (sql.indexOf(operationAndTable[0]) >= 0 && sql.indexOf(operationAndTable[1]) >= 0) {
                        return true;
                    }
                }
                return false;
            }).findAny().orElse(StrUtil.EMPTY);
        }
        return StrUtil.EMPTY;
    }

}
