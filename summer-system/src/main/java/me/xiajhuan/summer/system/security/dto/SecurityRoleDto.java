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

package me.xiajhuan.summer.system.security.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.dto.PageSortDto;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

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
public class SecurityRoleDto extends PageSortDto {

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
