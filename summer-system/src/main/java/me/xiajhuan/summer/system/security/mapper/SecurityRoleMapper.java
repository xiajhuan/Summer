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
import me.xiajhuan.summer.system.security.entity.SecurityRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 角色 Mapper
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public interface SecurityRoleMapper extends BaseMapper<SecurityRoleEntity> {

    /**
     * 判断是否存在
     *
     * @param name 角色名称
     * @return 返回{@code null}则不存在
     */
    @Select("SELECT 1 FROM security_role WHERE name = #{name} LIMIT 1")
    Integer exist(@Param("name") String name);

    /**
     * 获取角色名称集合
     *
     * @param idSet ID集合
     * @return 角色名称集合
     */
    @Select("<script>" +
            "  SELECT name FROM security_role WHERE id IN " +
            "  <foreach item=\"id\" collection=\"idSet\" open=\"(\" separator=\",\" close=\")\">" +
            "    #{id} " +
            "  </foreach>" +
            "</script>")
    Set<String> getNameSet(@Param("idSet") Set<Long> idSet);

}
