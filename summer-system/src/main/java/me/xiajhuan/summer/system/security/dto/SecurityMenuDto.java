/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.system.security.dto;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.TreeNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.xiajhuan.summer.system.security.enums.ComponentTypeEnum;
import me.xiajhuan.summer.system.security.enums.OpenModeEnum;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.security.entity.SecurityMenuEntity;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**
 * 菜单 Dto
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
public class SecurityMenuDto extends TreeNode {

    /**
     * 主键ID
     */
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    /**
     * 上级菜单ID
     */
    @NotNull(message = "{security.menu.parentId.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "{security.menu.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权
     *
     * @see SecurityMenuEntity#permissions
     */
    private String permissions;

    /**
     * 类型
     *
     * @see ComponentTypeEnum
     */
    @Range(min = 0, max = 1, message = "{security.menu.type.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{security.menu.type.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer type;

    /**
     * 打开方式
     *
     * @see OpenModeEnum
     */
    @Range(min = 0, max = 1, message = "{security.menu.openMode.range}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer openMode;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 顺序，越小优先级越高
     */
    @Min(value = 0, message = "{weight.number}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{weight.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer weight;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;

    /**
     * 子节点
     */
    private List<SecurityMenuDto> children = ListUtil.of();

    /**
     * 上级菜单名称
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String parentName;

}
