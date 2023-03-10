package me.xiajhuan.summer.common.security.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.dto.TreeNodeDto;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 部门 Dto
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityDeptDto extends TreeNodeDto {

    /**
     * 上级部门ID
     */
    @NotNull(message = "{security.dept.pid.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long pid;

    /**
     * 部门名称
     */
    @NotBlank(message = "{security.role.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 排序
     */
    @Min(value = 0, message = "{sort.number}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上级部门名称
     */
    private String parentName;

}
