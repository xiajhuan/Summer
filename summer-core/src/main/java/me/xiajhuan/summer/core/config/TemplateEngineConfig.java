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

import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.TemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 模板引擎配置
 *
 * @author xiajhuan
 * @date 2023/5/10
 * @see TemplateEngine
 */
@Configuration
public class TemplateEngineConfig {

    /**
     * 注册{@link TemplateEngine}
     *
     * @return {@link TemplateEngine}
     */
    @Bean
    public TemplateEngine templateEngine() {
        return TemplateFactory.create();
    }

}
