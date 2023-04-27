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

package me.xiajhuan.summer.system.extend.controller;

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.constant.TreeConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import me.xiajhuan.summer.system.extend.dto.ExtendRegionDto;
import me.xiajhuan.summer.system.extend.service.ExtendRegionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 行政区域 Controller
 *
 * @author xiajhuan
 * @date 2023/4/27
 */
@RestController
@RequestMapping("extend/region")
public class ExtendRegionController {

    @Resource
    private ExtendRegionService mainService;

    //*******************Common Crud********************

    /**
     * 列表
     *
     * @return 响应结果
     */
    @GetMapping("list")
    @RequiresPermissions("extend:region:list")
    @LogOperation(OperationConst.LIST)
    public Result<List<ExtendRegionDto>> list() {
        return Result.ofSuccess(mainService.treeList(true));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("extend:region:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<ExtendRegionDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 行政区域Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("extend:region:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result<?> add(@Validated(AddGroup.class) ExtendRegionDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 行政区域Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("extend:region:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result<?> update(@Validated(UpdateGroup.class) ExtendRegionDto dto) {
        mainService.update(dto);
        return Result.ofSuccess();
    }

    /**
     * 删除
     *
     * @param id ID
     * @return 响应结果
     */
    @DeleteMapping("delete")
    @RequiresPermissions("extend:region:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result<?> delete(Long id) {
        AssertUtil.isNotNull("id", id);
        mainService.delete(id);
        return Result.ofSuccess();
    }

    //*******************Other Operation********************

    /**
     * 全部
     *
     * @return 响应结果
     */
    @GetMapping("all")
    @LogOperation("全部")
    public Result<List<ExtendRegionDto>> all() {
        return Result.ofSuccess(mainService.treeList(false));
    }

    /**
     * 根据上级ID列表
     *
     * @param parentId 上级ID
     * @return 响应结果
     */
    @GetMapping("listByParentId")
    @LogOperation("根据上级ID列表")
    public Result<List<ExtendRegionDto>> listByParentId(Long parentId) {
        return Result.ofSuccess(mainService.listByParentId(parentId == null ? TreeConst.ROOT : parentId));
    }

}
