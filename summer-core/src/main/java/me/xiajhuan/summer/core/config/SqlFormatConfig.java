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

package me.xiajhuan.summer.core.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import me.xiajhuan.summer.core.constant.SettingBeanConst;

import java.util.Arrays;

/**
 * Sql输出格式配置
 *
 * @author xiajhuan
 * @date 2022/11/26
 * @see MessageFormattingStrategy
 */
public class SqlFormatConfig implements MessageFormattingStrategy {

    /**
     * 不详细打印的Sql语句替换数组，例如：["INSERT:log_operation","INSERT:log_error"]
     */
    private static String[] excludeAndReplaceSqlArray = null;

    /**
     * 初始化 {@link excludeAndReplaceSqlArray}
     */
    static {
        String excludeAndReplaceSql = SpringUtil.getBean(SettingBeanConst.CORE, Setting.class)
                .getByGroup("exclude-and-replace", "Sql");

        if (StrUtil.isNotBlank(excludeAndReplaceSql)) {
            excludeAndReplaceSqlArray = excludeAndReplaceSql.split(StrPool.COMMA);
        }
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String replace = getReplaceSql(sql);
        if (StrUtil.isNotBlank(sql)) {
            if (StrUtil.isNotBlank(replace)) {
                return replace;
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
     * @return 替换的字符串或空串
     */
    private String getReplaceSql(String sql) {
        if (ArrayUtil.isNotEmpty(excludeAndReplaceSqlArray)) {
            return Arrays.stream(excludeAndReplaceSqlArray).filter(replace -> {
                if (replace.indexOf(StrPool.COLON) > 0) {
                    String[] operationAndTable = replace.split(StrPool.COLON);
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
