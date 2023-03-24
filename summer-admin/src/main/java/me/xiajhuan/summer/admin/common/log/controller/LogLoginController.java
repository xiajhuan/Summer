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
import me.xiajhuan.summer.core.exception.FileDownloadException;
import me.xiajhuan.summer.admin.common.log.dto.LogLoginDto;
import me.xiajhuan.summer.admin.common.log.service.LogLoginService;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录日志 Controller
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@RestController
@RequestMapping("log/login")
public class LogLoginController {

    @Resource
    private LogLoginService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param pageAndSort 分页排序参数
     * @param dto         登录日志Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("log:login:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<LogLoginDto>> page(PageAndSort pageAndSort, LogLoginDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(pageAndSort, dto)));
    }

    //*******************Excel Operation********************

    /**
     * Excel导出
     *
     * @param dto      登录日志Dto
     * @param response {@link HttpServletResponse}
     * @throws FileDownloadException 文件下载异常
     */
    @GetMapping("excelExport")
    @RequiresPermissions("log:login:excelExport")
    @RateLimiter(0.2)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LogLoginDto dto, HttpServletResponse response) throws FileDownloadException {
        try {
            ExcelUtil.export(response, "登录日志", "登录日志", mainService.list(dto), LogLoginDto.class);
        } catch (Exception e) {
            throw FileDownloadException.of(e, ErrorCode.EXCEL_EXPORT_FAILURE);
        }
    }

}
