package me.xiajhuan.summer.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Filter配置
 *
 * @author xiajhuan
 * @date 2023/2/27
 */
@Configuration
public class FilterConfig {

    /**
     * 注册 {@link FilterRegistrationBean}
     *
     * @return {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false，表示生命周期由Spring容器管理，设置为true则表示由Servlet容器管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(1000);
        registration.addUrlPatterns("/*");
        return registration;
    }

}
