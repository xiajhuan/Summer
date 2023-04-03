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

import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册setting配置文件Bean
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Configuration
public class SettingConfig {

    /**
     * 注册 core.setting的配置文件Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingConst.CORE)
    public Setting coreSetting() {
        return new Setting("setting/core.setting");
    }

    /**
     * 注册 system.setting的配置文件Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingConst.SYSTEM)
    public Setting systemSetting() {
        return new Setting("setting/system.setting");
    }

}
