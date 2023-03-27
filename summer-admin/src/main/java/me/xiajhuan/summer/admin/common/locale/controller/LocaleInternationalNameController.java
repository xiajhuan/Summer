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

package me.xiajhuan.summer.admin.common.locale.controller;

import me.xiajhuan.summer.admin.common.base.annotation.LogOperation;
import me.xiajhuan.summer.admin.common.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.admin.common.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.admin.common.locale.excel.parser.LocaleInternationalNameExcelDbParser;
import me.xiajhuan.summer.admin.common.locale.service.LocaleInternationalNameService;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageAndSort;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.ErrorCode;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.ExcelUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
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
@RequestMapping("locale/internationalName")
public class LocaleInternationalNameController {

    @Resource
    private LocaleInternationalNameService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param pageAndSort 分页排序参数
     * @param dto         国际化名称Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("locale:internationalName:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<LocaleInternationalNameDto>> page(PageAndSort pageAndSort, LocaleInternationalNameDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(pageAndSort, dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("locale:internationalName:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<LocaleInternationalNameDto> getById(Long id) {
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
    @RequiresPermissions("locale:internationalName:add")
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) LocaleInternationalNameDto dto) {
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
    @RequiresPermissions("locale:internationalName:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) LocaleInternationalNameDto dto) {
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
    @RequiresPermissions("locale:internationalName:delete")
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
    @RequiresPermissions("locale:internationalName:excelTemplate")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_TEMPLATE)
    public void excelTemplate(HttpServletResponse response) {
        ExcelUtil.export(response, "国际化名称模板", "国际化名称", mainService.excelTemplate(),
                LocaleInternationalNameDto.class, ErrorCode.EXCEL_TEMPLATE_DOWNLOAD_FAILURE);
    }

    /**
     * Excel导入
     *
     * @param file {@link MultipartFile}
     * @return 响应结果
     */
    @PostMapping("excelImport")
    @RequiresPermissions("locale:internationalName:excelImport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_IMPORT)
    public Result excelImport(MultipartFile file) {
        ExcelUtil.importDb(file, LocaleInternationalNameDto.class,
                LocaleInternationalNameExcelDbParser.of(mainService, LocaleInternationalNameEntity.class));
        return Result.ofSuccess();
    }

    /**
     * Excel导出
     *
     * @param dto      国际化名称Dto
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("excelExport")
    @RequiresPermissions("locale:internationalName:excelExport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LocaleInternationalNameDto dto, HttpServletResponse response) {
        ExcelUtil.export(response, "国际化名称", "国际化名称", mainService.list(dto),
                LocaleInternationalNameDto.class, ErrorCode.EXCEL_EXPORT_FAILURE);
    }

}
