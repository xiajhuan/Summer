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

package me.xiajhuan.summer.system.message.controller;

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import me.xiajhuan.summer.system.message.dto.MessageMailDto;
import me.xiajhuan.summer.system.message.dto.SendMailDto;
import me.xiajhuan.summer.system.message.service.MessageMailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 邮件 Controller
 *
 * @author xiajhuan
 * @date 2023/5/9
 */
@RestController
@RequestMapping("message/mail")
public class MessageMailController {

    @Resource
    private MessageMailService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 邮件Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("message:mail:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<MessageMailDto>> page(MessageMailDto dto) {
        return Result.ok(PageData.of(mainService.page(dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("message:mail:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<MessageMailDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ok(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 邮件Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("message:mail:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result<Void> add(@Validated(AddGroup.class) MessageMailDto dto) {
        mainService.add(dto);
        return Result.ok();
    }

    /**
     * 修改
     *
     * @param dto 邮件Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("message:mail:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result<Void> update(@Validated(UpdateGroup.class) MessageMailDto dto) {
        mainService.update(dto);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @DeleteMapping("delete")
    @RequiresPermissions("message:mail:delete")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result<Void> delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ok();
    }

    //*******************Other Operation********************

    /**
     * 发送
     *
     * @param dto   发送邮件Dto
     * @param files {@link MultipartFile}数组
     * @return 响应结果
     */
    @PostMapping("send")
    @RequiresPermissions("message:mail:send")
    @RateLimiter(0.2)
    @LogOperation("发送")
    public Result<Void> send(@Validated(DefaultGroup.class) SendMailDto dto, MultipartFile[] files) {
        // note：若没有文件上传，files为null而不是空数组
        if (mainService.send(dto, files)) {
            return Result.ok();
        }
        return Result.fail();
    }

}
