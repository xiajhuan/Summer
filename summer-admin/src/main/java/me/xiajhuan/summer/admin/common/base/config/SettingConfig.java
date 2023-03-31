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

package me.xiajhuan.summer.admin.common.base.config;

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
@Configuration("adminSettingConfig")
public class SettingConfig {

    /**
     * 注册 common.setting的配置文件Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingConst.COMMON)
    public Setting commonSetting() {
        return new Setting("setting/common.setting");
    }

    /**
     * 注册 business.setting的配置文件Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingConst.BUSINESS)
    public Setting businessSetting() {
        return new Setting("setting/business.setting");
    }

}
