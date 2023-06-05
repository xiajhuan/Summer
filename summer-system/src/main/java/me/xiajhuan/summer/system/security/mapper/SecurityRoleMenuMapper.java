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

package me.xiajhuan.summer.system.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.system.security.entity.SecurityRoleMenuEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 角色菜单关联 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/7
 */
public interface SecurityRoleMenuMapper extends BaseMapper<SecurityRoleMenuEntity> {

    /**
     * 获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Select("SELECT menu_id from security_role_menu WHERE role_id = #{roleId}")
    Set<Long> getMenuIdSet(@Param("roleId") long roleId);

}
