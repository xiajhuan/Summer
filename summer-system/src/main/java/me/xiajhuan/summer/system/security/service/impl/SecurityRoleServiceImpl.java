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
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.system.security.dto.SecurityRoleDto;
import me.xiajhuan.summer.system.security.entity.SecurityRoleDeptEntity;
import me.xiajhuan.summer.system.security.entity.SecurityRoleEntity;
import me.xiajhuan.summer.system.security.entity.SecurityRoleMenuEntity;
import me.xiajhuan.summer.system.security.entity.SecurityRoleUserEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleDeptMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleMenuMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleUserMapper;
import me.xiajhuan.summer.system.security.service.SecurityRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityRoleServiceImpl extends ServiceImpl<SecurityRoleMapper, SecurityRoleEntity> implements SecurityRoleService, MpHelper<SecurityRoleDto, SecurityRoleEntity> {

    @Resource
    private SecurityRoleUserMapper securityRoleUserMapper;

    @Resource
    private SecurityRoleMenuMapper securityRoleMenuMapper;

    @Resource
    private SecurityRoleDeptMapper securityRoleDeptMapper;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> getQueryWrapper(SecurityRoleDto dto) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 角色名称
        String name = dto.getName();
        queryWrapper.eq(StrUtil.isNotBlank(name), SecurityRoleEntity::getName, name);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> getSelectWrapper(SecurityRoleDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> addSelectField(LambdaQueryWrapper<SecurityRoleEntity> queryWrapper) {
        return queryWrapper.select(SecurityRoleEntity::getId, SecurityRoleEntity::getName, SecurityRoleEntity::getDescription,
                SecurityRoleEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(SecurityRoleDto dto) {
        // 角色名称不能重复
        String name = dto.getName();
        if (baseMapper.exist(name) != null) {
            throw ValidationException.of(ErrorCode.ROLE_EXISTS, name);
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<SecurityRoleDto> page(SecurityRoleDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), SecurityRoleDto.class);
    }

    @Override
    public List<SecurityRoleDto> list(SecurityRoleDto dto) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = getQueryWrapper(dto);
        queryWrapper.select(SecurityRoleEntity::getId, SecurityRoleEntity::getName);
        return BeanUtil.convert(list(queryWrapper), SecurityRoleDto.class);
    }

    @Override
    public SecurityRoleDto getById(Long id) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityRoleEntity::getId, id);
        SecurityRoleEntity entity = getOne(addSelectField(queryWrapper));

        if (entity != null) {
            SecurityRoleDto dto = BeanUtil.convert(entity, SecurityRoleDto.class);
            // 菜单ID集合
            dto.setMenuIdSet(securityRoleMenuMapper.getMenuIdSet(id));
            // 部门ID集合
            dto.setDeptIdSet(securityRoleDeptMapper.getDeptIdSet(id));

            return dto;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SecurityRoleDto dto) {
        handleDtoBefore(dto);

        SecurityRoleEntity entity = BeanUtil.convert(dto, SecurityRoleEntity.class);

        if (save(entity)) {
            long id = entity.getId();

            // 保存角色菜单关联
            saveOrUpdateRoleMenu(id, dto.getMenuIdSet(), false);

            // 保存角色部门关联
            saveOrUpdateRoleDept(id, dto.getDeptIdSet(), false);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SecurityRoleDto dto) {
        SecurityRoleEntity entity = BeanUtil.convert(dto, SecurityRoleEntity.class);

        if (updateById(entity)) {
            long id = entity.getId();

            // 修改角色菜单关联
            saveOrUpdateRoleMenu(id, dto.getMenuIdSet(), true);

            // 修改角色部门关联
            saveOrUpdateRoleDept(id, dto.getDeptIdSet(), true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        if (removeByIds(idSet)) {
            // 删除角色菜单关联
            LambdaQueryWrapper<SecurityRoleMenuEntity> roleMenuQueryWrapper = Wrappers.lambdaQuery();
            roleMenuQueryWrapper.in(SecurityRoleMenuEntity::getRoleId, idSet);
            securityRoleMenuMapper.delete(roleMenuQueryWrapper);

            // 删除角色部门关联
            LambdaQueryWrapper<SecurityRoleDeptEntity> roleDeptQueryWrapper = Wrappers.lambdaQuery();
            roleDeptQueryWrapper.in(SecurityRoleDeptEntity::getRoleId, idSet);
            securityRoleDeptMapper.delete(roleDeptQueryWrapper);

            // 删除角色用户关联
            LambdaQueryWrapper<SecurityRoleUserEntity> roleUserQueryWrapper = Wrappers.lambdaQuery();
            roleUserQueryWrapper.in(SecurityRoleUserEntity::getRoleId, idSet);
            securityRoleUserMapper.delete(roleUserQueryWrapper);
        }
    }

    /**
     * 保存/修改角色菜单关联
     *
     * @param id        ID
     * @param menuIdSet 菜单ID集合
     * @param isUpdate  是否为更新，true：是 false：否
     */
    private void saveOrUpdateRoleMenu(long id, Set<Long> menuIdSet, boolean isUpdate) {
        if (isUpdate) {
            // 更新则先删除原来的角色菜单关联
            LambdaQueryWrapper<SecurityRoleMenuEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SecurityRoleMenuEntity::getRoleId, id);
            securityRoleMenuMapper.delete(queryWrapper);
        }

        if (menuIdSet.size() > 0) {
            // 保存新角色菜单关联，note：这里数据量不会很大，直接循环插入就好
            menuIdSet.forEach(menuId -> {
                SecurityRoleMenuEntity entity = new SecurityRoleMenuEntity();
                entity.setRoleId(id);
                entity.setMenuId(menuId);
                securityRoleMenuMapper.insert(entity);
            });
        }
    }

    /**
     * 保存/修改角色部门关联
     *
     * @param id        ID
     * @param deptIdSet 部门ID集合
     * @param isUpdate  是否为更新，true：是 false：否
     */
    private void saveOrUpdateRoleDept(long id, Set<Long> deptIdSet, boolean isUpdate) {
        if (isUpdate) {
            // 更新则先删除原来的角色部门关联
            LambdaQueryWrapper<SecurityRoleDeptEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SecurityRoleDeptEntity::getRoleId, id);
            securityRoleDeptMapper.delete(queryWrapper);
        }

        if (deptIdSet.size() > 0) {
            // 保存新角色部门关联，note：这里数据量不会很大，直接循环插入就好
            String currentUsername = SecurityUtil.getCurrentUsername();
            Date now = DateUtil.date();
            deptIdSet.forEach(deptId -> {
                SecurityRoleDeptEntity entity = new SecurityRoleDeptEntity();
                entity.setRoleId(id);
                entity.setDeptId(deptId);
                // note：这里不能使用字段自动填充，否则deptId会被覆盖！
                entity.setCreateBy(currentUsername);
                entity.setCreateTime(now);
                securityRoleDeptMapper.insert(entity);
            });
        }
    }

}
