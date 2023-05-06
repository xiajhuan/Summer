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
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.oss.factory.OssServerFactory;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.properties.ApplicationProperties;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.extend.dto.ExtendOssDto;
import me.xiajhuan.summer.system.extend.entity.ExtendOssEntity;
import me.xiajhuan.summer.system.extend.mapper.ExtendOssMapper;
import me.xiajhuan.summer.system.extend.service.ExtendOssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
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

    @Resource
    private ApplicationProperties applicationProperties;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<ExtendOssEntity> getQueryWrapper(ExtendOssDto dto) {
        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 类型
        queryWrapper.eq(ExtendOssEntity::getType, applicationProperties.getOss().getType());

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<ExtendOssEntity> getSelectWrapper(ExtendOssDto dto) {
        LambdaQueryWrapper<ExtendOssEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(ExtendOssEntity::getId, ExtendOssEntity::getName, ExtendOssEntity::getUrl,
                ExtendOssEntity::getType, ExtendOssEntity::getCreateTime);

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
        // 路径（相对路径）集合
        Set<String> pathSet = baseMapper.getPathSet(idSet);

        if (pathSet.size() > 0 && removeByIds(idSet)) {
            // 删除文件
            AbstractOssServer ossServer = OssServerFactory.getOssServer();
            pathSet.forEach(path -> ossServer.delete(null, path));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(Dict[] dictArray) {
        Arrays.stream(dictArray).filter(Objects::nonNull)
                .forEach(dict -> save(ExtendOssEntity.builder()
                        .name(dict.getStr("name"))
                        .url(dict.getStr("url"))
                        .bucketName(dict.getStr("bucketName"))
                        .path(dict.getStr("path"))
                        .type(dict.getStr("type")).build()));
    }

}
