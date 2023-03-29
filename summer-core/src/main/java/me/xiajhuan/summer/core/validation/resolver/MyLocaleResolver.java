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

package me.xiajhuan.summer.core.validation.resolver;

import me.xiajhuan.summer.core.utils.LocaleUtil;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 自定义地区语言解析器
 *
 * @author xiajhuan
 * @date 2023/3/20
 * @see LocaleResolver
 * @see LocaleUtil#getLocalePriority()
 */
public class MyLocaleResolver implements LocaleResolver {

    /**
     * 构造MyLocaleResolver
     */
    private MyLocaleResolver() {
    }

    /**
     * 构建MyLocaleResolver
     *
     * @return MyLocaleResolver
     */
    public static MyLocaleResolver of() {
        return new MyLocaleResolver();
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return LocaleUtil.getLocalePriority();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("不支持更改“Accept-Language”请求头");
    }

}
