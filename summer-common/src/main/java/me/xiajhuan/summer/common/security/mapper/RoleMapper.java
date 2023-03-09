package me.xiajhuan.summer.common.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.common.security.entity.RoleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 角色 Mapper
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 根据角色名称判断角色是否存在
     *
     * @param name 角色名称
     * @return 返回null则不存在
     */
    @Select("SELECT 1 FROM c_role WHERE name = #{name} LIMIT 1")
    Integer isExistByName(@Param("name") String name);

}
