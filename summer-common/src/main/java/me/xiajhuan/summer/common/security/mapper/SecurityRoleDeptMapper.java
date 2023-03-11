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
