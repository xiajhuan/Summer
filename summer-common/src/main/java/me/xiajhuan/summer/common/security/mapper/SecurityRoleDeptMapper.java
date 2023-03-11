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

package me.xiajhuan.summer.common.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.common.security.entity.SecurityRoleDeptEntity;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 角色部门关联 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityRoleDeptMapper extends BaseMapper<SecurityRoleDeptEntity> {

    @Select("SELECT\n" +
            "\tt2.dept_id \n" +
            "FROM\n" +
            "\tsecurity_role_user t1,\n" +
            "\tsecurity_role_dept t2 \n" +
            "WHERE\n" +
            "\tt1.user_id = #{value} \n" +
            "\tAND t1.role_id = t2.role_id")
    Set<Long> getDeptSetByUserId(Long userId);

}
