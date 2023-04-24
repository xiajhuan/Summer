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

package me.xiajhuan.summer.system.locale.controller;

import me.xiajhuan.summer.core.base.controller.BaseController;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.ExcelUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import me.xiajhuan.summer.system.locale.dto.LocaleNameDto;
import me.xiajhuan.summer.system.locale.entity.LocaleNameEntity;
import me.xiajhuan.summer.system.locale.excel.parser.LocaleNameExcelDbParser;
import me.xiajhuan.summer.system.locale.service.LocaleNameService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 国际化名称 Controller
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@RestController
@RequestMapping("locale/name")
public class LocaleNameController extends BaseController {

    @Resource
    private LocaleNameService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 国际化名称Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("locale:name:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<LocaleNameDto>> page(LocaleNameDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("locale:name:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<LocaleNameDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 国际化名称Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("locale:name:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) LocaleNameDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 国际化名称Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("locale:name:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) LocaleNameDto dto) {
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
    @RequiresPermissions("locale:name:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ofSuccess();
    }

    //*******************Excel Operation********************

    /**
     * Excel模板下载
     *
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("excelTemplate")
    @RequiresPermissions("locale:name:excelTemplate")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_TEMPLATE)
    public void excelTemplate(HttpServletResponse response) {
        ExcelUtil.export(response, "国际化名称模板", "国际化名称", mainService.template(),
                LocaleNameDto.class, ErrorCode.EXCEL_TEMPLATE_DOWNLOAD_FAILURE);
    }

    /**
     * Excel导入
     *
     * @param file {@link MultipartFile}
     * @return 响应结果
     */
    @PostMapping("excelImport")
    @RequiresPermissions("locale:name:excelImport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_IMPORT)
    public Result excelImport(MultipartFile file) {
        ExcelUtil.importDb(file, LocaleNameDto.class,
                LocaleNameExcelDbParser.of(mainService, LocaleNameEntity.class));
        return Result.ofSuccess();
    }

    /**
     * Excel导出
     *
     * @param dto      国际化名称Dto
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("excelExport")
    @RequiresPermissions("locale:name:excelExport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LocaleNameDto dto, HttpServletResponse response) {
        validateMaxExport(mainService.count(dto));
        ExcelUtil.export(response, "国际化名称", "国际化名称", mainService.list(dto),
                LocaleNameDto.class, ErrorCode.EXCEL_EXPORT_FAILURE);
    }

}
