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
import me.xiajhuan.summer.system.security.entity.SecurityUserPostEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 用户岗位关联 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
public interface SecurityUserPostMapper extends BaseMapper<SecurityUserPostEntity> {

    /**
     * 获取岗位ID集合
     *
     * @param userId 用户ID
     * @return 岗位ID集合
     */
    @Select("SELECT post_id from security_user_post WHERE user_id = #{userId}")
    Set<Long> getPostIdSet(@Param("userId") long userId);

}
