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
import me.xiajhuan.summer.core.properties.LimitBatchProperties;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.system.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.system.locale.mapper.LocaleInternationalNameMapper;
import me.xiajhuan.summer.system.locale.service.LocaleInternationalNameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 国际化名称 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class LocaleInternationalNameServiceImpl extends ServiceImpl<LocaleInternationalNameMapper, LocaleInternationalNameEntity> implements LocaleInternationalNameService, MpHelper<LocaleInternationalNameDto, LocaleInternationalNameEntity> {

    @Resource
    private LimitBatchProperties limitBatchProperties;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LocaleInternationalNameEntity> getQueryWrapper(LocaleInternationalNameDto dto) {
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 表名
        String tableName = dto.getTableName();
        queryWrapper.eq(StrUtil.isNotBlank(tableName), LocaleInternationalNameEntity::getTableName, tableName);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LocaleInternationalNameEntity> getSelectWrapper(LocaleInternationalNameDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<LocaleInternationalNameEntity> addSelectField(LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper) {
        return queryWrapper.select(LocaleInternationalNameEntity::getId, LocaleInternationalNameEntity::getTableName, LocaleInternationalNameEntity::getLineId,
                LocaleInternationalNameEntity::getFieldName, LocaleInternationalNameEntity::getFieldValue, LocaleInternationalNameEntity::getLocale,
                LocaleInternationalNameEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(LocaleInternationalNameDto dto) {
        // 行ID+地区语言不能重复
        long lineId = dto.getLineId();
        String locale = dto.getLocale();
        if (exist(lineId, locale)) {
            throw ValidationException.of(ErrorCode.INTERNATIONAL_NAME_EXISTS, String.valueOf(lineId), locale);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LocaleInternationalNameDto> page(LocaleInternationalNameDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LocaleInternationalNameDto.class);
    }

    @Override
    public List<LocaleInternationalNameDto> list(LocaleInternationalNameDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LocaleInternationalNameDto.class);
    }

    @Override
    public LocaleInternationalNameDto getById(Long id) {
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(LocaleInternationalNameEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), LocaleInternationalNameDto.class);
    }

    @Override
    public void add(LocaleInternationalNameDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, LocaleInternationalNameEntity.class));
    }

    @Override
    public void update(LocaleInternationalNameDto dto) {
        baseMapper.alwaysUpdateById(BeanUtil.convert(dto, LocaleInternationalNameEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public long count(LocaleInternationalNameDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public boolean exist(long lineId, String locale) {
        return baseMapper.exist(lineId, locale) != null ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<LocaleInternationalNameEntity> entityList) {
        ListUtil.split(ListUtil.toList(entityList), limitBatchProperties.getRealSaveNumEveryTime())
                .forEach(baseMapper::realSaveBatch);
        return true;
    }

}
