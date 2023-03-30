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

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.admin.common.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.admin.common.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.admin.common.locale.mapper.LocaleInternationalNameMapper;
import me.xiajhuan.summer.admin.common.locale.service.LocaleInternationalNameService;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
@DS(DataSourceConst.COMMON)
public class LocaleInternationalNameServiceImpl extends ServiceImpl<LocaleInternationalNameMapper, LocaleInternationalNameEntity> implements LocaleInternationalNameService, MpHelper<LocaleInternationalNameDto, LocaleInternationalNameEntity> {

    @Resource(name = SettingBeanConst.CORE)
    private Setting setting;

    /**
     * 每次批量插入数（JDBC批量提交）
     */
    private int batchNumEveryTime;

    /**
     * 初始化 {@link batchNumEveryTime}
     */
    @PostConstruct
    private void init() {
        batchNumEveryTime = setting.getInt("batch-num-every-time", "Mp", 100);
    }

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
    public LambdaQueryWrapper<LocaleInternationalNameEntity> getQueryWrapper(LocaleInternationalNameDto dto) {
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = getSelectWrapper(currentModelClass());
        // 动态Sql查询条件
        // 表名
        String tableName = dto.getTableName();
        queryWrapper.eq(StrUtil.isNotBlank(tableName), LocaleInternationalNameEntity::getTableName, tableName);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LocaleInternationalNameDto> page(LocaleInternationalNameDto dto) {
        return ConvertUtil.convert(page(handlePageSort(dto), getQueryWrapper(dto)), LocaleInternationalNameDto.class);
    }

    @Override
    public List<LocaleInternationalNameDto> list(LocaleInternationalNameDto dto) {
        return ConvertUtil.convert(list(getSortWrapper(dto)), LocaleInternationalNameDto.class);
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
        baseMapper.alwaysUpdateById(ConvertUtil.convert(dto, LocaleInternationalNameEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public Integer exist(LocaleInternationalNameEntity entity) {
        return baseMapper.exist(entity);
    }

    @Override
    public boolean saveBatch(Collection<LocaleInternationalNameEntity> entityList) {
        ListUtil.split(ListUtil.toList(entityList), batchNumEveryTime)
                .forEach(list -> baseMapper.realSaveBatch(list));
        return true;
    }

}
