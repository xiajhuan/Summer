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

import me.xiajhuan.summer.core.base.controller.BaseController;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.ExcelUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.common.annotation.LogOperation;
import me.xiajhuan.summer.system.security.dto.PasswordDto;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.service.SecurityUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户 Controller
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
@RestController
@RequestMapping("security/user")
public class SecurityUserController extends BaseController {

    @Resource
    private SecurityUserService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 用户Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("security:user:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<SecurityUserDto>> page(SecurityUserDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("security:user:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<SecurityUserDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 用户Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("security:user:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) SecurityUserDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 用户Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("security:user:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityUserDto dto) {
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
    @RequiresPermissions("security:user:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ofSuccess();
    }

    //*******************Excel Operation********************

    /**
     * Excel导出
     *
     * @param dto      用户Dto
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("excelExport")
    @RequiresPermissions("security:user:excelExport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(SecurityUserDto dto, HttpServletResponse response) {
        validateMaxExport(mainService.count(dto));
        ExcelUtil.export(response, "用户", "用户", mainService.list(dto),
                SecurityUserDto.class, ErrorCode.EXCEL_EXPORT_FAILURE);
    }

    //*******************Other Operation********************

    /**
     * 修改密码
     *
     * @param dto 密码Dto
     * @return 响应结果
     */
    @PutMapping("password")
    @LogOperation("修改密码")
    public Result password(@Validated(DefaultGroup.class) PasswordDto dto) {
        mainService.updatePasswordAndLogout(dto);
        return Result.ofSuccess();
    }

    /**
     * 重置密码
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @PutMapping("reset")
    @LogOperation("重置密码")
    public Result reset(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        String passwordReset = mainService.reset(ids);
        return passwordReset == null ? Result.ofFail() :
                Result.ofSuccess(null, Result.SuccessCode.PASSWORD_RESET, passwordReset);
    }

}
