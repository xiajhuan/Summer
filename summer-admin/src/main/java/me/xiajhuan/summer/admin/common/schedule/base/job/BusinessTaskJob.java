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

package me.xiajhuan.summer.admin.common.schedule.base.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiajhuan.summer.core.constant.TimeUnitConst;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 业务模块基本定时任务
 *
 * @author xiajhuan
 * @date 2023/3/12
 */
@Setter
@Accessors(chain = true)
public class BusinessTaskJob {

    private static final Log LOGGER = LogFactory.get();

    /**
     * demo<br>
     * note：从启动时间开始，延迟 10s 后每隔 1分钟 执行
     */
    @Scheduled(fixedRate = TimeUnitConst.MINUTE, initialDelay = 10 * TimeUnitConst.SECOND)
    public void demo() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【BusinessTaskJob】【demo】Job开始执行：{}", DateUtil.date());

        Console.log("业务 XXX 开始执行...");
        Console.log("业务 XXX 执行完毕！");

        LOGGER.info("【BusinessTaskJob】【demo】Job执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

}
