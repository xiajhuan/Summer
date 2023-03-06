package me.xiajhuan.summer.common.security.login;

import lombok.Data;
import me.xiajhuan.summer.common.enums.GenderEnum;
import me.xiajhuan.summer.common.enums.CommonStatusEnum;
import me.xiajhuan.summer.common.enums.UserTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 *
 * @author xiajhuan
 * @date 2023/02/27
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     *
     * @see GenderEnum
     */
    private Integer gender;

    /**
     * 头像URL
     */
    private String headUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 所属部门ID
     */
    private Long deptId;

    /**
     * 状态
     *
     * @see CommonStatusEnum
     */
    private Integer status;

    /**
     * 用户类型
     *
     * @see UserTypeEnum
     */
    private Integer userType;

    /**
     * 所属部门ID列表（数据权限）
     */
    private List<Long> deptIdList;

}