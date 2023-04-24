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
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.xiajhuan.summer.core.base.dto.ExcelDto;
import me.xiajhuan.summer.core.constant.DateFormatConst;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.excel.converter.StatusConverter;
import me.xiajhuan.summer.system.security.enums.GenderEnum;
import me.xiajhuan.summer.core.constant.DataScopeConst;
import me.xiajhuan.summer.core.validation.annotation.Mobile;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.security.excel.converter.DataScopeConverter;
import me.xiajhuan.summer.system.security.excel.converter.GenderConverter;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SecurityUserDto extends ExcelDto {

    /**
     * 用户名
     */
    @NotBlank(message = "{security.user.username.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "用户名", index = 0)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "{security.user.password.require}", groups = AddGroup.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "{security.user.confirmPassword.require}", groups = AddGroup.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;

    /**
     * 真实姓名
     */
    @NotBlank(message = "{security.user.realName.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "真实姓名", index = 1)
    private String realName;

    /**
     * 头像URL
     */
    @ColumnWidth(40)
    @ExcelProperty(value = "头像URL", index = 2)
    private String headUrl;

    /**
     * 性别
     *
     * @see GenderEnum
     */
    @Range(min = 0, max = 2, message = "{security.user.gender.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{security.user.gender.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "性别", index = 3, converter = GenderConverter.class)
    private Integer gender;

    /**
     * 邮箱
     */
//    @NotBlank(message = "{security.user.email.require}", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "{security.user.email.error}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "邮箱", index = 4)
    private String email;

    /**
     * 手机号
     */
//    @NotBlank(message = "{security.user.mobile.require}", groups = {AddGroup.class, UpdateGroup.class})
    @Mobile(groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "手机号", index = 5)
    private String mobile;

    /**
     * 本部门ID
     */
    @NotNull(message = "{security.user.deptId.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long deptId;

    /**
     * 状态
     *
     * @see StatusEnum
     */
    @Range(min = 0, max = 1, message = "{status.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{status.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "状态", index = 6, converter = StatusConverter.class)
    private Integer status;

    /**
     * 数据权限
     *
     * @see DataScopeConst.Type
     */
    @Range(min = 0, max = 4, message = "{security.user.dataScope.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{security.user.dataScope.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "数据权限", index = 7, converter = DataScopeConverter.class)
    private Integer dataScope;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(DateFormatConst.DATE_TIME)
    @ExcelProperty(value = "创建时间", index = 8)
    private Date createTime;

    /**
     * 本部门名称
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ExcelProperty(value = "本部门名称", index = 9)
    private String deptName;

    /**
     * 角色ID集合
     */
    private Set<Long> roleIdSet = CollUtil.newHashSet();

    /**
     * 岗位ID集合
     */
    private Set<Long> postIdSet = CollUtil.newHashSet();

}
