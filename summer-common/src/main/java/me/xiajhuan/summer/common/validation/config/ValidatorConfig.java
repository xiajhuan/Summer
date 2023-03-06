package me.xiajhuan.summer.common.validation.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Locale;

/**
 * 校验器配置
 *
 * @author xiajhuan
 * @date 2023/02/25
 */
@Configuration
public class ValidatorConfig {

    @Resource
    private MessageSource messageSource;

    /**
     * 注册 {@link Validator}
     *
     * @return {@link Validator}
     */
    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * 注册 {@link LocaleResolver}
     *
     * @return {@link LocaleResolver}
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        // 设置默认的Locale为简体中文
        acceptHeaderLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return acceptHeaderLocaleResolver;
    }

}
