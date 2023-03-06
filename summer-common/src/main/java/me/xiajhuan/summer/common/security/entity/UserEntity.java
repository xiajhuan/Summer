package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.CommonBaseEntity;
import me.xiajhuan.summer.common.enums.GenderEnum;
import me.xiajhuan.summer.common.enums.CommonStatusEnum;
import me.xiajhuan.summer.common.enums.UserTypeEnum;

/**
 * 用户 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_user")
public class UserEntity extends CommonBaseEntity {

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
     * 所属部门名称
     */
    @TableField(exist = false)
    private String deptName;

}
