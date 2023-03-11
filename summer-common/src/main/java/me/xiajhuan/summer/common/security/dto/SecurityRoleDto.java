package me.xiajhuan.summer.common.security.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.dto.BaseDto;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

/**
 * 角色 Dto
 *
 * @author xiajhuan
 * @date 2023/3/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityRoleDto extends BaseDto {

    /**
     * 角色名称
     */
    @NotBlank(message = "{security.role.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 菜单ID集合
     */
    private Set<Long> menuIdSet;

    /**
     * 部门ID集合
     */
    private Set<Long> deptIdSet;

}
