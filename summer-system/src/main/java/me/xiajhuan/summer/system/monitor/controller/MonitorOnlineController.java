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

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.monitor.dto.MonitorOnlineDto;
import me.xiajhuan.summer.system.monitor.service.MonitorOnlineService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 在线用户 Controller
 *
 * @author xiajhuan
 * @date 2023/4/12
 */
@RestController
@RequestMapping("monitor/online")
public class MonitorOnlineController {

    @Resource
    private MonitorOnlineService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 在线用户Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("monitor:online:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<MonitorOnlineDto>> list(MonitorOnlineDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    //*******************Other Operation********************

    /**
     * 踢出
     *
     * @param userIds 用户ID数组
     * @return 响应结果
     */
    @DeleteMapping("kick")
    @LogOperation("踢出")
    public Result kick(Long[] userIds) {
        AssertUtil.isNotEmpty("userIds", userIds);
        mainService.kick(userIds);
        return Result.ofSuccess();
    }

}
