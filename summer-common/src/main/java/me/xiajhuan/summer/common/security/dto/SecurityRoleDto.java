package me.xiajhuan.summer.common.security.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.dto.BaseDto;
import me.xiajhuan.summer.common.validation.group.DefaultGroup;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

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
    @NotBlank(message = "{security.role.name.require}", groups = DefaultGroup.class)
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
     * 菜单ID列表
     */
    private List<Long> menuIdList;

    /**
     * 部门ID列表
     */
    private List<Long> deptIdList;

}
