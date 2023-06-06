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

package me.xiajhuan.summer.system.log.controller;

import me.xiajhuan.summer.core.base.controller.BaseController;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.ExcelUtil;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import me.xiajhuan.summer.system.log.dto.LogTaskDto;
import me.xiajhuan.summer.system.log.service.LogTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 定时任务日志 Controller
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
@RestController
@RequestMapping("log/task")
public class LogTaskController extends BaseController {

    @Resource
    private LogTaskService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 定时任务日志Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("log:task:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<LogTaskDto>> page(LogTaskDto dto) {
        return Result.ok(PageData.of(mainService.page(dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("log:task:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<LogTaskDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ok(mainService.getById(id));
    }

    //*******************Excel Operation********************

    /**
     * Excel导出
     *
     * @param dto      定时任务日志Dto
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("excelExport")
    @RequiresPermissions("log:task:excelExport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LogTaskDto dto, HttpServletResponse response) {
        validateMaxExport(mainService.count(dto));
        ExcelUtil.export(response, "定时任务日志", "定时任务日志", mainService.list(dto),
                LogTaskDto.class, ErrorCode.EXCEL_EXPORT_FAILURE);
    }

}
