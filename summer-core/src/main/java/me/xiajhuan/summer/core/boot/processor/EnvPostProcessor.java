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

package me.xiajhuan.summer.core.boot.processor;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.List;

/**
 * EnvironmentPostProcessor（加载环境配置yml文件）
 *
 * @author xiajhuan
 * @date 2023/4/2
 * @see EnvironmentPostProcessor
 */
@Configuration
public class EnvPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        loadEnvYml(environment.getPropertySources());
    }

    /**
     * 加载环境配置yml文件<br>
     * admin模块env下：summer-dev/pre/prod/test.yml
     *
     * @param propertySources {@link MutablePropertySources}
     */
    private void loadEnvYml(MutablePropertySources propertySources) {
        // yml配置加载器
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        // 资源路径匹配解析器
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:env/summer-*.yml");
            if (ArrayUtil.isNotEmpty(resources)) {
                for (Resource resource : resources) {
                    List<PropertySource<?>> load = loader.load(resource.getFilename(), resource);
                    load.forEach(propertySources::addLast);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}