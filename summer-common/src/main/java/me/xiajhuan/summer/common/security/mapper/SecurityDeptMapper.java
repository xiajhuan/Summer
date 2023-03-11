package me.xiajhuan.summer.common.security.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.common.security.entity.SecurityDeptEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 部门 Mapper
 *
 * @author xiajhuan
 * @date 2023/3/7
 */
@InterceptorIgnore
public interface SecurityDeptMapper extends BaseMapper<SecurityDeptEntity> {

    @Select("")
    List<SecurityDeptEntity> getList(@Param("param") Set<Long> param);

}
