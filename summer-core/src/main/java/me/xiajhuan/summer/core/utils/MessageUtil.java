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
import me.xiajhuan.summer.core.enums.RegionSupportEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import me.xiajhuan.summer.core.exception.ErrorCode;

import java.util.Locale;

/**
 * 消息国际化工具
 *
 * @author xiajhuan
 * @date 2023/2/24
 */
public class MessageUtil {

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
                .getByGroupWithLog("extra.default", "Locale");
        if (StrUtil.isBlank(extraDefault)) {
            // 没有配置则默认为：zh_CN
            extraDefault = RegionSupportEnum.ZH_CN.getName();
        }
    }

    /**
     * 获取国际化消息
     *
     * @param code   错误编码 {@link ErrorCode}
     * @param params 消息填充参数
     * @return 国际化消息
     * @see RegionSupportEnum
     */
    public static String getI18nMessage(int code, String... params) {
        // 优先取请求头“Accept-Language”中的设置
        Locale locale = HttpContextUtil.getLocale(HttpContextUtil.getHttpServletRequest());

        if (locale == null) {
            locale = LocaleContextHolder.getLocale();

            // 默认地区，中国（中文）
            Locale defaultLocale = RegionSupportEnum.ZH_CN.getValue();
            // 美国（英文）
            Locale englishLocale = RegionSupportEnum.EN_US.getValue();
            if (locale.getLanguage().equals(defaultLocale.getLanguage())) {
                // 所有中文地区，强制为：中国（中文）
                locale = defaultLocale;
            } else if (locale.getLanguage().equals(englishLocale.getLanguage())) {
                // 所有英文地区，强制为：美国（英语）
                locale = englishLocale;
            } else {
                // note：如果“locale.toLanguageTag()”输出为“und”，则请求头“Accept-Language”的值在java.util.Locale中未被定义
                LOGGER.warn("中英文以外的地区【{}】，自动调整为默认地区【{}】", locale.toLanguageTag(), extraDefault);

                locale = RegionSupportEnum.ZH_CN.getName().equalsIgnoreCase(extraDefault)
                        ? defaultLocale : englishLocale;
            }
        }

        return messageSource.getMessage(String.valueOf(code), params, locale);
    }

}
