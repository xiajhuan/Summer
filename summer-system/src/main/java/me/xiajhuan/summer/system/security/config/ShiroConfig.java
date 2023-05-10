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

package me.xiajhuan.summer.system.security.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.system.security.oauth2.Oauth2Filter;
import me.xiajhuan.summer.system.security.oauth2.Oauth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author xiajhuan
 * @date 2023/02/27
 */
@Configuration
public class ShiroConfig {

    /**
     * 白名单标识
     */
    private static final String ANON = "anon";

    /**
     * Oauth2标识
     */
    private static final String OAUTH2 = "oauth2";

    /**
     * 注册{@link DefaultWebSessionManager}
     *
     * @return {@link DefaultWebSessionManager}
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 不使用JSESSIONID
        sessionManager.setSessionIdCookieEnabled(false);
        // 不开启Session定时校验
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    /**
     * 注册{@link SecurityManager}
     *
     * @param oauth2Realm    Oauth2Realm
     * @param sessionManager {@link SessionManager}
     * @return {@link SecurityManager}
     */
    @Bean
    public SecurityManager securityManager(Oauth2Realm oauth2Realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oauth2Realm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    /**
     * 注册{@link ShiroFilter}
     *
     * @param securityManager {@link SecurityManager}
     * @return {@link ShiroFilterFactoryBean}
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, @Qualifier(SettingConst.SYSTEM) Setting setting) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // 添加Oauth2过滤
        Map<String, Filter> filterMap = MapUtil.newHashMap(1);
        filterMap.put("oauth2", Oauth2Filter.of());
        shiroFilter.setFilters(filterMap);

        // 白名单URI过滤
        Map<String, String> filterUriMap = MapUtil.newHashMap(true);
        // 默认过滤的URI
        filterUriMap.put("/webjars/**", ANON);
        filterUriMap.put("/favicon.ico", ANON);
        filterUriMap.put("/appMonitor/**", ANON);
        filterUriMap.put("/security/captcha", ANON);
        filterUriMap.put("/security/login", ANON);

        // 自定义过滤的URI
        String anonUris = setting.getByGroup("anon-uris", "Security");
        if (StrUtil.isNotBlank(anonUris)) {
            Arrays.stream(anonUris.split(StrPool.COMMA))
                    .forEach(anonUri -> filterUriMap.put(anonUri, ANON));
        }

        filterUriMap.put("/**", OAUTH2);
        shiroFilter.setFilterChainDefinitionMap(filterUriMap);

        return shiroFilter;
    }

    /**
     * 注册{@link LifecycleBeanPostProcessor}
     *
     * @return {@link LifecycleBeanPostProcessor}
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 注册{@link AuthorizationAttributeSourceAdvisor}
     *
     * @param securityManager {@link SecurityManager}
     * @return {@link AuthorizationAttributeSourceAdvisor}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}