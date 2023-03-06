package me.xiajhuan.summer.common.config;

import cn.hutool.setting.Setting;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册setting配置文件Bean
 *
 * @author xiajhuan
 * @date 2022/11/23
 */
@Configuration
public class SettingConfig {

    /**
     * 注册 common.setting的配置Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingBeanConst.COMMON)
    public Setting commonSetting() {
        return new Setting("setting/common.setting");
    }

}
