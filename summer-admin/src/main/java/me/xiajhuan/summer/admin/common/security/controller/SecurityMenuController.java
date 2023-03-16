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

package me.xiajhuan.summer.admin.common.security.controller;

import me.xiajhuan.summer.admin.common.base.annotation.LogOperation;
import me.xiajhuan.summer.admin.common.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.admin.common.security.service.SecurityMenuService;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.BusinessException;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
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
     * @throws BusinessException 业务异常
     */
    @GetMapping("getById")
    @RequiresPermissions("security:menu:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<SecurityMenuDto> getById(Long id) throws BusinessException {
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
     * @throws BusinessException 业务异常
     */
    @PutMapping("update")
    @RequiresPermissions("security:menu:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityMenuDto dto) throws BusinessException {
        mainService.update(dto);
        return Result.ofSuccess();
    }

    /**
     * 删除
     *
     * @param id ID
     * @return 响应结果
     * @throws BusinessException 业务异常
     */
    @DeleteMapping("delete")
    @RequiresPermissions("security:menu:delete")
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long id) throws BusinessException {
        AssertUtil.isNotNull("id", id);
        mainService.delete(id);
        return Result.ofSuccess();
    }

    //*******************Other Operation********************

}
