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

import lombok.Data;
import me.xiajhuan.summer.admin.common.security.cache.SecurityCacheKey;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户登录 Dto
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "{security.user.username.require}", groups = DefaultGroup.class)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "{security.user.password.require}", groups = DefaultGroup.class)
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * uuid<br>
     * 唯一标识，作为验证码 Key的一部分
     *
     * @see SecurityCacheKey#captchaCode(String)
     */
    private String uuid;

}
