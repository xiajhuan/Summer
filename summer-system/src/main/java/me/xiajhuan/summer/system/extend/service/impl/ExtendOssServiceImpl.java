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

package me.xiajhuan.summer.system.extend.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.properties.ApplicationProperties;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.extend.dto.ExtendOssDto;
import me.xiajhuan.summer.system.extend.entity.ExtendOssEntity;
import me.xiajhuan.summer.system.extend.mapper.ExtendOssMapper;
import me.xiajhuan.summer.system.extend.service.ExtendOssService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 对象存储 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/30
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class ExtendOssServiceImpl extends ServiceImpl<ExtendOssMapper, ExtendOssEntity> implements ExtendOssService, MpHelper<ExtendOssDto, ExtendOssEntity> {

    @Resource
    private ApplicationProperties applicationProperties;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<ExtendOssEntity> getQueryWrapper(ExtendOssDto dto) {
        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 类型
        Integer type = dto.getType();
        queryWrapper.eq(type != null, ExtendOssEntity::getType, type);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<ExtendOssEntity> getSelectWrapper(ExtendOssDto dto) {
        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(ExtendOssEntity::getId, ExtendOssEntity::getUrl, ExtendOssEntity::getType,
                ExtendOssEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<ExtendOssDto> page(ExtendOssDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), ExtendOssDto.class);
    }

    @Override
    public void delete(Long[] ids) {
        // TODO
    }

    @Override
    public void addBatch(String[] urls) {
        int type = getType();
        Arrays.stream(urls).forEach(url -> save(
                ExtendOssEntity.builder()
                        .url(url).type(type)
                        .build()));
    }

    /**
     * 获取对象存储类型
     *
     * @return 对象存储类型
     */
    private int getType() {
        switch (applicationProperties.getOss().getType()) {
            case "LOCAL":
                return OssSupportEnum.LOCAL.getValue();
            case "MIN_IO":
                return OssSupportEnum.MIN_IO.getValue();
            default:
                return OssSupportEnum.QI_NIU.getValue();
        }
    }

}
