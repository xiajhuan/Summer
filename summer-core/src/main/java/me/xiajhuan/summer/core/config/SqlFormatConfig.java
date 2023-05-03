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

package me.xiajhuan.summer.core.config;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import me.xiajhuan.summer.core.constant.SettingConst;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Sql打印格式配置
 *
 * @author xiajhuan
 * @date 2022/11/26
 * @see MessageFormattingStrategy
 */
public class SqlFormatConfig implements MessageFormattingStrategy {

    /**
     * Sql打印格式
     */
    private static final String SQL_FORMAT = "耗时 {} ms | Sql 语句：\n{};";

    /**
     * Sql替换打印格式
     */
    private static final String SQL_REPLACE_FORMAT = "耗时 {} ms | {}";

    /**
     * Quartz相关Sql替换列表
     */
    private static final List<String> QUARTZ_SQL_REPLACE_LIST = ListUtil.of(
            "SELECT->QUARTZ_TRIGGERS", "INSERT->QUARTZ_TRIGGERS",
            "UPDATE->QUARTZ_TRIGGERS", "DELETE->QUARTZ_TRIGGERS",
            "SELECT->QUARTZ_SIMPROP_TRIGGERS", "INSERT->QUARTZ_SIMPROP_TRIGGERS",
            "UPDATE->QUARTZ_SIMPROP_TRIGGERS", "DELETE->QUARTZ_SIMPROP_TRIGGERS",
            "SELECT->QUARTZ_SIMPLE_TRIGGERS", "INSERT->QUARTZ_SIMPLE_TRIGGERS",
            "UPDATE->QUARTZ_SIMPLE_TRIGGERS", "DELETE->QUARTZ_SIMPLE_TRIGGERS",
            "SELECT->QUARTZ_SCHEDULER_STATE", "INSERT->QUARTZ_SCHEDULER_STATE",
            "UPDATE->QUARTZ_SCHEDULER_STATE", "DELETE->QUARTZ_SCHEDULER_STATE",
            "SELECT->QUARTZ_PAUSED_TRIGGER_GRPS", "INSERT->QUARTZ_PAUSED_TRIGGER_GRPS",
            "UPDATE->QUARTZ_PAUSED_TRIGGER_GRPS", "DELETE->QUARTZ_PAUSED_TRIGGER_GRPS",
            "SELECT->QUARTZ_LOCKS", "INSERT->QUARTZ_LOCKS",
            "UPDATE->QUARTZ_LOCKS", "DELETE->QUARTZ_LOCKS",
            "SELECT->QUARTZ_JOB_DETAILS", "INSERT->QUARTZ_JOB_DETAILS",
            "UPDATE->QUARTZ_JOB_DETAILS", "DELETE->QUARTZ_JOB_DETAILS",
            "SELECT->QUARTZ_FIRED_TRIGGERS", "INSERT->QUARTZ_FIRED_TRIGGERS",
            "UPDATE->QUARTZ_FIRED_TRIGGERS", "DELETE->QUARTZ_FIRED_TRIGGERS",
            "SELECT->QUARTZ_CRON_TRIGGERS", "INSERT->QUARTZ_CRON_TRIGGERS",
            "UPDATE->QUARTZ_CRON_TRIGGERS", "DELETE->QUARTZ_CRON_TRIGGERS",
            "SELECT->QUARTZ_CALENDARS", "INSERT->QUARTZ_CALENDARS",
            "UPDATE->QUARTZ_CALENDARS", "DELETE->QUARTZ_CALENDARS",
            "SELECT->QUARTZ_BLOB_TRIGGERS", "INSERT->QUARTZ_BLOB_TRIGGERS",
            "UPDATE->QUARTZ_BLOB_TRIGGERS", "DELETE->QUARTZ_BLOB_TRIGGERS"
    );

    /**
     * Sql替换集合
     */
    private static final Set<String> SQL_REPLACE_SET = new HashSet<>(64);

    static {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);

        if (setting.getBool("p6spy.quartz-sql-replace", "Sql", true)) {
            SQL_REPLACE_SET.addAll(QUARTZ_SQL_REPLACE_LIST);
        }

        String sqlReplace = setting.getByGroup("p6spy.sql-replace", "Sql");
        if (StrUtil.isNotBlank(sqlReplace)) {
            SQL_REPLACE_SET.addAll(ListUtil.of(sqlReplace.split(StrPool.COMMA)));
        }
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (StrUtil.isNotBlank(sql)) {
            String replace = getReplacedSql(sql);
            if (replace != null) {
                return StrUtil.format(SQL_REPLACE_FORMAT, elapsed, replace);
            }
            return StrUtil.format(SQL_FORMAT, elapsed, sql.replaceAll("[\\s]+", StrUtil.SPACE));
        }
        return StrUtil.EMPTY;
    }

    /**
     * <p>
     * 判断Sql是否是被替换的Sql，
     * 若是则返回替换的字符串，若不是则返回{@code null}
     * </p>
     *
     * @param sql Sql
     * @return 替换的字符串或{@code null}
     */
    private String getReplacedSql(String sql) {
        return SQL_REPLACE_SET.stream().filter(replace -> {
            if (replace.indexOf("->") > 0) {
                String[] operationAndTable = replace.split("->");
                if (sql.contains(operationAndTable[0]) && sql.contains(operationAndTable[1])) {
                    return true;
                }
            }
            return false;
        }).findAny().orElse(null);
    }

}
