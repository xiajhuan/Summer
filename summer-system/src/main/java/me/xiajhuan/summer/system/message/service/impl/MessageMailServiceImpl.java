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

package me.xiajhuan.summer.system.message.service.impl;

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
import me.xiajhuan.summer.system.message.dto.MessageMailDto;
import me.xiajhuan.summer.system.message.entity.MessageMailEntity;
import me.xiajhuan.summer.system.message.mapper.MessageMailMapper;
import me.xiajhuan.summer.system.message.service.MessageMailService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 邮件 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/5/9
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class MessageMailServiceImpl extends ServiceImpl<MessageMailMapper, MessageMailEntity> implements MessageMailService, MpHelper<MessageMailDto, MessageMailEntity> {

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<MessageMailEntity> getQueryWrapper(MessageMailDto dto) {
        LambdaQueryWrapper<MessageMailEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 邮件名称
        String name = dto.getName();
        queryWrapper.eq(StrUtil.isNotBlank(name), MessageMailEntity::getName, name);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<MessageMailEntity> getSelectWrapper(MessageMailDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<MessageMailEntity> addSelectField(LambdaQueryWrapper<MessageMailEntity> queryWrapper) {
        return queryWrapper.select(MessageMailEntity::getId, MessageMailEntity::getName, MessageMailEntity::getSubject,
                MessageMailEntity::getContent, MessageMailEntity::getContentType, MessageMailEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(MessageMailDto dto) {
        // 邮件名称不能重复
        String name = dto.getName();
        if (baseMapper.exist(name) != null) {
            throw ValidationException.of(ErrorCode.MAIL_EXISTS, name);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<MessageMailDto> page(MessageMailDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), MessageMailDto.class);
    }

    @Override
    public MessageMailDto getById(Long id) {
        LambdaQueryWrapper<MessageMailEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(MessageMailEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), MessageMailDto.class);
    }

    @Override
    public void add(MessageMailDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, MessageMailEntity.class));
    }

    @Override
    public void update(MessageMailDto dto) {
        updateById(BeanUtil.convert(dto, MessageMailEntity.class));
    }

    @Override
    public void delete(Long[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

}
