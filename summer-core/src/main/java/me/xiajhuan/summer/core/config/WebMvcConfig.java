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

package me.xiajhuan.summer.core.config;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.interceptor.ContentTypeInterceptor;
import me.xiajhuan.summer.core.interceptor.FileUploadInterceptor;
import me.xiajhuan.summer.core.interceptor.SqlInjectionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.FileNotFoundException;

/**
 * SpringMvc配置
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see WebMvcConfigurer
 * @see CorsRegistry
 * @see InterceptorRegistry
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingConst.CORE)
    private Setting setting;

    /**
     * 允许的跨域请求方式数组
     */
    private String[] allowedMethodArray;

    /**
     * 跨域请求可被客户端缓存的时间（s）
     */
    private int maxAge;

    /**
     * 是否开启请求体类型过滤
     */
    private boolean enableContentTypeFilter;

    /**
     * 是否开启文件上传过滤
     */
    private boolean enableFileUploadFilter;

    /**
     * 是否开启Sql注入过滤
     */
    private boolean enableInjectionFilter;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        String allowedMethod = setting.getByGroupWithLog("cors.allowed-method", "Http");
        if (StrUtil.isBlank(allowedMethod)) {
            // 没有配置时的默认请求方式
            allowedMethod = "GET,POST,PUT,DELETE,OPTIONS";
        }
        allowedMethodArray = allowedMethod.split(StrPool.COMMA);
        maxAge = setting.getInt("cors.max-age", "Http", 1800);

        // 拦截器启用
        enableContentTypeFilter = setting.getBool("enable-content-type-filter", "Http", true);
        enableFileUploadFilter = setting.getBool("enable-file-upload-filter", "Http", true);
        enableInjectionFilter = setting.getBool("enable-injection-filter", "Sql", false);
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
        if (enableContentTypeFilter) {
            // 注册请求体类型拦截器
            registry.addInterceptor(ContentTypeInterceptor.of(setting)).order(Ordered.HIGHEST_PRECEDENCE)
                    .addPathPatterns("/**").excludePathPatterns("/api/test/**");
        }

        if (enableFileUploadFilter) {
            // 注册文件上传拦截器
            try {
                registry.addInterceptor(FileUploadInterceptor.of()).order(Ordered.HIGHEST_PRECEDENCE + 1)
                        .addPathPatterns("/**").excludePathPatterns("/api/test/**");
            } catch (FileNotFoundException e) {
                LOGGER.error(e, "注册文件上传拦截器失败【{}】", e.getMessage());
            }
        }

        if (enableInjectionFilter) {
            // 注册Sql注入拦截器
            registry.addInterceptor(SqlInjectionInterceptor.of(setting)).order(Ordered.LOWEST_PRECEDENCE - 1)
                    .addPathPatterns("/**").excludePathPatterns("/api/test/**");
        }
    }

}