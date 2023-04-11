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

package me.xiajhuan.summer.system.security.controller;

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.system.security.service.SecurityDeptService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门 Controller
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
@RestController
@RequestMapping("security/dept")
public class SecurityDeptController {

    @Resource
    private SecurityDeptService mainService;

    //*******************Common Crud********************

    /**
     * 列表
     *
     * @return 响应结果
     */
    @GetMapping("list")
    @RequiresPermissions("security:dept:list")
    @LogOperation(OperationConst.LIST)
    public Result<List<SecurityDeptDto>> list() {
        return Result.ofSuccess(mainService.treeList(false));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("security:dept:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<SecurityDeptDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 部门Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("security:dept:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) SecurityDeptDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 部门Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("security:dept:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityDeptDto dto) {
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
    @RequiresPermissions("security:dept:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long id) {
        AssertUtil.isNotNull("id", id);
        mainService.delete(id);
        return Result.ofSuccess();
    }

    //*******************Other Operation********************

    /**
     * 全部<br>
     * note：该接口用于新增/修改角色时带出部门树
     *
     * @return 响应结果
     */
    @GetMapping("all")
    @RequiresPermissions(value = {"security:role:add", "security:role:update"}, logical = Logical.OR)
    @LogOperation("全部")
    public Result<List<SecurityDeptDto>> all() {
        return Result.ofSuccess(mainService.treeList(true));
    }

}
