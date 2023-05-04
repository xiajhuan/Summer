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

package me.xiajhuan.summer.system.security.shiro.oauth2;

import cn.hutool.core.collection.CollUtil;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.List;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.*;

/**
 * Oauth2Realm
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see AuthorizingRealm
 * @see AuthenticationInfo
 * @see AuthorizationInfo
 */
@Component
public class Oauth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = String.valueOf(token.getPrincipal());

        CacheServer cacheServer = CacheServerFactory.getCacheServer();
        // 用户ID
        String userId = cacheServer.getString(userId(accessToken));
        if (userId == null) {
            throw new IncorrectCredentialsException(LocaleUtil.getI18nMessage(ErrorCode.TOKEN_INVALID));
        }

        // 登录用户信息
        final LoginUser loginUser = (LoginUser) cacheServer.getHash(loginInfo(Long.parseLong(userId)), "loginUser");

        return new SimpleAuthenticationInfo(loginUser, accessToken, getName());
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser loginUser = (LoginUser) principals.getPrimaryPrincipal();

        // 用户权限集合
        List<String> permissions = CacheServerFactory.getCacheServer()
                .getList(permissions(loginUser.getId()));

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(CollUtil.newHashSet(permissions));

        return info;
    }

}