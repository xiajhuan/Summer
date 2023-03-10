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
import me.xiajhuan.summer.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.common.security.service.SecurityDeptService;
import me.xiajhuan.summer.common.utils.AssertUtil;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 部门 Controller
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
@RestController
@RequestMapping("security/dept")
public class SecurityDeptController {

    private static final Log LOGGER = LogFactory.get();

    @Resource
    private SecurityDeptService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param pageAndSort 分页排序参数
     * @param dto         部门Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("security:dept:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<SecurityDeptDto>> page(PageAndSort pageAndSort, SecurityDeptDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(pageAndSort, dto)));
    }

    /**
     * 新增
     *
     * @param dto 部门Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("security:dept:add")
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) SecurityDeptDto dto) {
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
     * @param dto 部门Dto
     * @return 响应结果
     */
    @PostMapping("update")
    @RequiresPermissions("security:dept:update")
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) SecurityDeptDto dto) {
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
    @RequiresPermissions("security:dept:delete")
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

    //*******************Other Operation********************

}
