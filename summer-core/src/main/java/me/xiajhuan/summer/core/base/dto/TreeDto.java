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

package me.xiajhuan.summer.core.base.dto;

import cn.hutool.core.lang.tree.TreeNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Map;

/**
 * 树形结构 Dto基类
 *
 * @author xiajhuan
 * @date 2023/4/26
 * @see TreeNode
 */
public abstract class TreeDto<Long> extends TreeNode<Long> {

    /**
     * 主键ID
     */
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    /**
     * 上级ID
     */
    @NotNull(message = "{parentId.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long parentId;

    /**
     * 名称
     */
    @NotBlank(message = "{name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private CharSequence name;

    /**
     * 顺序，越小优先级越高
     */
    @Min(value = 0, message = "{weight.number}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{weight.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer weight;

    /**
     * 扩展字段（不 序列化/反序列化）
     *
     * @see TreeNode#extra
     */
    @JsonIgnore
    private Map<String, Object> extra;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public TreeDto<Long> setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public TreeDto<Long> setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public TreeDto<Long> setName(CharSequence name) {
        this.name = name;
        return this;
    }

    @Override
    public Integer getWeight() {
        return weight;
    }

    /**
     * weight Setter
     * <p>
     * note：这里不能 {@link Override} {@link TreeNode#setWeight(Comparable)}，
     * 必须明确形参为 {@link Integer}，否则“weight属性”会反序列化失败！
     * </p>
     *
     * @param weight 顺序
     * @return TreeDto
     */
    public TreeDto<Long> setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

}
