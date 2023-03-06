package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;

/**
 * 角色用户关联 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_role_user")
public class RoleUserEntity extends SimpleBaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 用户ID
     */
    private Long userId;

}
