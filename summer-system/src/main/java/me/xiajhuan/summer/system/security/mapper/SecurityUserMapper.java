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
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityUserMapper extends BaseMapper<SecurityUserEntity> {

    /**
     * 判断是否存在
     *
     * @param username 用户名
     * @param id       ID
     * @return 返回{@code null}则不存在
     */
    @Select("<script>" +
            "  SELECT 1 FROM security_user " +
            "  <where>" +
            "    username = #{username} " +
            "    <if test=\"id != null\">" +
            "      AND id <![CDATA[ <> ]]> #{id} " +
            "    </if>" +
            "  </where>" +
            "  LIMIT 1" +
            "</script>")
    Integer exist(@Param("username") String username, @Param("id") long id);

    /**
     * 获取用户名
     *
     * @param id ID
     * @return 用户名
     */
    @Select("SELECT username FROM security_user WHERE id = #{id} LIMIT 1")
    String getUsername(@Param("id") long id);

}
