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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.dto.PageSortDto;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.admin.common.security.enums.GenderEnum;
import me.xiajhuan.summer.core.constant.DataScopeConst;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.validation.annotation.Mobile;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

/**
 * 用户 Dto
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityUserDto extends PageSortDto {

    /**
     * 用户名
     */
    @NotBlank(message = "{security.user.username.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "{security.user.password.require}", groups = AddGroup.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 真实姓名
     */
    @NotBlank(message = "{security.user.realName.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String realName;

    /**
     * 头像URL
     */
    private String headUrl;

    /**
     * 性别
     *
     * @see GenderEnum
     */
    @Range(min = 0, max = 2, message = "{security.user.gender.range}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer gender;

    /**
     * 邮箱
     */
//    @NotBlank(message = "{security.user.email.require}", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "{security.user.email.error}", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
//    @NotBlank(message = "{security.user.mobile.require}", groups = {AddGroup.class, UpdateGroup.class})
    @Mobile(groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    /**
     * 部门ID
     */
    @NotNull(message = "{security.user.deptId.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long deptId;

    /**
     * 状态
     *
     * @see StatusEnum
     */
    @Range(min = 0, max = 1, message = "{security.user.status.range}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 用户类型
     *
     * @see UserTypeEnum
     */
    private Integer userType;

    /**
     * 数据权限
     *
     * @see DataScopeConst.Type
     */
    @Range(min = 0, max = 4, message = "{security.user.dataScope.range}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer dataScope;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色ID集合
     */
    private Set<Long> roleIdSet;

    /**
     * 所属部门名称
     */
    private String deptName;

}
