/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.security.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.admin.common.security.entity.SecurityMenuEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 菜单 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@InterceptorIgnore
public interface SecurityMenuMapper extends BaseMapper<SecurityMenuEntity> {

    /**
     * 获取所有权限集合
     *
     * @return 所有权限集合
     */
    @Select("SELECT permissions FROM security_menu")
    Set<String> getPermissionsAll();

    /**
     * 获取用户权限集合
     *
     * @param userId 用户ID
     * @return 用户权限集合
     */
    Set<String> getPermissionsOfUser(@Param("userId") Long userId);

}
