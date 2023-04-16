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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.TreeConst;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.utils.*;
import me.xiajhuan.summer.system.locale.entity.LocaleNameEntity;
import me.xiajhuan.summer.system.locale.service.LocaleNameService;
import me.xiajhuan.summer.system.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.system.security.entity.SecurityMenuEntity;
import me.xiajhuan.summer.system.security.entity.SecurityRoleMenuEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityMenuMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleMenuMapper;
import me.xiajhuan.summer.system.security.service.SecurityMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 菜单 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityMenuServiceImpl extends ServiceImpl<SecurityMenuMapper, SecurityMenuEntity> implements SecurityMenuService {

    @Resource
    private LocaleNameService localeNameService;

    @Resource
    private SecurityRoleMenuMapper securityRoleMenuMapper;

    @Override
    public List<SecurityMenuDto> treeList(Integer type, boolean needAll) {
        final List<SecurityMenuEntity> entityList;
        LoginUser loginUser = SecurityUtil.getLoginUser();
        String locale = LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest());

        // 非超级管理员，一律只能查询自己的菜单列表
        if (!needAll && UserTypeEnum.GENERAL.getValue() == loginUser.getUserType()) {
            entityList = baseMapper.getMenus(locale, type, loginUser.getId());
        } else {
            entityList = baseMapper.getMenusAll(locale, type);
        }

        // 构建菜单树形结构列表
        return TreeUtil.buildDto(SecurityMenuDto.class, BeanUtil.convert(entityList, SecurityMenuDto.class),
                TreeConst.ROOT, TreeConst.Extra.MENU);
    }

    @Override
    public SecurityMenuDto getById(Long id) {
        return BeanUtil.convert(baseMapper.getById(id, LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest())),
                SecurityMenuDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SecurityMenuDto dto) {
        SecurityMenuEntity entity = BeanUtil.convert(dto, SecurityMenuEntity.class);

        if (save(entity)) {
            // 保存国际化名称
            LocaleNameEntity nameEntity = LocaleNameEntity.builder()
                    .tableName("security_menu").lineId(entity.getId())
                    .fieldName("name").fieldValue(entity.getName())
                    .locale(LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest())).build();
            localeNameService.save(nameEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SecurityMenuDto dto) {
        // 上级菜单不能为自身
        if (dto.getId().longValue() == dto.getParentId().longValue()) {
            throw ValidationException.of(ErrorCode.SUPERIOR_MENU_ERROR);
        }

        SecurityMenuEntity entity = BeanUtil.convert(dto, SecurityMenuEntity.class);

        if (updateById(entity)) {
            // 更新国际化名称
            // note：通过 update(LambdaUpdateWrapper) 更新时基础字段自动填充不会生效
            LambdaUpdateWrapper<LocaleNameEntity> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(LocaleNameEntity::getLineId, entity.getId());
            updateWrapper.eq(LocaleNameEntity::getLocale, LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest()));
            updateWrapper.set(LocaleNameEntity::getFieldValue, entity.getName());
            updateWrapper.set(LocaleNameEntity::getUpdateBy, SecurityUtil.getCurrentUsername());
            updateWrapper.set(LocaleNameEntity::getUpdateTime, DateUtil.date());
            localeNameService.update(updateWrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 判断是否存在子菜单或按钮
        LambdaQueryWrapper<SecurityMenuEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityMenuEntity::getParentId, id);
        if (count(queryWrapper) > 0) {
            throw ValidationException.of(ErrorCode.MENU_SUB_DELETE_ERROR);
        }

        if (removeById(id)) {
            // 删除国际化名称
            LambdaQueryWrapper<LocaleNameEntity> nameQueryWrapper = Wrappers.lambdaQuery();
            nameQueryWrapper.eq(LocaleNameEntity::getLineId, id);
            nameQueryWrapper.eq(LocaleNameEntity::getLocale, LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest()));
            localeNameService.remove(nameQueryWrapper);

            // 删除角色菜单关联
            LambdaQueryWrapper<SecurityRoleMenuEntity> roleMenuQueryWrapper = Wrappers.lambdaQuery();
            roleMenuQueryWrapper.eq(SecurityRoleMenuEntity::getMenuId, id);
            securityRoleMenuMapper.delete(roleMenuQueryWrapper);
        }
    }

    @Override
    public Set<String> getPermissions(LoginUser loginUser) {
        // 用户菜单权限集合，note：1个菜单/按钮可能包含多个权限，以“,”分隔
        final Set<String> menuPermissions;
        if (loginUser.getUserType() == UserTypeEnum.SUPER_ADMIN.getValue()) {
            // 超级管理员
            menuPermissions = baseMapper.getMenuPermissionsAll();
        } else {
            // 普通用户
            menuPermissions = baseMapper.getMenuPermissions(loginUser.getId());
        }

        if (menuPermissions.size() > 0) {
            Set<String> permissions = CollUtil.newHashSet();
            menuPermissions.forEach(p -> {
                if (!StrUtil.isBlankOrUndefined(p)) {
                    // 如果包含多个权限，根据“,”拆分出来
                    permissions.addAll(ListUtil.of(p.split(StrPool.COMMA)));
                }
            });
            return permissions;
        } else {
            return null;
        }
    }

}
