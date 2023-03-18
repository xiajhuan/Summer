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

package me.xiajhuan.summer.admin.common.base.shiro;

import me.xiajhuan.summer.core.exception.ErrorCode;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserTokenEntity;
import me.xiajhuan.summer.admin.common.security.service.SecurityService;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Oauth2Realm
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see AuthorizingRealm
 */
@Component
public class Oauth2Realm extends AuthorizingRealm {

    @Lazy
    @Resource
    private SecurityService securityService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    // 授权（验证资源权限时调用）
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser loginUser = (LoginUser) principals.getPrimaryPrincipal();

        // 用户权限集合
        Set<String> permsSet = securityService.getPermissionsOfUser(loginUser);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    // 认证（登录时调用）
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        // 根据accessToken，查询用户Token
        SecurityUserTokenEntity tokenEntity = securityService.getByToken(accessToken);
        // token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException(LocaleUtil.getI18nMessage(ErrorCode.TOKEN_INVALID));
        }

        // 查询用户信息并转换成LoginUser
        LoginUser loginUser = ConvertUtil.convert(securityService.getUserById(tokenEntity.getUserId()), LoginUser.class);

        // 获取部门ID集合（这里指用户所有角色关联的所有部门ID）
        loginUser.setDeptIdSet(securityService.getDeptIdSetOfUser(loginUser.getId()));

        // 获取本部门及本部门下子部门ID
        loginUser.setDeptAndChildIdSet(securityService.getDeptAndChildIdSet(loginUser.getDeptId()));

        // 账号锁定
        if (loginUser.getStatus() == 0) {
            throw new LockedAccountException(LocaleUtil.getI18nMessage(ErrorCode.ACCOUNT_LOCK));
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, accessToken, getName());
        return info;
    }

}