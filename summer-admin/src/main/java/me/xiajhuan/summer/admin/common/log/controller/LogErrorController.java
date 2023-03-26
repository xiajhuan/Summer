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

package me.xiajhuan.summer.admin.common.log.controller;

import me.xiajhuan.summer.admin.common.base.annotation.LogOperation;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageAndSort;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.ErrorCode;
import me.xiajhuan.summer.admin.common.log.dto.LogErrorDto;
import me.xiajhuan.summer.admin.common.log.service.LogErrorService;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 错误日志 Controller
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@RestController
@RequestMapping("log/error")
public class LogErrorController {

    @Resource
    private LogErrorService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param pageAndSort 分页排序参数
     * @param dto         错误日志Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("log:error:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<LogErrorDto>> page(PageAndSort pageAndSort, LogErrorDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(pageAndSort, dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("log:error:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<LogErrorDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    //*******************Excel Operation********************

    /**
     * Excel导出
     *
     * @param dto      错误日志Dto
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("excelExport")
    @RequiresPermissions("log:error:excelExport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LogErrorDto dto, HttpServletResponse response) {
        ExcelUtil.export(response, "错误日志", "错误日志", mainService.list(dto),
                LogErrorDto.class, ErrorCode.EXCEL_EXPORT_FAILURE);
    }

}
