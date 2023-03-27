/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.locale.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.admin.common.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.admin.common.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.admin.common.locale.mapper.LocaleInternationalNameMapper;
import me.xiajhuan.summer.admin.common.locale.service.LocaleInternationalNameService;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.data.PageAndSort;
import me.xiajhuan.summer.core.mp.custom.MpHelper;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 国际化名称 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Service
@DS(DataSourceConst.COMMON)
public class LocaleInternationalNameServiceImpl extends ServiceImpl<LocaleInternationalNameMapper, LocaleInternationalNameEntity> implements LocaleInternationalNameService, MpHelper<LocaleInternationalNameDto, LocaleInternationalNameEntity> {

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LocaleInternationalNameEntity> getSelectWrapper(Class<LocaleInternationalNameEntity> entityClass) {
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(LocaleInternationalNameEntity::getId, LocaleInternationalNameEntity::getTableName, LocaleInternationalNameEntity::getFieldName,
                LocaleInternationalNameEntity::getFieldValue, LocaleInternationalNameEntity::getLocale, LocaleInternationalNameEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LocaleInternationalNameEntity> getQueryWrapper(LocaleInternationalNameDto dto, boolean isCount) {
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = getQueryWrapperUnconditional(isCount);
        // 动态Sql查询条件
        // 表名
        String tableName = dto.getTableName();
        queryWrapper.eq(StrUtil.isNotBlank(tableName), LocaleInternationalNameEntity::getTableName, tableName);

        return queryWrapper;
    }

    @Override
    public IPage<LocaleInternationalNameEntity> customPage(Page<LocaleInternationalNameEntity> page, LocaleInternationalNameDto dto) {
        // 关闭MP分页内置的count查询
        page.setSearchCount(false);

        IPage<LocaleInternationalNameEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public IPage<LocaleInternationalNameDto> page(PageAndSort pageAndSort, LocaleInternationalNameDto dto) {
        return ConvertUtil.convert(customPage(handlePageAndSort(pageAndSort), dto), LocaleInternationalNameDto.class);
    }

    @Override
    public List<LocaleInternationalNameDto> list(LocaleInternationalNameDto dto) {
        return ConvertUtil.convert(list(getQueryWrapper(dto, false)), LocaleInternationalNameDto.class);
    }

    @Override
    public LocaleInternationalNameDto getById(Long id) {
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = getSelectWrapper(LocaleInternationalNameEntity.class);
        queryWrapper.eq(LocaleInternationalNameEntity::getId, id);

        return ConvertUtil.convert(getOne(queryWrapper), LocaleInternationalNameDto.class);
    }

    @Override
    public void add(LocaleInternationalNameDto dto) {
        save(ConvertUtil.convert(dto, LocaleInternationalNameEntity.class));
    }

    @Override
    public void update(LocaleInternationalNameDto dto) {
        updateById(ConvertUtil.convert(dto, LocaleInternationalNameEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public Integer exist(LocaleInternationalNameEntity entity) {
        return baseMapper.exist(entity);
    }

}
