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

package me.xiajhuan.summer.system.schedule.controller;

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import me.xiajhuan.summer.system.schedule.dto.ScheduleTaskDto;
import me.xiajhuan.summer.system.schedule.service.ScheduleTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 定时任务 Controller
 *
 * @author xiajhuan
 * @date 2023/4/17
 */
@RestController
@RequestMapping("schedule/task")
public class ScheduleTaskController {

    @Resource
    private ScheduleTaskService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 定时任务Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("schedule:task:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<ScheduleTaskDto>> page(ScheduleTaskDto dto) {
        return Result.ok(PageData.of(mainService.page(dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("schedule:task:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<ScheduleTaskDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ok(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 定时任务Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("schedule:task:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result<?> add(@Validated(AddGroup.class) ScheduleTaskDto dto) {
        mainService.add(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto 定时任务Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("schedule:task:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result<?> update(@Validated(UpdateGroup.class) ScheduleTaskDto dto) {
        mainService.update(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @DeleteMapping("delete")
    @RequiresPermissions("schedule:task:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result<?> delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ok();
    }

    //*******************Other Operation********************

    /**
     * 执行
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @PostMapping("execute")
    @RequiresPermissions("schedule:task:execute")
    @RateLimiter(0.5)
    @LogOperation("执行")
    public Result<?> execute(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.execute(ids);
        return Result.ok();
    }

    /**
     * 暂停
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @PutMapping("pause")
    @RequiresPermissions("schedule:task:pause")
    @RateLimiter(0.5)
    @LogOperation("暂停")
    public Result<?> pause(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.pause(ids);
        return Result.ok();
    }

    /**
     * 恢复
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @PutMapping("resume")
    @RequiresPermissions("schedule:task:resume")
    @RateLimiter(0.5)
    @LogOperation("恢复")
    public Result<?> resume(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.resume(ids);
        return Result.ok();
    }

    /**
     * 手动启动
     *
     * @return 响应结果
     */
    @PostMapping("manualStart")
    @LogOperation("手动启动")
    public Result<?> manualStart() {
        mainService.manualStart();
        return Result.ok();
    }

}
