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
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.security.dto.SecurityPostDto;
import me.xiajhuan.summer.system.security.service.SecurityPostService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 岗位 Controller
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
@RestController
@RequestMapping("security/post")
public class SecurityPostController {

    @Resource
    private SecurityPostService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 岗位Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("security:post:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<SecurityPostDto>> page(SecurityPostDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    /**
     * 列表
     *
     * @return 响应结果
     */
    @GetMapping("list")
    @RequiresPermissions("security:post:list")
    @LogOperation(OperationConst.LIST)
    public Result<List<SecurityPostDto>> list() {
        return Result.ofSuccess(mainService.list(1));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("security:post:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<SecurityPostDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 岗位Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("security:post:add")
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) SecurityPostDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 岗位Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("security:post:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityPostDto dto) {
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
    @RequiresPermissions("security:post:delete")
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ofSuccess();
    }

}
