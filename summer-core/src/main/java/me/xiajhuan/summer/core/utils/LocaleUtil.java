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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import me.xiajhuan.summer.core.exception.ErrorCode;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 国际化工具
 * <p>
 * Locale：表示特定的地理，政治，或文化区域
 * 优先级从上到下依次：
 * <pre>
 *     1.请求头“Accept-Language”有值且以“zh”或“en”开头，
 *       以“Accept-Language”为准
 *     2.请求头“Accept-Language”有值但不以“zh”或“en”开头，
 *       以core.setting中extra-default配置值为准
 *     3.请求头“Accept-Language”没有值，
 *       取本地操作系统默认Locale，若语言不是“zh”或“en”，
 *       以core.setting中extra-default配置值为准
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
     * {@link MessageSource}
     */
    private static MessageSource messageSource;

    /**
     * 中英文以外地区的默认语言
     */
    private static String extraDefault;

    /**
     * 初始化 {@link messageSource} {@link extraDefault}
     */
    static {
        messageSource = (MessageSource) SpringContextUtil.getBean("messageSource");
        extraDefault = SpringContextUtil.getBean(SettingBeanConst.CORE, Setting.class)
                .getByGroupWithLog("extra-default", "Locale");
        if (StrUtil.isBlank(extraDefault)) {
            // 没有配置则默认为：zh_CN
            extraDefault = LocaleSupportEnum.ZH_CN.getName();
        }
    }

    /**
     * 获取国际化消息
     *
     * @param code   错误编码 {@link ErrorCode}
     * @param params 消息填充参数
     * @return 国际化消息
     * @see LocaleSupportEnum
     */
    public static String getI18nMessage(int code, String... params) {
        return messageSource.getMessage(String.valueOf(code), params, getLocale());
    }

    /**
     * 获取 {@link Locale}
     *
     * @return {@link Locale}
     */
    public static Locale getLocale() {
        // 优先取请求头“Accept-Language”的值
        Locale locale = getLocaleAcceptLanguage(HttpContextUtil.getHttpServletRequest());

        if (locale == null) {
            locale = LocaleContextHolder.getLocale();

            // 默认地区，中国（中文）
            Locale defaultLocale = LocaleSupportEnum.ZH_CN.getValue();
            // 美国（英文）
            Locale englishLocale = LocaleSupportEnum.EN_US.getValue();
            if (locale.getLanguage().equals(defaultLocale.getLanguage())) {
                // 所有中文地区，强制为：中国（中文）
                locale = defaultLocale;
            } else if (locale.getLanguage().equals(englishLocale.getLanguage())) {
                // 所有英文地区，强制为：美国（英语）
                locale = englishLocale;
            } else {
                // note：如果“locale.toLanguageTag()”输出为“und”，则请求头“Accept-Language”的值在java.util.Locale中未被定义
                LOGGER.warn("中英文以外的地区【{}】，自动调整为默认地区【{}】", locale.toLanguageTag(), extraDefault);

                locale = LocaleSupportEnum.ZH_CN.getName().equalsIgnoreCase(extraDefault)
                        ? defaultLocale : englishLocale;
            }
        }

        return locale;
    }

    /**
     * 根据请求头“Accept-Language”获取 {@link Locale}
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Locale}
     */
    private static Locale getLocaleAcceptLanguage(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 请求语言
        String languageHeader = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (languageHeader != null && languageHeader.length() >= 2) {
            if ("zh".equals(languageHeader.substring(0, 2))) {
                // 所有中文地区，强制为：中国（中文）
                return LocaleSupportEnum.ZH_CN.getValue();
            } else if ("en".equals(languageHeader.substring(0, 2))) {
                // 所有英文地区，强制为：美国（英语）
                return LocaleSupportEnum.EN_US.getValue();
            }
        }
        return null;
    }

}
