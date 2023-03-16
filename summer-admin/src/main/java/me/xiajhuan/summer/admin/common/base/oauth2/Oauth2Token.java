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

package me.xiajhuan.summer.admin.common.base.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Oauth2Token
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see AuthenticationToken
 */
public class Oauth2Token implements AuthenticationToken {

    /**
     * accessToken
     */
    private String token;

    /**
     * 构造Oauth2Token
     *
     * @param token accessToken
     */
    private Oauth2Token(String token) {
        this.token = token;
    }

    /**
     * 构建Oauth2Token
     *
     * @param token accessToken
     * @return Oauth2Token
     */
    public static Oauth2Token of(String token) {
        return new Oauth2Token(token);
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
