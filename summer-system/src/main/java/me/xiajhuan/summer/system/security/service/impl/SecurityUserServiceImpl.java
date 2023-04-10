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
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleUserMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityUserMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityUserPostMapper;
import me.xiajhuan.summer.system.security.service.SecurityUserService;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.dept;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private SecurityRoleUserMapper securityRoleUserMapper;

    @Resource
    private SecurityUserPostMapper securityUserPostMapper;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getQueryWrapper(SecurityUserDto dto) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 排除“超级管理员”
        queryWrapper.eq(SecurityUserEntity::getUserType, 1);
        // 用户名
        String username = dto.getUsername();
        queryWrapper.eq(StrUtil.isNotBlank(username), SecurityUserEntity::getUsername, username);
        // 本部门ID
        Long deptId = dto.getDeptId();
        queryWrapper.eq(deptId != null, SecurityUserEntity::getDeptId, deptId);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, SecurityUserEntity::getStatus, status);

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
                SecurityUserEntity::getDataScope, SecurityUserEntity::getCreateTime);
    }

    @Override
    public void handleEntityAfter(SecurityUserEntity entity) {
        // 本部门名称
        entity.setDeptName(String.valueOf(CacheServerFactory.getCacheServer()
                .getHash(dept(entity.getDeptId()), "name")));
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<SecurityUserDto> page(SecurityUserDto dto) {
        Page<SecurityUserEntity> page = page(handlePageSort(dto), getSelectWrapper(dto));
        List<SecurityUserEntity> entityList = page.getRecords();
        if (entityList.size() > 0) {
            entityList.forEach(this::handleEntityAfter);
        }

        return BeanUtil.convert(page, SecurityUserDto.class);
    }

    @Override
    public List<SecurityUserDto> list(SecurityUserDto dto) {
        List<SecurityUserEntity> entityList = list(getSortWrapper(dto));
        if (entityList.size() > 0) {
            entityList.forEach(this::handleEntityAfter);
        }

        return BeanUtil.convert(entityList, SecurityUserDto.class);
    }

    @Override
    public SecurityUserDto getById(Long id) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getId, id);
        SecurityUserEntity entity = getOne(addSelectField(queryWrapper));
        if (entity != null) {
            handleEntityAfter(entity);

            SecurityUserDto dto = BeanUtil.convert(entity, SecurityUserDto.class);
            // 角色ID集合
            dto.setRoleIdSet(securityRoleUserMapper.getRoleIdSet(id));

            // 岗位ID集合
            dto.setPostIdSet(securityUserPostMapper.getPostIdSet(id));

            return dto;
        }
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
    public SecurityUserEntity getByUsername(String username) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getUsername, username);
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getPassword,
                SecurityUserEntity::getRealName, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getDataScope);

        return getOne(queryWrapper);
    }

}
