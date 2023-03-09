package me.xiajhuan.summer.common.security.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.common.annotation.LogOperation;
import me.xiajhuan.summer.common.constant.OperationConst;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.data.PageData;
import me.xiajhuan.summer.common.data.Result;
import me.xiajhuan.summer.common.exception.BusinessException;
import me.xiajhuan.summer.common.exception.FileDownloadException;
import me.xiajhuan.summer.common.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.IpKeyStrategy;
import me.xiajhuan.summer.common.security.dto.RoleDto;
import me.xiajhuan.summer.common.security.entity.RoleEntity;
import me.xiajhuan.summer.common.security.excel.RoleExcel;
import me.xiajhuan.summer.common.security.excel.parser.RoleExcelDbParser;
import me.xiajhuan.summer.common.security.service.RoleService;
import me.xiajhuan.summer.common.utils.AssertUtil;
import me.xiajhuan.summer.common.utils.ExcelUtil;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色 Controller
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@RestController
@RequestMapping("security/role")
public class RoleController {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private RoleService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param pageAndSort 分页排序参数
     * @param dto         角色Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("c:security:role:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<RoleDto>> page(PageAndSort pageAndSort, RoleDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(pageAndSort, dto)));
    }

    /**
     * 新增
     *
     * @param dto 角色Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("c:security:role:add")
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) RoleDto dto) {
        try {
            mainService.add(dto);

            return Result.ofSuccess();
        } catch (Exception e) {
            LOGGER.error(e, StrTemplateConst.ERROR_LOG_MSG, OperationConst.ADD, e.getMessage());

            return Result.ofFail();
        }
    }

    /**
     * 修改
     *
     * @param dto 角色Dto
     * @return 响应结果
     */
    @PostMapping("update")
    @RequiresPermissions("c:security:role:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) RoleDto dto) {
        try {
            mainService.update(dto);

            return Result.ofSuccess();
        } catch (Exception e) {
            LOGGER.error(e, StrTemplateConst.ERROR_LOG_MSG, OperationConst.UPDATE, e.getMessage());

            return Result.ofFail();
        }
    }

    /**
     * 删除
     *
     * @param ids id数组
     * @return 响应结果
     * @throws BusinessException 业务异常
     */
    @DeleteMapping("delete")
    @RequiresPermissions("c:security:role:delete")
    @LogOperation(OperationConst.DELETE)
    public Result delete(String[] ids) throws BusinessException {
        AssertUtil.isNotEmpty("ids", ids);

        try {
            mainService.delete(ids);

            return Result.ofSuccess();
        } catch (Exception e) {
            LOGGER.error(e, StrTemplateConst.ERROR_LOG_MSG, OperationConst.DELETE, e.getMessage());

            return Result.ofFail();
        }
    }

    //*******************Excel Operation********************

    /**
     * Excel导入
     *
     * @param file {@link MultipartFile}
     * @return 响应结果
     */
    @PostMapping("excelImport")
    @RequiresPermissions("c:security:role:excelImport")
    @RateLimiter(value = 0.2, keyStrategy = IpKeyStrategy.class)
    @LogOperation(OperationConst.EXCEL_IMPORT)
    public Result excelImport(@RequestParam("file") MultipartFile file) {
        try {
            ExcelUtil.importToDb(file, RoleExcel.class, RoleExcelDbParser.of(mainService, RoleEntity.class));

            return Result.ofSuccess();
        } catch (Exception e) {
            LOGGER.error(e, StrTemplateConst.ERROR_LOG_MSG, OperationConst.EXCEL_IMPORT, e.getMessage());

            return Result.ofFail();
        }
    }

    /**
     * Excel模板下载
     *
     * @param response {@link HttpServletResponse}
     */
    @PostMapping("excelTemplate")
    @RequiresPermissions("c:security:role:excelTemplate")
    @RateLimiter(value = 0.2, keyStrategy = IpKeyStrategy.class)
    @LogOperation(OperationConst.EXCEL_TEMPLATE)
    public void excelTemplate(HttpServletResponse response) throws FileDownloadException {
        try {
            ExcelUtil.exportWithE(response, "角色模板", "角色", mainService.excelTemplate(), RoleExcel.class);
        } catch (Exception e) {
            throw FileDownloadException.of(e, "Excel模板下载失败");
        }
    }

    /**
     * Excel导出
     *
     * @param dto      角色Dto
     * @param response {@link HttpServletResponse}
     * @throws FileDownloadException 文件下载异常
     */
    @PostMapping("excelExport")
    @RequiresPermissions("c:security:role:excelExport")
    @RateLimiter(value = 0.2, keyStrategy = IpKeyStrategy.class)
    @LogOperation(OperationConst.EXCEL_EXPORT)
    public void excelExport(RoleDto dto, HttpServletResponse response) throws FileDownloadException {
        try {
            ExcelUtil.exportWithE(response, "角色", "角色", mainService.list(dto), RoleExcel.class);
        } catch (Exception e) {
            throw FileDownloadException.of(e, "Excel导出失败");
        }
    }

    //*******************Other Operation********************

}
