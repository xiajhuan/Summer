package me.xiajhuan.summer.common.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.common.security.entity.RoleDeptEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色部门关联 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface RoleDeptMapper extends BaseMapper<RoleDeptEntity> {

    @Select("SELECT\n" +
            "\tt2.dept_id \n" +
            "FROM\n" +
            "\tc_role_user t1,\n" +
            "\tc_role_dept t2 \n" +
            "WHERE\n" +
            "\tt1.user_id = #{value} \n" +
            "\tAND t1.role_id = t2.role_id")
    List<Long> getDeptListByUserId(Long userId);

}
