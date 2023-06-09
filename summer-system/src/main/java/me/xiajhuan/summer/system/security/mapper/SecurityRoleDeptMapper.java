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
import me.xiajhuan.summer.system.security.entity.SecurityRoleDeptEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 角色部门关联 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityRoleDeptMapper extends BaseMapper<SecurityRoleDeptEntity> {

    /**
     * 获取部门ID集合
     *
     * @param roleId 角色ID
     * @return 部门ID集合
     */
    @Select("SELECT dept_id from security_role_dept WHERE role_id = #{roleId}")
    Set<Long> getDeptIdSet(@Param("roleId") long roleId);

    /**
     * 获取部门ID集合（用户所有角色关联的所有部门ID）
     *
     * @param userId 用户ID
     * @return 部门ID集合
     */
    Set<Long> getDeptIdRoleBasedSet(@Param("userId") long userId);

}
