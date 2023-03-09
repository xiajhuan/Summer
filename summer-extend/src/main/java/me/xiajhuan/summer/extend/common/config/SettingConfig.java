package me.xiajhuan.summer.extend.common.config;

import cn.hutool.setting.Setting;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册setting配置文件Bean
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Configuration("extendConfig")
public class SettingConfig {

    /**
     * 注册 extend.setting的配置Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingBeanConst.EXTEND)
    public Setting extendSetting() {
        return new Setting("setting/extend.setting");
    }

}
