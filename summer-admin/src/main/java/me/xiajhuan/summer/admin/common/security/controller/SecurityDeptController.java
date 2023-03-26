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
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.BusinessException;
import me.xiajhuan.summer.admin.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.admin.common.security.service.SecurityDeptService;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
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
        return Result.ofSuccess(mainService.treeList());
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
     * @throws BusinessException 业务异常
     */
    @PutMapping("update")
    @RequiresPermissions("security:dept:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityDeptDto dto) throws BusinessException {
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
    @RequiresPermissions("security:dept:delete")
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long id) throws BusinessException {
        AssertUtil.isNotNull("id", id);
        mainService.delete(id);
        return Result.ofSuccess();
    }

}
