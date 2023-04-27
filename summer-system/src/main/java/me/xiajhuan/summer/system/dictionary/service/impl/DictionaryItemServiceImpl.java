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

package me.xiajhuan.summer.system.dictionary.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.dictionary.dto.DictionaryItemDto;
import me.xiajhuan.summer.system.dictionary.entity.DictionaryItemEntity;
import me.xiajhuan.summer.system.dictionary.mapper.DictionaryItemMapper;
import me.xiajhuan.summer.system.dictionary.service.DictionaryItemService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字典项 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class DictionaryItemServiceImpl extends ServiceImpl<DictionaryItemMapper, DictionaryItemEntity> implements DictionaryItemService, MpHelper<DictionaryItemDto, DictionaryItemEntity> {

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<DictionaryItemEntity> getQueryWrapper(DictionaryItemDto dto) {
        // 类别ID不能为空
        Long categoryId = dto.getCategoryId();
        if (categoryId == null) {
            throw ValidationException.of(ErrorCode.CATEGORY_ID_NOT_NULL);
        }

        LambdaQueryWrapper<DictionaryItemEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 类别ID
        queryWrapper.eq(DictionaryItemEntity::getCategoryId, categoryId);
        // 标签或值
        String labelOrValue = dto.getLabelOrValue();
        // Sql片段示例：AND (label = xxx OR value = xxx)
        queryWrapper.and(StrUtil.isNotBlank(labelOrValue), i -> i.eq(DictionaryItemEntity::getLabel, labelOrValue)
                .or().eq(DictionaryItemEntity::getValue, labelOrValue));

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<DictionaryItemEntity> getSelectWrapper(DictionaryItemDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<DictionaryItemEntity> addSelectField(LambdaQueryWrapper<DictionaryItemEntity> queryWrapper) {
        return queryWrapper.select(DictionaryItemEntity::getId, DictionaryItemEntity::getLabel, DictionaryItemEntity::getValue,
                DictionaryItemEntity::getWeight, DictionaryItemEntity::getDescription, DictionaryItemEntity::getCreateTime,
                DictionaryItemEntity::getUpdateTime);
    }

    @Override
    public void handleDtoBefore(DictionaryItemDto dto) {
        // 值不能重复
        String value = dto.getValue();
        if (baseMapper.exist(dto.getCategoryId(), value) != null) {
            throw ValidationException.of(ErrorCode.VALUE_EXISTS, value);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<DictionaryItemDto> page(DictionaryItemDto dto) {
        dto.setField("weight");
        dto.setOrder("asc");
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), DictionaryItemDto.class);
    }

    @Override
    public DictionaryItemDto getById(Long id) {
        LambdaQueryWrapper<DictionaryItemEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(DictionaryItemEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), DictionaryItemDto.class);
    }

    @Override
    public void add(DictionaryItemDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, DictionaryItemEntity.class));
    }

    @Override
    public void update(DictionaryItemDto dto) {
        updateById(BeanUtil.convert(dto, DictionaryItemEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public List<DictionaryItemDto> list(Long categoryId) {
        LambdaQueryWrapper<DictionaryItemEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(DictionaryItemEntity::getCategoryId, categoryId);
        queryWrapper.select(DictionaryItemEntity::getLabel, DictionaryItemEntity::getValue);
        queryWrapper.orderByAsc(DictionaryItemEntity::getWeight);

        return BeanUtil.convert(baseMapper.selectList(queryWrapper), DictionaryItemDto.class);
    }

    @Override
    public void delete(Set<Long> categoryIdSet) {
        LambdaQueryWrapper<DictionaryItemEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.in(DictionaryItemEntity::getCategoryId, categoryIdSet);

        remove(queryWrapper);
    }

}
