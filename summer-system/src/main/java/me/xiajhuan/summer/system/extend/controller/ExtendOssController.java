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

package me.xiajhuan.summer.system.extend.controller;

import me.xiajhuan.summer.core.base.controller.BaseController;
import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import me.xiajhuan.summer.system.extend.dto.ExtendOssDto;
import me.xiajhuan.summer.system.extend.service.ExtendOssService;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 对象存储 Controller
 *
 * @author xiajhuan
 * @date 2023/4/30
 */
@RestController
@RequestMapping("extend/oss")
public class ExtendOssController extends BaseController {

    @Resource
    private ExtendOssService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 对象存储Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("extend:oss:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<ExtendOssDto>> page(ExtendOssDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    /**
     * 删除
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @DeleteMapping("delete")
    @RequiresPermissions("extend:oss:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result<?> delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ofSuccess();
    }

    //*******************Other Operation********************

    /**
     * 批量上传
     *
     * @param files {@link MultipartFile}数组
     * @return 响应结果
     */
    @PostMapping("uploadBatch")
    @RequiresPermissions("extend:oss:uploadBatch")
    @RateLimiter(0.2)
    @LogOperation("批量上传")
    public Result<?> uploadBatch(MultipartFile[] files) {
        // note：若没有文件上传，files为null而不是空数组
        AssertUtil.isNotNull("files", files);
        mainService.addBatch(multiFileUpload(files));
        return Result.ofSuccess();
    }

    /**
     * 下载
     *
     * @param dto      对象存储Dto
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("download")
    @RequiresPermissions("extend:oss:download")
    @RateLimiter(0.2)
    @LogOperation("下载")
    public void download(@Validated(DefaultGroup.class) ExtendOssDto dto, HttpServletResponse response) {
        fileDownload(dto.getPath(), dto.getName(), response);
    }

}
