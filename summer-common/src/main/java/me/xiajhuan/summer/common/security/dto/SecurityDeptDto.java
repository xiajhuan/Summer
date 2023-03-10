package me.xiajhuan.summer.common.security.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import lombok.*;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**
 * 部门 Dto
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SecurityDeptDto extends TreeNode {

    /**
     * 主键ID
     */
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    /**
     * 上级部门ID
     */
    @NotNull(message = "{security.dept.parentId.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long parentId;

    /**
     * 部门名称
     */
    @NotBlank(message = "{security.dept.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 顺序，越小优先级越高
     */
    @Min(value = 0, message = "{weight.number}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer weight;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 子节点列表
     */
    private List<SecurityDeptDto> children = CollUtil.newArrayList();

    /**
     * 上级部门名称
     */
    private String parentName;

}
