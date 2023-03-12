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

package me.xiajhuan.summer.core.data;

import lombok.Data;
import me.xiajhuan.summer.core.enums.DataScopeEnum;
import me.xiajhuan.summer.core.enums.GenderEnum;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.UserTypeEnum;

import java.io.Serializable;
import java.util.Set;

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
     * @see DataScopeEnum
     */
    private Integer dataScope;

    /**
     * 部门ID集合（这里指用户所有角色关联的所有部门ID）
     */
    private Set<Long> deptIdSet;

    /**
     * 本部门及本部门下子部门ID
     */
    private Set<Long> deptAndChildIdSet;

}