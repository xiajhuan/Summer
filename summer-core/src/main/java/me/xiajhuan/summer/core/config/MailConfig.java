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

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件发送配置
 *
 * @author xiajhuan
 * @date 2023/5/8
 * @see MailAccount
 */
@Configuration
public class MailConfig {

    /**
     * 注册{@link MailAccount}
     *
     * @param setting {@link Setting}
     * @return {@link MailAccount}
     */
    @Bean
    public MailAccount mailAccount(@Qualifier(SettingConst.CORE) Setting setting) {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(setting.getByGroupWithLog("smtp-host", "Mail"));
        mailAccount.setPort(setting.getInt("smtp-port", "Mail", 25));
        mailAccount.setSslEnable(setting.getBool("enable-ssl", "Mail", true));
        mailAccount.setFrom(setting.getByGroupWithLog("sender", "Mail"));
        // 是否需要用户名/密码验证
        mailAccount.setAuth(true);
        mailAccount.setUser(setting.getByGroupWithLog("username", "Mail"));
        mailAccount.setPass(setting.getByGroupWithLog("password", "Mail"));
        return mailAccount;
    }

}
