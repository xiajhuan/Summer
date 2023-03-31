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

package me.xiajhuan.summer.core.config;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.interceptor.ContentTypeInterceptor;
import me.xiajhuan.summer.core.interceptor.SqlInjectionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * SpringMvc配置
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see WebMvcConfigurer
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource(name = SettingConst.CORE)
    private Setting setting;

    @Resource
    private ContentTypeInterceptor contentTypeInterceptor;

    @Resource
    private SqlInjectionInterceptor sqlInjectionInterceptor;

    /**
     * 允许的跨域请求方式数组
     */
    private String[] allowedMethodArray;

    /**
     * 跨域请求可被客户端缓存的时间（s）
     */
    private int maxAge;

    /**
     * 初始化 {@link allowedMethodArray} {@link maxAge}
     */
    @PostConstruct
    private void init() {
        String allowedMethod = setting.getByGroupWithLog("cors.allowed-method", "Http");
        if (StrUtil.isBlank(allowedMethod)) {
            // 没有配置时的默认请求方式
            allowedMethod = "GET,POST,PUT,DELETE,OPTIONS";
        }
        allowedMethodArray = allowedMethod.split(StrPool.COMMA);
        maxAge = setting.getInt("cors.max-age", "Http", 3600);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods(allowedMethodArray)
                .maxAge(maxAge);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册请求体类型 拦截器
        registry.addInterceptor(contentTypeInterceptor).order(Ordered.HIGHEST_PRECEDENCE)
                .addPathPatterns("/**").excludePathPatterns("/open/test/**");

        // 注册Sql注入 拦截器
        registry.addInterceptor(sqlInjectionInterceptor).order(Ordered.LOWEST_PRECEDENCE - 1)
                .addPathPatterns("/**").excludePathPatterns("/open/test/**");
    }

}