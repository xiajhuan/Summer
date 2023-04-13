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

package me.xiajhuan.summer.system.monitor.controller;

import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.monitor.service.MonitorSystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统 Controller
 *
 * @author xiajhuan
 * @date 2023/4/13
 */
@RestController
@RequestMapping("monitor/system")
public class MonitorSystemController {

    @Resource
    private MonitorSystemService mainService;

    //*******************Other Operation********************

    /**
     * 信息
     *
     * @return 响应结果
     */
    @GetMapping("info")
    @RequiresPermissions("monitor:system:info")
    @LogOperation("信息")
    public Result info() {
        return Result.ofSuccess(mainService.info());
    }

}
