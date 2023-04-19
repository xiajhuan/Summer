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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;
import org.springframework.context.MessageSource;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 国际化工具
 * <p>
 * Locale：表示特定的地理，政治，或文化区域
 * 优先级从上到下依次：
 * <pre>
 *   1.请求头“Accept-Language”有值且以“zh”或“en”开头，
 *     以“Accept-Language”为准
 *   2.请求头“Accept-Language”没有值或有值但不以“zh”或“en”开头，
 *     以服务器JVM设置的值为准 {@link Locale#getDefault()}
 *   3.满足2但JVM设置的值语言不为“zh”或“en”，
 *     以core.setting中extra-default配置值为准
 * </pre>
 * </p>
 *
 * @author xiajhuan
 * @date 2023/2/24
 * @see Locale
 * @see LocaleSupportEnum
 */
public class LocaleUtil {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 构造LocaleUtil（不允许实例化）
     */
    private LocaleUtil() {
    }

    /**
     * {@link MessageSource}
     */
    private static final MessageSource MESSAGE_SOURCE;

    /**
     * 请求头“Accept-Language”的值是中英文以外时的默认语言<br>
     * note：此默认值只适用于 {@link LocaleUtil#getAcceptLanguage(HttpServletRequest)}
     */
    private static String defaultRequestHeader;

    /**
     * 服务器JVM设置的语言是中英文以外时的默认语言
     */
    private static String defaultJvm;

    /**
     * 初始化 {@link MESSAGE_SOURCE} {@link defaultRequestHeader} {@link defaultJvm}
     */
    static {
        MESSAGE_SOURCE = SpringUtil.getBean("messageSource", MessageSource.class);

        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        defaultRequestHeader = setting.getByGroupWithLog("default.request-header", "Locale");
        if (StrUtil.isBlank(defaultRequestHeader)) {
            // 没有配置则默认为：zh_CN
            defaultRequestHeader = LocaleSupportEnum.ZH_CN.getName();
        }
        defaultJvm = setting.getByGroupWithLog("default.jvm", "Locale");
        if (StrUtil.isBlank(defaultJvm)) {
            // 没有配置则默认为：en_US
            defaultJvm = LocaleSupportEnum.EN_US.getName();
        }
    }

    /**
     * 获取国际化消息
     *
     * @param code   错误编码 {@link ErrorCode}
     * @param params 消息填充参数
     * @return 国际化消息
     */
    public static String getI18nMessage(int code, String... params) {
        return getI18nMessage(getLocalePriority(), code, params);
    }

    /**
     * 获取国际化消息
     *
     * @param locale {@link Locale}
     * @param code   错误编码 {@link ErrorCode}
     * @param params 消息填充参数
     * @return 国际化消息
     */
    public static String getI18nMessage(Locale locale, int code, String... params) {
        return MESSAGE_SOURCE.getMessage(String.valueOf(code), params, locale);
    }

    /**
     * 根据优先级获取 {@link Locale}
     *
     * @return {@link Locale}
     */
    public static Locale getLocalePriority() {
        // 优先取请求头“Accept-Language”的值
        Locale locale = getLocaleAcceptLanguage(ServletUtil.getHttpRequest());

        if (locale == null) {
            // 取JVM设置的值
            locale = Locale.getDefault();

            // 中国（中文）
            Locale chineseLocale = LocaleSupportEnum.ZH_CN.getValue();
            // 美国（英文）
            Locale americaLocale = LocaleSupportEnum.EN_US.getValue();
            if (locale.getLanguage().equals(chineseLocale.getLanguage())) {
                // 所有中文地区，强制为：中国（中文）
                locale = chineseLocale;
            } else if (locale.getLanguage().equals(americaLocale.getLanguage())) {
                // 所有英文地区，强制为：美国（英语）
                locale = americaLocale;
            } else {
                LOGGER.warn("中英文以外的地区【{}】，自动调整为默认地区【{}】", locale.toString(), defaultJvm);

                locale = LocaleSupportEnum.ZH_CN.getName().equalsIgnoreCase(defaultJvm)
                        ? chineseLocale : americaLocale;
            }
        }

        return locale;
    }

    /**
     * 根据请求头“Accept-Language”获取地区语言
     *
     * @param request {@link HttpServletRequest}
     * @return 地区语言
     */
    public static String getAcceptLanguage(HttpServletRequest request) {
        Locale locale = getLocaleAcceptLanguage(request);

        return locale == null ? defaultRequestHeader : locale.toString();
    }

    /**
     * 根据请求头“Accept-Language”获取 {@link Locale}
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Locale} 或 {@code null}
     */
    private static Locale getLocaleAcceptLanguage(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 请求语言
        String languageHeader = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (languageHeader != null && languageHeader.length() >= 2) {
            if ("zh".equalsIgnoreCase(languageHeader.substring(0, 2))) {
                // 所有中文地区，强制为：中国（中文）
                return LocaleSupportEnum.ZH_CN.getValue();
            } else if ("en".equalsIgnoreCase(languageHeader.substring(0, 2))) {
                // 所有英文地区，强制为：美国（英语）
                return LocaleSupportEnum.EN_US.getValue();
            }
        }
        return null;
    }

}
