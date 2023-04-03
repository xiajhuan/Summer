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
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.system.security.service.SecurityMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单 Controller
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@RestController
@RequestMapping("security/menu")
public class SecurityMenuController {

    @Resource
    private SecurityMenuService mainService;

    //*******************Common Crud********************

    /**
     * 列表
     *
     * @return 响应结果
     */
    @GetMapping("list")
    @RequiresPermissions("security:menu:list")
    @LogOperation(OperationConst.LIST)
    public Result<List<SecurityMenuDto>> list() {
        return Result.ofSuccess(mainService.treeList());
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("security:menu:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<SecurityMenuDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 菜单Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("security:menu:add")
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) SecurityMenuDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 菜单Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("security:menu:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityMenuDto dto) {
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
    @RequiresPermissions("security:menu:delete")
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long id) {
        AssertUtil.isNotNull("id", id);
        mainService.delete(id);
        return Result.ofSuccess();
    }

}