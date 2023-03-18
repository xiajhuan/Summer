/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.security.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import lombok.*;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

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
 * @see TreeNode
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
     * 子节点
     */
    private List<SecurityDeptDto> children = CollUtil.newArrayList();

    /**
     * 上级部门名称
     */
    private String parentName;

}
