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

package me.xiajhuan.summer.admin.common.base.shiro.oauth2;

import cn.hutool.core.collection.CollUtil;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.SecurityConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import static me.xiajhuan.summer.admin.common.security.cache.SecurityCacheKey.userId;
import static me.xiajhuan.summer.admin.common.security.cache.SecurityCacheKey.loginInfo;
import static me.xiajhuan.summer.admin.common.security.cache.SecurityCacheKey.permissions;

import java.util.List;

/**
 * Oauth2Realm
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see AuthorizingRealm
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
        // 获取用户ID
        String userIdStr = cacheServer.getString(userId(accessToken));
        if (userIdStr == null) {
            throw new IncorrectCredentialsException(LocaleUtil.getI18nMessage(ErrorCode.TOKEN_INVALID));
        }

        // 获取登录用户信息
        final LoginUser loginUser = (LoginUser) cacheServer.getHash(loginInfo(userIdStr), SecurityConst.LoginInfo.LOGIN_USER);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, accessToken, getName());
        return info;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser loginUser = (LoginUser) principals.getPrimaryPrincipal();

        // 用户权限集合
        List<String> permissions = CacheServerFactory.getCacheServer()
                .getList(permissions(String.valueOf(loginUser.getId())));

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(CollUtil.newHashSet(permissions));
        return info;
    }

}