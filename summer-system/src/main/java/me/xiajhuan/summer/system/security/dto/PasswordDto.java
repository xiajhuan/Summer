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

import lombok.Data;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 密码 Dto
 *
 * @author xiajhuan
 * @date 2023/4/10
 */
@Data
public class PasswordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原密码
     */
    @NotBlank(message = "{security.user.oldPassword.require}", groups = DefaultGroup.class)
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "{security.user.newPassword.require}", groups = DefaultGroup.class)
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "{security.user.confirmPassword.require}", groups = DefaultGroup.class)
    private String confirmPassword;

}
