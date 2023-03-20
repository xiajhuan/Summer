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
public class CustomLocaleResolver implements LocaleResolver {

    /**
     * 构造CustomLocaleResolver
     */
    private CustomLocaleResolver() {
    }

    /**
     * 构建CustomLocaleResolver
     *
     * @return CustomLocaleResolver
     */
    public static CustomLocaleResolver of() {
        return new CustomLocaleResolver();
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
