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

package me.xiajhuan.summer.core.converter;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.constant.DateFormatConst;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 日期转换器
 *
 * @author xiajhuan
 * @date 2023/3/24
 * @see Converter
 * @see DateFormatConst
 */
@Component
public class DateConverter implements Converter<String, Date> {

    /**
     * 日期正则表达式列表
     */
    private final List<Pattern> patternList = ListUtil.of(
            Pattern.compile("^\\d{4}-\\d{2}$"),
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"),
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$"),
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$"),
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}$")
    );

    @Override
    public Date convert(String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }

        if (patternList.get(0).matcher(source).matches()) {
            // yyyy-MM
            return DateUtil.parse(source, DateFormatConst.YEAR_MONTH);
        } else if (patternList.get(1).matcher(source).matches()) {
            // yyyy-MM-dd
            return DateUtil.parse(source, DateFormatConst.DATE);
        } else if (patternList.get(2).matcher(source).matches()) {
            // yyyy-MM-dd HH:mm
            return DateUtil.parse(source, DateFormatConst.HOUR_MINUTE);
        } else if (patternList.get(3).matcher(source).matches()) {
            // yyyy-MM-dd HH:mm:ss
            return DateUtil.parse(source, DateFormatConst.DATE_TIME);
        } else if (patternList.get(4).matcher(source).matches()) {
            // yyyy-MM-dd HH:mm:ss.SSS
            return DateUtil.parse(source, DateFormatConst.MILLISECOND);
        } else {
            throw new IllegalArgumentException(StrUtil.format("不支持的日期格式【{}】", source));
        }
    }

}
