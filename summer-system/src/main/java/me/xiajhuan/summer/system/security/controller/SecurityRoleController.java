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

package me.xiajhuan.summer.system.security.controller;

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.security.dto.SecurityRoleDto;
import me.xiajhuan.summer.system.security.service.SecurityRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色 Controller
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@RestController
@RequestMapping("security/role")
public class SecurityRoleController {

    @Resource
    private SecurityRoleService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 角色Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("security:role:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<SecurityRoleDto>> page(SecurityRoleDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    /**
     * 新增
     *
     * @param dto 角色Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("security:role:add")
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) SecurityRoleDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 角色Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("security:role:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityRoleDto dto) {
        mainService.update(dto);
        return Result.ofSuccess();
    }

    /**
     * 删除
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @DeleteMapping("delete")
    @RequiresPermissions("security:role:delete")
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ofSuccess();
    }

}
