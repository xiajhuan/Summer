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
import cn.hutool.core.date.DateUtil;
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
import me.xiajhuan.summer.core.exception.custom.BusinessException;
import me.xiajhuan.summer.core.utils.*;
import me.xiajhuan.summer.system.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.system.locale.service.LocaleInternationalNameService;
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
import java.util.stream.Collectors;

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
    private LocaleInternationalNameService localeInternationalNameService;

    @Resource
    private SecurityRoleMenuMapper securityRoleMenuMapper;

    @Override
    public List<SecurityMenuDto> treeList() {
        // 构建菜单树形结构列表
        return TreeUtil.buildDto(SecurityMenuDto.class,
                BeanUtil.convert(baseMapper.getMenusAll(LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest()), null), SecurityMenuDto.class),
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
        // 保存菜单
        save(entity);

        // 保存国际化名称
        LocaleInternationalNameEntity internationalNameEntity = LocaleInternationalNameEntity.builder()
                .tableName("security_menu").lineId(entity.getId())
                .fieldName("name").fieldValue(entity.getName())
                .locale(LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest())).build();
        localeInternationalNameService.save(internationalNameEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SecurityMenuDto dto) {
        // 上级菜单不能为自身
        if (dto.getId().longValue() == dto.getParentId().longValue()) {
            throw BusinessException.of(ErrorCode.SUPERIOR_MENU_ERROR);
        }

        SecurityMenuEntity entity = BeanUtil.convert(dto, SecurityMenuEntity.class);
        // 更新菜单
        updateById(entity);

        // 更新国际化名称
        // note：通过 update(LambdaUpdateWrapper) 更新时基础字段自动填充不会生效
        LambdaUpdateWrapper<LocaleInternationalNameEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(LocaleInternationalNameEntity::getLineId, entity.getId());
        updateWrapper.eq(LocaleInternationalNameEntity::getLocale, LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest()));
        updateWrapper.set(LocaleInternationalNameEntity::getFieldValue, entity.getName());
        updateWrapper.set(LocaleInternationalNameEntity::getUpdateBy, SecurityUtil.getCurrentUsername());
        updateWrapper.set(LocaleInternationalNameEntity::getUpdateTime, DateUtil.date());
        localeInternationalNameService.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除菜单
        removeById(id);

        // 删除国际化名称
        LambdaQueryWrapper<LocaleInternationalNameEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(LocaleInternationalNameEntity::getLineId, id);
        queryWrapper.eq(LocaleInternationalNameEntity::getLocale, LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest()));
        localeInternationalNameService.remove(queryWrapper);

        // 删除角色菜单关系
        LambdaQueryWrapper<SecurityRoleMenuEntity> roleMenuQueryWrapper = Wrappers.lambdaQuery();
        roleMenuQueryWrapper.eq(SecurityRoleMenuEntity::getMenuId, id);
        securityRoleMenuMapper.delete(roleMenuQueryWrapper);
    }

    @Override
    public List<SecurityMenuDto> navList(Integer type) {
        final List<SecurityMenuEntity> entityList;
        LoginUser loginUser = SecurityUtil.getLoginUser();
        String locale = LocaleUtil.getAcceptLanguage(ServletUtil.getHttpRequest());

        // 非超级管理员，一律只能查询自己的菜单列表
        if (UserTypeEnum.GENERAL.getValue() == loginUser.getUserType()) {
            entityList = baseMapper.getMenus(locale, type, loginUser.getId());
        } else {
            entityList = baseMapper.getMenusAll(locale, type);
        }

        // 构建菜单树形结构列表
        return TreeUtil.buildDto(SecurityMenuDto.class, BeanUtil.convert(entityList, SecurityMenuDto.class),
                TreeConst.ROOT, TreeConst.Extra.MENU);
    }

    @Override
    public Set<String> getPermissions(LoginUser loginUser) {
        // 用户权限集合
        final Set<String> permissions;
        if (loginUser.getUserType() == UserTypeEnum.SUPER_ADMIN.getValue()) {
            // 超级管理员
            permissions = baseMapper.getPermissionsAll();
        } else {
            // 普通用户
            permissions = baseMapper.getPermissions(loginUser.getId());
        }

        if (CollUtil.isNotEmpty(permissions)) {
            return permissions.stream().filter(p -> !StrUtil.isBlankOrUndefined(p))
                    .collect(Collectors.toSet());
        } else {
            return null;
        }
    }

}
