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

package me.xiajhuan.summer.system.hook.runner;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.properties.ApplicationProperties;
import me.xiajhuan.summer.core.utils.SystemUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（对象存储服务加载完毕）
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OssReadyRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private ApplicationProperties applicationProperties;

    @Override
    public void run(ApplicationArguments args) {
        ApplicationProperties.Oss oss = applicationProperties.getOss();
        // 校验对象存储配置
        if (oss.isStrict()) {
            // TODO
        } else {
            // TODO
        }

        LOGGER.info("【{}】对象存储服务加载完毕，类型【{}】", SystemUtil.getApplicationName(), oss.getType());
    }

}
