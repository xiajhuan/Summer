package me.xiajhuan.summer.common.log.controller;

import me.xiajhuan.summer.common.annotation.LogOperation;
import me.xiajhuan.summer.common.constant.OperationConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.data.PageData;
import me.xiajhuan.summer.common.data.Result;
import me.xiajhuan.summer.common.exception.FileDownloadException;
import me.xiajhuan.summer.common.log.dto.LogLoginDto;
import me.xiajhuan.summer.common.log.excel.LogLoginExcel;
import me.xiajhuan.summer.common.log.service.LogLoginService;
import me.xiajhuan.summer.common.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.IpKeyStrategy;
import me.xiajhuan.summer.common.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @RequiresPermissions("c:log:login:page")
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
    @PostMapping("excelExport")
    @RequiresPermissions("c:log:login:excelExport")
    @RateLimiter(value = 0.2, keyStrategy = IpKeyStrategy.class)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LogLoginDto dto, HttpServletResponse response) throws FileDownloadException {
        try {
            ExcelUtil.exportWithE(response, "登录日志", "登录日志", mainService.list(dto), LogLoginExcel.class);
        } catch (Exception e) {
            throw FileDownloadException.of(e, "Excel导出失败");
        }
    }

    //*******************Other Operation********************

}
