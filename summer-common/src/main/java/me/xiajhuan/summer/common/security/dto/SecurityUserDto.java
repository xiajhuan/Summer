package me.xiajhuan.summer.common.security.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.dto.BaseDto;
import me.xiajhuan.summer.common.enums.CommonStatusEnum;
import me.xiajhuan.summer.common.enums.DataScopeEnum;
import me.xiajhuan.summer.common.enums.GenderEnum;
import me.xiajhuan.summer.common.enums.UserTypeEnum;
import me.xiajhuan.summer.common.validation.annotation.Mobile;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;
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
public class SecurityUserDto extends BaseDto {

    /**
     * 用户名
     */
    @NotBlank(message = "{security.user.username.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "{security.user.password.require}", groups = AddGroup.class)
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
    @Email(message = "{security.user.email.error}", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
    @Mobile(message = "{security.user.mobile.error}", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    /**
     * 部门ID
     */
    @NotNull(message = "{security.user.deptId.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long deptId;

    /**
     * 状态
     *
     * @see CommonStatusEnum
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
     * @see DataScopeEnum
     */
    @Range(min = 0, max = 4, message = "{security.user.status.range}", groups = {AddGroup.class, UpdateGroup.class})
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
