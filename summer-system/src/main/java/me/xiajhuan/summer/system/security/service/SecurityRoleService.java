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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.security.dto.SecurityRoleDto;
import me.xiajhuan.summer.system.security.entity.SecurityRoleEntity;

/**
 * 角色 Service
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public interface SecurityRoleService extends IService<SecurityRoleEntity> {

    Page<SecurityRoleDto> page(SecurityRoleDto dto);

    void add(SecurityRoleDto dto);

    void update(SecurityRoleDto dto);

    void delete(Long[] ids);

}
