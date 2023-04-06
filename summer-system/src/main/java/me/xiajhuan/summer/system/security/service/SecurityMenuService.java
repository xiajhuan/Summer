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

package me.xiajhuan.summer.system.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.system.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.system.security.entity.SecurityMenuEntity;
import me.xiajhuan.summer.system.security.enums.ComponentTypeEnum;

import java.util.List;
import java.util.Set;

/**
 * 菜单 Service
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
public interface SecurityMenuService extends IService<SecurityMenuEntity> {

    /**
     * 树形结构列表
     *
     * @return 菜单列表（树形结构）
     */
    List<SecurityMenuDto> treeList();

    SecurityMenuDto getById(Long id);

    void add(SecurityMenuDto dto);

    void update(SecurityMenuDto dto);

    void delete(Long id);

    /**
     * 导航菜单列表
     *
     * @param type 类型 {@link ComponentTypeEnum}
     * @return 菜单列表（树形结构）
     */
    List<SecurityMenuDto> navList(Integer type);

    /**
     * 获取用户权限集合
     *
     * @param loginUser 登录用户信息
     * @return 用户权限集合 或 {@code null}
     */
    Set<String> getPermissions(LoginUser loginUser);

}
