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

package me.xiajhuan.summer.core.validation.config;

import me.xiajhuan.summer.core.validation.resolver.CustomLocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.validation.Validator;

/**
 * 校验器配置
 *
 * @author xiajhuan
 * @date 2023/02/25
 * @see MessageSource
 */
@Configuration
public class ValidatorConfig {

    @Resource
    private MessageSource messageSource;

    /**
     * 注册{@link Validator}
     *
     * @return {@link Validator}
     */
    @Bean
    public Validator i18nValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * 注册{@link LocaleResolver}
     *
     * @return {@link LocaleResolver}
     */
    @Bean
    public LocaleResolver localeResolver() {
        return CustomLocaleResolver.of();
    }

}
