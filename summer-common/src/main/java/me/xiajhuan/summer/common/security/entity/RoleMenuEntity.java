package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;

/**
 * 角色菜单关联 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_role_menu")
public class RoleMenuEntity extends SimpleBaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
    
}
