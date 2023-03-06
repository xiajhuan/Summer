package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;

/**
 * 角色部门关联 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_role_dept")
public class RoleDeptEntity extends SimpleBaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;

}
