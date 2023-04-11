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

package me.xiajhuan.summer.system.common.schedule.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiajhuan.summer.core.constant.TimeUnitConst;
import me.xiajhuan.summer.system.security.service.SecurityDeptService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 权限相关定时任务
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
@Setter
@Accessors(chain = true)
public class SecurityTask {

    private static final Log LOGGER = LogFactory.get();

    private SecurityDeptService securityDeptService;

    /**
     * 缓存所有部门<br>
     * note：从启动时间开始，延迟 5s 后每隔 1周 执行
     */
    @Scheduled(fixedRate = TimeUnitConst.WEEK, initialDelay = 5 * TimeUnitConst.SECOND)
    public void cacheAllDept() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【SecurityTask】【cacheAllDept】开始执行：{}", DateUtil.date());

        securityDeptService.cacheAll();

        LOGGER.info("【SecurityTask】【cacheAllDept】执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

}