package me.xiajhuan.summer.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import me.xiajhuan.summer.common.exception.ErrorCode;

/**
 * 消息国际化工具
 *
 * @author xiajhuan
 * @date 2023/2/24
 */
public class MessageUtil {

    /**
     * {@link MessageSource}
     */
    private static MessageSource messageSource;

    /**
     * 初始化 {@link messageSource}
     */
    static {
        messageSource = (MessageSource) SpringContextUtil.getBean("messageSource");
    }

    /**
     * 获取国际化消息
     *
     * @param code   错误编码 {@link ErrorCode}
     * @param params 消息填充参数
     * @return 国际化消息
     */
    public static String getI18nMessage(int code, String... params) {
        return messageSource.getMessage(String.valueOf(code), params, LocaleContextHolder.getLocale());
    }

}
