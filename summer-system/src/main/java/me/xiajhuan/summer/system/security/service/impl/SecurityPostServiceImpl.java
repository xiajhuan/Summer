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

package me.xiajhuan.summer.system.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.security.dto.SecurityPostDto;
import me.xiajhuan.summer.system.security.entity.SecurityPostEntity;
import me.xiajhuan.summer.system.security.entity.SecurityUserPostEntity;
import me.xiajhuan.summer.system.security.mapper.*;
import me.xiajhuan.summer.system.security.service.SecurityPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 岗位 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityPostServiceImpl extends ServiceImpl<SecurityPostMapper, SecurityPostEntity> implements SecurityPostService, MpHelper<SecurityPostDto, SecurityPostEntity> {

    @Resource
    private SecurityUserPostMapper securityUserPostMapper;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityPostEntity> getQueryWrapper(SecurityPostDto dto) {
        LambdaQueryWrapper<SecurityPostEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 岗位编码或名称
        String codeOrName = dto.getCodeOrName();
        if (StrUtil.isNotBlank(codeOrName)) {
            // Sql片段示例：AND (code = xxx OR name = xxx)
            queryWrapper.and(i -> i.eq(SecurityPostEntity::getCode, codeOrName)
                    .or().eq(SecurityPostEntity::getName, codeOrName));
        }
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, SecurityPostEntity::getStatus, status);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityPostEntity> getSelectWrapper(SecurityPostDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<SecurityPostEntity> addSelectField(LambdaQueryWrapper<SecurityPostEntity> queryWrapper) {
        return queryWrapper.select(SecurityPostEntity::getId, SecurityPostEntity::getCode, SecurityPostEntity::getName,
                SecurityPostEntity::getStatus, SecurityPostEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(SecurityPostDto dto) {
        // 岗位编码不能重复
        String code = dto.getCode();
        if (baseMapper.exist(code) != null) {
            throw ValidationException.of(ErrorCode.POST_EXISTS, code);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<SecurityPostDto> page(SecurityPostDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), SecurityPostDto.class);
    }

    @Override
    public List<SecurityPostDto> list(int status) {
        LambdaQueryWrapper<SecurityPostEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityPostEntity::getStatus, status);
        queryWrapper.select(SecurityPostEntity::getId, SecurityPostEntity::getName);
        return BeanUtil.convert(list(queryWrapper), SecurityPostDto.class);
    }

    @Override
    public SecurityPostDto getById(Long id) {
        LambdaQueryWrapper<SecurityPostEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityPostEntity::getId, id);

        return BeanUtil.convert(getOne(addSelectField(queryWrapper)), SecurityPostDto.class);
    }

    @Override
    public void add(SecurityPostDto dto) {
        handleDtoBefore(dto);

        save(BeanUtil.convert(dto, SecurityPostEntity.class));
    }

    @Override
    public void update(SecurityPostDto dto) {
        updateById(BeanUtil.convert(dto, SecurityPostEntity.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        // 删除岗位
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());
        removeByIds(idSet);

        // 删除用户岗位关联
        LambdaQueryWrapper<SecurityUserPostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SecurityUserPostEntity::getPostId, idSet);
        securityUserPostMapper.delete(queryWrapper);
    }

}
