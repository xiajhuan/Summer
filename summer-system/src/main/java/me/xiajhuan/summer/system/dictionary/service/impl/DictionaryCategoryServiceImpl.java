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
import me.xiajhuan.summer.system.dictionary.dto.DictionaryCategoryDto;
import me.xiajhuan.summer.system.dictionary.entity.DictionaryCategoryEntity;
import me.xiajhuan.summer.system.dictionary.mapper.DictionaryCategoryMapper;
import me.xiajhuan.summer.system.dictionary.service.DictionaryCategoryService;
import me.xiajhuan.summer.system.dictionary.service.DictionaryItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字典类别 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class DictionaryCategoryServiceImpl extends ServiceImpl<DictionaryCategoryMapper, DictionaryCategoryEntity> implements DictionaryCategoryService, MpHelper<DictionaryCategoryDto, DictionaryCategoryEntity> {

    @Resource
    private DictionaryItemService dictionaryItemService;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<DictionaryCategoryEntity> getQueryWrapper(DictionaryCategoryDto dto) {
        LambdaQueryWrapper<DictionaryCategoryEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 类别编码或名称
        String codeOrName = dto.getCodeOrName();
        if (StrUtil.isNotBlank(codeOrName)) {
            // Sql片段示例：AND (code = xxx OR name = xxx)
            queryWrapper.and(i -> i.eq(DictionaryCategoryEntity::getCode, codeOrName)
                    .or().eq(DictionaryCategoryEntity::getName, codeOrName));
        }

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<DictionaryCategoryEntity> getSelectWrapper(DictionaryCategoryDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<DictionaryCategoryEntity> addSelectField(LambdaQueryWrapper<DictionaryCategoryEntity> queryWrapper) {
        return queryWrapper.select(DictionaryCategoryEntity::getId, DictionaryCategoryEntity::getCode, DictionaryCategoryEntity::getName,
                DictionaryCategoryEntity::getDescription, DictionaryCategoryEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(DictionaryCategoryDto dto) {
        // 类别编码不能重复
        String code = dto.getCode();
        if (baseMapper.exist(code) != null) {
            throw ValidationException.of(ErrorCode.DICTIONARY_EXISTS, code);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<DictionaryCategoryDto> page(DictionaryCategoryDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), DictionaryCategoryDto.class);
    }

    @Override
    public DictionaryCategoryDto getById(Long id) {
        LambdaQueryWrapper<DictionaryCategoryEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(DictionaryCategoryEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), DictionaryCategoryDto.class);
    }

    @Override
    public void add(DictionaryCategoryDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, DictionaryCategoryEntity.class));
    }

    @Override
    public void update(DictionaryCategoryDto dto) {
        updateById(BeanUtil.convert(dto, DictionaryCategoryEntity.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        if (removeByIds(idSet)) {
            // 删除关联字典项
            dictionaryItemService.delete(idSet);
        }
    }

    @Override
    public List<DictionaryCategoryDto> all() {
        // 全部类别
        LambdaQueryWrapper<DictionaryCategoryEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.select(DictionaryCategoryEntity::getCode);
        List<DictionaryCategoryEntity> entityList = list(queryWrapper);

        if (entityList.size() > 0) {
            List<DictionaryCategoryDto> dtoList = BeanUtil.convert(entityList, DictionaryCategoryDto.class);
            // 字典项
            dtoList.forEach(dto -> dto.setItemList(dictionaryItemService.list(dto.getId())));

            return dtoList;
        }
        return null;
    }

}
