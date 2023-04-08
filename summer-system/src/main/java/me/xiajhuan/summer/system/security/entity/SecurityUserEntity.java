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

package me.xiajhuan.summer.system.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.entity.CommonEntity;
import me.xiajhuan.summer.core.constant.DataScopeConst;
import me.xiajhuan.summer.system.security.enums.GenderEnum;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.UserTypeEnum;

import java.util.Date;

/**
 * 用户 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_user")
public class SecurityUserEntity extends CommonEntity {

    /**
     * 创建者<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private String createBy;

    /**
     * 创建时间<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private Date createTime;

    /**
     * 更新者<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private String updateBy;

    /**
     * 更新时间<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private Date updateTime;

    /**
     * 本部门ID<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private Long deptId;

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
     * 手机号
     */
    private String mobile;

    /**
     * 状态
     *
     * @see StatusEnum
     */
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
    private Integer dataScope;

    /**
     * 本部门名称
     */
    @TableField(exist = false)
    private String deptName;

}
