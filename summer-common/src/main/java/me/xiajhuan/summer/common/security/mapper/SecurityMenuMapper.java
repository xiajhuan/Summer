package me.xiajhuan.summer.common.security.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.common.security.entity.SecurityMenuEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@InterceptorIgnore
public interface SecurityMenuMapper extends BaseMapper<SecurityMenuEntity> {

    /**
     * 获取所有权限列表
     *
     * @return 所有权限列表
     */
    @Select("SELECT permissions FROM security_menu")
    List<String> getPermissionsAll();

    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 用户权限列表
     */
    @Select("SELECT" +
            "\tt3.permissions \n" +
            "FROM\n" +
            "\tsecurity_role_user t1\n" +
            "\tLEFT JOIN security_role_menu t2 ON t1.role_id = t2.role_id\n" +
            "\tLEFT JOIN security_menu t3 ON t2.menu_id = t3.id \n" +
            "WHERE\n" +
            "\tt1.user_id = #{value} \n" +
            "ORDER BY\n" +
            "\tt3.weight ASC")
    List<String> getPermissionsOfUser(Long userId);

}
