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

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.xiajhuan.summer.core.validation.annotation.Mobile;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import me.xiajhuan.summer.system.security.enums.GenderEnum;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息 Dto
 *
 * @author xiajhuan
 * @date 2023/5/11
 */
@Setter
@Getter
@ToString
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @NotNull(message = "{id.require}", groups = DefaultGroup.class)
    private Long id;

    /**
     * 真实姓名
     */
    @NotBlank(message = "{security.user.realName.require}", groups = DefaultGroup.class)
    private String realName;

    /**
     * 性别
     *
     * @see GenderEnum
     */
    @Range(min = 0, max = 2, message = "{security.user.gender.range}", groups = DefaultGroup.class)
    @NotNull(message = "{security.user.gender.require}", groups = DefaultGroup.class)
    private Integer gender;

    /**
     * 邮箱
     */
//    @NotBlank(message = "{security.user.email.require}", groups = DefaultGroup.class)
    @Email(message = "{security.user.email.error}", groups = DefaultGroup.class)
    private String email;

    /**
     * 手机号
     */
//    @NotBlank(message = "{security.user.mobile.require}", groups = DefaultGroup.class)
    @Mobile(groups = DefaultGroup.class)
    private String mobile;

    /**
     * 头像URL（外链）
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String headUrl;

    /**
     * 头像路径（相对路径）
     */
    private String headPath;

    /**
     * 描述
     */
    private String description;

    /**
     * 本部门名称
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String deptName;

    /**
     * 角色名称集合
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<String> roleNameSet = CollUtil.newHashSet();

    /**
     * 岗位名称集合
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<String> postNameSet = CollUtil.newHashSet();

}
