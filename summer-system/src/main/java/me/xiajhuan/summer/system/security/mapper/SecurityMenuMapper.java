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
import me.xiajhuan.summer.system.security.entity.SecurityMenuEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;
import me.xiajhuan.summer.system.security.enums.ComponentTypeEnum;

import java.util.List;
import java.util.Set;

/**
 * 菜单 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityMenuMapper extends BaseMapper<SecurityMenuEntity> {

    /**
     * 根据ID和地区语言获取
     *
     * @param id     ID
     * @param locale 地区语言，参考{@link LocaleSupportEnum}
     * @return 菜单Entity
     */
    SecurityMenuEntity getByIdAndLocale(@Param("id") Long id, @Param("locale") String locale);

    /**
     * 获取全部菜单
     *
     * @param locale 地区语言，参考{@link LocaleSupportEnum}
     * @param type   类型，参考{@link ComponentTypeEnum}
     * @return 菜单Entity列表
     */
    List<SecurityMenuEntity> getMenusAll(@Param("locale") String locale, @Param("type") Integer type);

    /**
     * 获取菜单
     *
     * @param locale 地区语言，参考{@link LocaleSupportEnum}
     * @param type   类型，参考{@link ComponentTypeEnum}
     * @param userId 用户ID
     * @return 菜单Entity列表
     */
    List<SecurityMenuEntity> getMenus(@Param("locale") String locale, @Param("type") Integer type, @Param("userId") Long userId);

    /**
     * 获取全部菜单权限
     *
     * @return 菜单权限集合
     */
    @Select("SELECT permissions FROM security_menu")
    Set<String> getMenuPermissionsAll();

    /**
     * 获取菜单权限
     *
     * @param userId 用户ID
     * @return 菜单权限集合
     */
    Set<String> getMenuPermissions(@Param("userId") Long userId);

}
