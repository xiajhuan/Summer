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

import cn.hutool.core.lang.Dict;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.enums.BucketTypeEnum;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.oss.server.OssServerFactory;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.extend.dto.ExtendOssDto;
import me.xiajhuan.summer.system.extend.entity.ExtendOssEntity;
import me.xiajhuan.summer.system.extend.mapper.ExtendOssMapper;
import me.xiajhuan.summer.system.extend.service.ExtendOssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 对象存储 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/30
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class ExtendOssServiceImpl extends ServiceImpl<ExtendOssMapper, ExtendOssEntity> implements ExtendOssService, MpHelper<ExtendOssDto, ExtendOssEntity> {

    @Value("${application.oss.type}")
    private String ossType;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<ExtendOssEntity> getQueryWrapper(ExtendOssDto dto) {
        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 支持类型
        queryWrapper.eq(ExtendOssEntity::getSupportType, ossType);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<ExtendOssEntity> getSelectWrapper(ExtendOssDto dto) {
        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(ExtendOssEntity::getId, ExtendOssEntity::getName, ExtendOssEntity::getUrl,
                ExtendOssEntity::getPath, ExtendOssEntity::getBucketType, ExtendOssEntity::getSupportType,
                ExtendOssEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<ExtendOssDto> page(ExtendOssDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), ExtendOssDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.in(ExtendOssEntity::getId, idSet);
        queryWrapper.select(ExtendOssEntity::getPath, ExtendOssEntity::getBucketType);
        List<ExtendOssEntity> entityList = list(queryWrapper);

        if (entityList.size() > 0 && removeByIds(idSet)) {
            // 删除文件
            AbstractOssServer ossServer = OssServerFactory.getOssServer();
            entityList.forEach(entity -> ossServer.delete(entity.getPath(),
                    entity.getBucketType() == BucketTypeEnum.PRIVATE.getValue()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(Dict[] dictArray) {
        Arrays.stream(dictArray).filter(Objects::nonNull)
                .forEach(dict -> save(ExtendOssEntity.builder()
                        .name(dict.getStr("name"))
                        .url(dict.getStr("url"))
                        .path(dict.getStr("path"))
                        .bucketType(dict.getInt("bucketType"))
                        .supportType(dict.getStr("supportType")).build()));
    }

}
