package me.xiajhuan.summer.server.common.config;

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
@Configuration("serverConfig")
public class SettingConfig {

    /**
     * 注册 server.setting的配置Bean
     *
     * @return {@link Setting}
     */
    @Bean(SettingBeanConst.SERVER)
    public Setting serverSetting() {
        return new Setting("setting/server.setting");
    }

}
