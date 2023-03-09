package me.xiajhuan.summer.common.log.controller;

import me.xiajhuan.summer.common.annotation.LogOperation;
import me.xiajhuan.summer.common.constant.OperationConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.data.PageData;
import me.xiajhuan.summer.common.data.Result;
import me.xiajhuan.summer.common.exception.BusinessException;
import me.xiajhuan.summer.common.exception.FileDownloadException;
import me.xiajhuan.summer.common.log.dto.LogErrorDto;
import me.xiajhuan.summer.common.log.excel.LogErrorExcel;
import me.xiajhuan.summer.common.log.service.LogErrorService;
import me.xiajhuan.summer.common.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.IpKeyStrategy;
import me.xiajhuan.summer.common.utils.AssertUtil;
import me.xiajhuan.summer.common.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Result<LogErrorDto> getById(Long id) throws BusinessException {
        AssertUtil.isNotNull("id", id);

        return Result.ofSuccess(mainService.getById(id));
    }

    //*******************Excel Operation********************

    /**
     * Excel导出
     *
     * @param dto      错误日志Dto
     * @param response {@link HttpServletResponse}
     * @throws FileDownloadException 文件下载异常
     */
    @PostMapping("excelExport")
    @RequiresPermissions("log:error:excelExport")
    @RateLimiter(value = 0.2, keyStrategy = IpKeyStrategy.class)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(LogErrorDto dto, HttpServletResponse response) throws FileDownloadException {
        try {
            ExcelUtil.exportWithE(response, "错误日志", "错误日志", mainService.list(dto), LogErrorExcel.class);
        } catch (Exception e) {
            throw FileDownloadException.of(e, "Excel导出失败");
        }
    }

    //*******************Other Operation********************

}
