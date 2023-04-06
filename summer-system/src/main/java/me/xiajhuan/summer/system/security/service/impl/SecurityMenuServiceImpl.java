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
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.TreeConst;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.utils.*;
import me.xiajhuan.summer.system.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.system.security.entity.SecurityMenuEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityMenuMapper;
import me.xiajhuan.summer.system.security.service.SecurityMenuService;
import org.springframework.stereotype.Service;

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

    @Override
    public List<SecurityMenuDto> treeList() {
        // 构建菜单树形结构列表
        return TreeUtil.buildDto(SecurityMenuDto.class,
                BeanUtil.convert(baseMapper.getMenusAll(LocaleUtil.getAcceptLanguage(ServletUtil.getHttpServletRequest()), null), SecurityMenuDto.class),
                TreeConst.ROOT, TreeConst.Extra.MENU);
    }

    @Override
    public SecurityMenuDto getById(Long id) {
        return BeanUtil.convert(baseMapper.getById(id, LocaleUtil.getAcceptLanguage(ServletUtil.getHttpServletRequest())),
                SecurityMenuDto.class);
    }


    @Override
    public void add(SecurityMenuDto dto) {
        // TODO
    }

    @Override
    public void update(SecurityMenuDto dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<SecurityMenuDto> navList(Integer type) {
        final List<SecurityMenuEntity> entityList;
        LoginUser loginUser = SecurityUtil.getLoginUser();
        String locale = LocaleUtil.getAcceptLanguage(ServletUtil.getHttpServletRequest());

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
