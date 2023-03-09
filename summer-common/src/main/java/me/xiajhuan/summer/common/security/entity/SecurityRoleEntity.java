package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.CommonBaseEntity;

/**
 * 角色 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_role")
public class SecurityRoleEntity extends CommonBaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

}
