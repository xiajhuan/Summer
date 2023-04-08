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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityUserMapper;
import me.xiajhuan.summer.system.security.service.SecurityUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityUserServiceImpl extends ServiceImpl<SecurityUserMapper, SecurityUserEntity> implements SecurityUserService, MpHelper<SecurityUserDto, SecurityUserEntity> {

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getQueryWrapper(SecurityUserDto dto) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 用户名
        String username = dto.getUsername();
        queryWrapper.eq(StrUtil.isNotBlank(username), SecurityUserEntity::getUsername, username);
        // 性别
        Integer gender = dto.getGender();
        queryWrapper.eq(gender != null, SecurityUserEntity::getGender, gender);
        // 本部门ID
        Long deptId = dto.getDeptId();
        queryWrapper.eq(deptId != null, SecurityUserEntity::getDeptId, deptId);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getSelectWrapper(SecurityUserDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> addSelectField(LambdaQueryWrapper<SecurityUserEntity> queryWrapper) {
        return queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getRealName,
                SecurityUserEntity::getHeadUrl, SecurityUserEntity::getGender, SecurityUserEntity::getEmail,
                SecurityUserEntity::getMobile, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getDataScope, SecurityUserEntity::getCreateTime);
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<SecurityUserDto> page(SecurityUserDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), SecurityUserDto.class);
    }

    @Override
    public List<SecurityUserDto> list(SecurityUserDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), SecurityUserDto.class);
    }

    @Override
    public SecurityUserDto getById(Long id) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getId, id);
        queryWrapper = addSelectField(queryWrapper);

        return null;
    }

    @Override
    public void add(SecurityUserDto dto) {

    }

    @Override
    public void update(SecurityUserDto dto) {

    }

    @Override
    public void delete(Long[] ids) {

    }

    @Override
    public long count(SecurityUserDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public SecurityUserDto getByUsername(String username) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getUsername, username);
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getPassword,
                SecurityUserEntity::getRealName, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getDataScope);

        return BeanUtil.convert(getOne(queryWrapper), SecurityUserDto.class);
    }

}
