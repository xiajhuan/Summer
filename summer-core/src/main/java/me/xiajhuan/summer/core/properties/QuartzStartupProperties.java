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

package me.xiajhuan.summer.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.quartz.Scheduler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 定时任务启动
 *
 * @author xiajhuan
 * @date 2023/4/21
 */
@Setter
@Getter
@Component
@ConfigurationProperties("quartz.startup")
public class QuartzStartupProperties {

    /**
     * 是否自动启动，true：是 false：否
     * <p>
     * note：不自动启动时可通过调用
     * {@link Scheduler#start()} 或 {@link Scheduler#startDelayed(int)}
     * 启动定时任务，通常仅在不需要定时任务执行的开发/测试环境下设置为“false”
     * </p>
     */
    private boolean auto = true;

    /**
     * 延迟启动时间（s），只有auto = true 时会生效
     */
    private int delay = 20;

}
