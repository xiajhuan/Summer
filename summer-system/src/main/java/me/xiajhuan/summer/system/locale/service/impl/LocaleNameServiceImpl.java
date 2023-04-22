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

package me.xiajhuan.summer.system.locale.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.properties.BatchLimitProperties;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.locale.dto.LocaleNameDto;
import me.xiajhuan.summer.system.locale.entity.LocaleNameEntity;
import me.xiajhuan.summer.system.locale.mapper.LocaleNameMapper;
import me.xiajhuan.summer.system.locale.service.LocaleNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 名称 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class LocaleNameServiceImpl extends ServiceImpl<LocaleNameMapper, LocaleNameEntity> implements LocaleNameService, MpHelper<LocaleNameDto, LocaleNameEntity> {

    @Resource
    private BatchLimitProperties batchLimitProperties;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LocaleNameEntity> getQueryWrapper(LocaleNameDto dto) {
        LambdaQueryWrapper<LocaleNameEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 表名
        String tableName = dto.getTableName();
        queryWrapper.eq(StrUtil.isNotBlank(tableName), LocaleNameEntity::getTableName, tableName);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LocaleNameEntity> getSelectWrapper(LocaleNameDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<LocaleNameEntity> addSelectField(LambdaQueryWrapper<LocaleNameEntity> queryWrapper) {
        return queryWrapper.select(LocaleNameEntity::getId, LocaleNameEntity::getTableName, LocaleNameEntity::getLineId,
                LocaleNameEntity::getFieldName, LocaleNameEntity::getFieldValue, LocaleNameEntity::getLocale,
                LocaleNameEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(LocaleNameDto dto) {
        // 行ID+地区语言不能重复
        long lineId = dto.getLineId();
        String locale = dto.getLocale();
        if (exist(lineId, locale)) {
            throw ValidationException.of(ErrorCode.NAME_LINE_ID_AND_LOCALE_EXISTS, String.valueOf(lineId), locale);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LocaleNameDto> page(LocaleNameDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LocaleNameDto.class);
    }

    @Override
    public List<LocaleNameDto> list(LocaleNameDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LocaleNameDto.class);
    }

    @Override
    public LocaleNameDto getById(Long id) {
        LambdaQueryWrapper<LocaleNameEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(LocaleNameEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), LocaleNameDto.class);
    }

    @Override
    public void add(LocaleNameDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, LocaleNameEntity.class));
    }

    @Override
    public void update(LocaleNameDto dto) {
        baseMapper.alwaysUpdateById(BeanUtil.convert(dto, LocaleNameEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public long count(LocaleNameDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public boolean exist(long lineId, String locale) {
        return baseMapper.exist(lineId, locale) != null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<LocaleNameEntity> entityList) {
        ListUtil.split(ListUtil.toList(entityList), batchLimitProperties.getRealSaveNumEveryTime())
                .forEach(baseMapper::realSaveBatch);
        return true;
    }

}
