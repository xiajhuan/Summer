package me.xiajhuan.summer.common.utils;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Spring容器工具
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * {@link ApplicationContext}
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取Bean
     *
     * @param name 名称
     * @return Bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取Bean
     *
     * @param type BeanClass
     * @param <T>  Bean类型
     * @return Bean
     */
    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
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
        return applicationContext.getBean(name, type);
    }

    /**
     * 判断容器中是否存在Bean
     *
     * @param name 名称
     * @return 是否存在，true：存在 false：不存在
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断Bean是否是单例的
     *
     * @param name 名称
     * @return 是否是单例的，true：是 false：不是
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    /**
     * 获取BeanClass
     *
     * @param name 名称
     * @return BeanClass
     */
    public static Class<? extends Object> getType(String name) {
        return applicationContext.getType(name);
    }

    /**
     * 获取所有Bean名称
     *
     * @param type BeanClass
     * @param <T>  Bean类型
     * @return Bean名称数组
     */
    public static <T> String[] getNameArray(Class<T> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    /**
     * 添加Bean
     *
     * @param name 名称
     * @param type BeanClass
     * @param <T>  Bean类型
     */
    public static <T> void addBean(String name, Class<T> type) {
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getParentBeanFactory();
        if (!beanDefinitionRegistry.containsBeanDefinition(name)) {
            beanDefinitionRegistry.registerBeanDefinition(name, BeanDefinitionBuilder.genericBeanDefinition(type).getBeanDefinition());
        }
    }

    /**
     * 移除Bean
     *
     * @param name 名称
     */
    public static void removeBean(String name) {
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getParentBeanFactory();
        beanDefinitionRegistry.getBeanDefinition(name);
        beanDefinitionRegistry.removeBeanDefinition(name);
    }

    /**
     * 获取应用名称，spring.application.name
     *
     * @return 应用名称
     */
    public static String getApplicationName() {
        return StrUtil.subBefore(applicationContext.getId(), StrPool.DASHED, true);
    }

    /**
     * 获取服务端口，server.port
     *
     * @return 服务端口
     */
    public static String getServerPort() {
        return getBean(Environment.class).getProperty("server.port");
    }

}