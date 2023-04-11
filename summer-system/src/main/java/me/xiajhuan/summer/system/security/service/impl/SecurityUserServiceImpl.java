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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.entity.SecurityRoleUserEntity;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.entity.SecurityUserPostEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleUserMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityUserMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityUserPostMapper;
import me.xiajhuan.summer.system.security.service.SecurityService;
import me.xiajhuan.summer.system.security.service.SecurityUserService;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.dept;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityUserServiceImpl extends ServiceImpl<SecurityUserMapper, SecurityUserEntity> implements SecurityUserService, MpHelper<SecurityUserDto, SecurityUserEntity> {

    @Lazy
    @Resource
    private SecurityService securityService;

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
    public void handleDtoBefore(SecurityUserDto dto) {
        String password = dto.getPassword();
        if (StrUtil.isNotBlank(password)) {
            if (!password.equals(dto.getConfirmPassword())) {
                // 密码和确认密码不一致
                throw ValidationException.of(ErrorCode.PASSWORD_CONFIRM_ERROR);
            }

            // 加密密码
            dto.setPassword(SecurityUtil.encode(password));
        } else {
            // 修改时密码可能为空，设为“null”让MybatisPlus更新时忽略密码字段
            dto.setPassword(null);
        }

        // 新增时用户名不能重复
        if (dto.getId() == null) {
            String username = dto.getUsername();
            if (baseMapper.exist(username) != null) {
                throw ValidationException.of(ErrorCode.USER_EXISTS, username);
            }
        }
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
    @Transactional(rollbackFor = Exception.class)
    public void add(SecurityUserDto dto) {
        handleDtoBefore(dto);

        SecurityUserEntity entity = BeanUtil.convert(dto, SecurityUserEntity.class);
        // 保存用户
        String currentUsername = SecurityUtil.getCurrentUsername();
        Date now = DateUtil.date();
        // note：这里不能使用字段自动填充，否则“deptId”会被覆盖！
        entity.setCreateBy(currentUsername);
        entity.setCreateTime(now);
        entity.setUpdateBy(currentUsername);
        entity.setUpdateTime(now);
        save(entity);
        Long id = entity.getId();

        // 保存角色用户关联
        saveOrUpdateRoleUser(id, dto.getRoleIdSet());

        // 保存用户岗位关联
        saveOrUpdateUserPost(id, dto.getPostIdSet());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SecurityUserDto dto) {
        handleDtoBefore(dto);

        Long id = dto.getId();
        // 更新前的用户名
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getId, id);
        queryWrapper.select(SecurityUserEntity::getUsername);
        String oldUsername = getOne(queryWrapper).getUsername();

        SecurityUserEntity entity = BeanUtil.convert(dto, SecurityUserEntity.class);
        // 更新用户，note：这里不能使用字段自动填充，否则“deptId”会被覆盖！
        entity.setUpdateBy(SecurityUtil.getCurrentUsername());
        entity.setUpdateTime(DateUtil.date());
        updateById(entity);

        // 更新角色用户关联
        saveOrUpdateRoleUser(id, dto.getRoleIdSet());

        // 更新用户岗位关联
        saveOrUpdateUserPost(id, dto.getPostIdSet());

        if (!oldUsername.equals(entity.getUsername())) {
            // 如果修改了用户名，则让还在线的该用户自动退出
            securityService.logout(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        // 删除用户
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());
        removeByIds(idSet);

        // 删除角色用户关联
        LambdaQueryWrapper<SecurityRoleUserEntity> roleUserQueryWrapper = Wrappers.lambdaQuery();
        roleUserQueryWrapper.in(SecurityRoleUserEntity::getUserId, idSet);
        securityRoleUserMapper.delete(roleUserQueryWrapper);

        // 删除用户岗位关联
        LambdaQueryWrapper<SecurityUserPostEntity> userPostQueryWrapper = Wrappers.lambdaQuery();
        userPostQueryWrapper.in(SecurityUserPostEntity::getUserId, idSet);
        securityUserPostMapper.delete(userPostQueryWrapper);
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

    /**
     * 保存或修改角色用户关联
     *
     * @param id        ID
     * @param roleIdSet 角色ID集合
     */
    private void saveOrUpdateRoleUser(Long id, Set<Long> roleIdSet) {
        // 删除原来的角色用户关联
        LambdaQueryWrapper<SecurityRoleUserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityRoleUserEntity::getUserId, id);
        securityRoleUserMapper.delete(queryWrapper);

        if (roleIdSet.size() > 0) {
            // 保存新的角色用户关联，note：这里数据量不会很大，直接循环插入就好
            roleIdSet.forEach(roleId -> {
                SecurityRoleUserEntity entity = new SecurityRoleUserEntity();
                entity.setRoleId(roleId);
                entity.setUserId(id);
                securityRoleUserMapper.insert(entity);
            });
        }
    }

    /**
     * 保存或修改用户岗位关联
     *
     * @param id        ID
     * @param postIdSet 岗位ID集合
     */
    private void saveOrUpdateUserPost(Long id, Set<Long> postIdSet) {
        // 删除原来的用户岗位关联
        LambdaQueryWrapper<SecurityUserPostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityUserPostEntity::getUserId, id);
        securityUserPostMapper.delete(queryWrapper);

        if (postIdSet.size() > 0) {
            // 保存新的用户岗位关联，note：这里数据量不会很大，直接循环插入就好
            postIdSet.forEach(postId -> {
                SecurityUserPostEntity entity = new SecurityUserPostEntity();
                entity.setPostId(postId);
                entity.setUserId(id);
                securityUserPostMapper.insert(entity);
            });
        }
    }

}
