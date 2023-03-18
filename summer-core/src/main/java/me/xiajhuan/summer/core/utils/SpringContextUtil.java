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

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import net.dreamlu.mica.xss.config.MicaXssConfiguration;

/**
 * Spring容器工具<br>
 * note：这里需自定义Bean名称，否则会和 {@link MicaXssConfiguration#springContextUtil()} 注册的Bean冲突
 *
 * @author xiajhuan
 * @date 2022/11/22
 * @see ApplicationContextAware
 */
@Component("mySpringContextUtil")
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * {@link ApplicationContext}
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取Bean
     *
     * @param name 名称
     * @return Bean
     */
    public static Object getBean(String name) {
        return context == null ? null : context.getBean(name);
    }

    /**
     * 获取Bean
     *
     * @param type BeanClass
     * @param <T>  Bean类型
     * @return Bean
     */
    public static <T> T getBean(Class<T> type) {
        return context == null ? null : context.getBean(type);
    }

    /**
     * 获取Bean
     *
     * @param name 名称
     * @param type BeanClass
     * @param <T>  Bean类型
     * @return Bean
     */
    public static <T> T getBean(String name, Class<T> type) {
        return context == null ? null : context.getBean(name, type);
    }

    /**
     * 判断容器中是否存在Bean
     *
     * @param name 名称
     * @return 是否存在，true：存在 false：不存在
     */
    public static boolean containsBean(String name) {
        return context == null ? null : context.containsBean(name);
    }

    /**
     * 判断Bean是否是单例的
     *
     * @param name 名称
     * @return 是否是单例的，true：是 false：不是
     */
    public static boolean isSingleton(String name) {
        return context == null ? null : context.isSingleton(name);
    }

    /**
     * 获取BeanClass
     *
     * @param name 名称
     * @return BeanClass
     */
    public static Class<? extends Object> getType(String name) {
        return context == null ? null : context.getType(name);
    }

    /**
     * 获取所有Bean名称
     *
     * @param type BeanClass
     * @param <T>  Bean类型
     * @return Bean名称数组
     */
    public static <T> String[] getNameArray(Class<T> type) {
        return context == null ? null : context.getBeanNamesForType(type);
    }

    /**
     * 添加Bean
     *
     * @param name 名称
     * @param type BeanClass
     * @param <T>  Bean类型
     */
    public static <T> void addBean(String name, Class<T> type) {
        if (context != null) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) context.getParentBeanFactory();
            if (!beanDefinitionRegistry.containsBeanDefinition(name)) {
                beanDefinitionRegistry.registerBeanDefinition(name, BeanDefinitionBuilder.genericBeanDefinition(type).getBeanDefinition());
            }
        }
    }

    /**
     * 移除Bean
     *
     * @param name 名称
     */
    public static void removeBean(String name) {
        if (context != null) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) context.getParentBeanFactory();
            beanDefinitionRegistry.getBeanDefinition(name);
            beanDefinitionRegistry.removeBeanDefinition(name);
        }
    }

    /**
     * 获取应用名称（spring.application.name）
     *
     * @return 应用名称
     */
    public static String getApplicationName() {
        if (context != null) {
            return StrUtil.subBefore(context.getId(), StrPool.DASHED, true);
        }
        return null;
    }

    /**
     * 获取当前服务端口（server.port）
     *
     * @return 当前服务端口
     */
    public static String getServerPort() {
        return getBean(Environment.class).getProperty("server.port");
    }

}