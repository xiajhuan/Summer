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

package me.xiajhuan.summer.core.data;

import lombok.Data;
import me.xiajhuan.summer.core.constant.DataScopeConst;
import me.xiajhuan.summer.core.enums.UserTypeEnum;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录用户信息<br>
 * note：只包含必要字段
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
     * 密码（密文）
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 本部门ID
     */
    private Long deptId;

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
     * 部门ID集合（这里指用户所有角色关联的所有部门ID）
     */
    private Set<Long> deptIdRoleBasedSet;

    /**
     * 本部门及本部门下子部门ID
     */
    private Set<Long> deptAndChildIdSet;

}