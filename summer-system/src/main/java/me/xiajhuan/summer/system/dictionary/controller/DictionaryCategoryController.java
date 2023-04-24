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

package me.xiajhuan.summer.system.dictionary.controller;

import me.xiajhuan.summer.core.constant.OperationConst;
import me.xiajhuan.summer.core.data.PageData;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.log.annotation.LogOperation;
import me.xiajhuan.summer.system.dictionary.dto.DictionaryCategoryDto;
import me.xiajhuan.summer.system.dictionary.service.DictionaryCategoryService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典类别 Controller
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@RestController
@RequestMapping("dictionary/category")
public class DictionaryCategoryController {

    @Resource
    private DictionaryCategoryService mainService;

    //*******************Common Crud********************

    /**
     * 分页
     *
     * @param dto 字典类别Dto
     * @return 响应结果
     */
    @GetMapping("page")
    @RequiresPermissions("dictionary:category:page")
    @LogOperation(OperationConst.PAGE)
    public Result<PageData<DictionaryCategoryDto>> page(DictionaryCategoryDto dto) {
        return Result.ofSuccess(PageData.of(mainService.page(dto)));
    }

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 响应结果
     */
    @GetMapping("getById")
    @RequiresPermissions("dictionary:category:getById")
    @LogOperation(OperationConst.GET_BY_ID)
    public Result<DictionaryCategoryDto> getById(Long id) {
        AssertUtil.isNotNull("id", id);
        return Result.ofSuccess(mainService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 字典类别Dto
     * @return 响应结果
     */
    @PostMapping("add")
    @RequiresPermissions("dictionary:category:add")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.ADD)
    public Result add(@Validated(AddGroup.class) DictionaryCategoryDto dto) {
        mainService.add(dto);
        return Result.ofSuccess();
    }

    /**
     * 修改
     *
     * @param dto 字典类别Dto
     * @return 响应结果
     */
    @PutMapping("update")
    @RequiresPermissions("dictionary:category:update")
    @RateLimiter(0.5)
    @LogOperation(OperationConst.UPDATE)
    public Result update(@Validated(UpdateGroup.class) DictionaryCategoryDto dto) {
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
    @RequiresPermissions(value = {"dictionary:category:delete", "dictionary:item:delete"}, logical = Logical.AND)
    @RateLimiter(0.5)
    @LogOperation(OperationConst.DELETE)
    public Result delete(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);
        mainService.delete(ids);
        return Result.ofSuccess();
    }

    //*******************Other Operation********************

    /**
     * 全部
     *
     * @return 响应结果
     */
    @GetMapping("all")
    @LogOperation("全部")
    public Result<List<DictionaryCategoryDto>> all() {
        return Result.ofSuccess(mainService.all());
    }

}
