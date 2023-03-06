package me.xiajhuan.summer.common.security.oauth2;

import me.xiajhuan.summer.common.exception.ErrorCode;
import me.xiajhuan.summer.common.security.entity.UserTokenEntity;
import me.xiajhuan.summer.common.security.service.SecurityService;
import me.xiajhuan.summer.common.security.login.LoginUser;
import me.xiajhuan.summer.common.utils.ConvertUtil;
import me.xiajhuan.summer.common.utils.MessageUtil;
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
 * Oauth2域
 *
 * @author xiajhuan
 * @date 2023/02/27
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

        // 用户权限列表
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
        UserTokenEntity tokenEntity = securityService.getByToken(accessToken);
        // token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException(MessageUtil.getI18nMessage(ErrorCode.TOKEN_INVALID));
        }

        // 查询用户信息并转换成UserLogin对象
        LoginUser loginUser = ConvertUtil.convert(securityService.getUserById(tokenEntity.getUserId()), LoginUser.class);

        // 获取用户所属部门ID列表（数据权限）
        loginUser.setDeptIdList(securityService.getDeptIdListOfUser(loginUser.getId()));

        // 账号锁定
        if (loginUser.getStatus() == 0) {
            throw new LockedAccountException(MessageUtil.getI18nMessage(ErrorCode.ACCOUNT_LOCK));
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, accessToken, getName());
        return info;
    }

}