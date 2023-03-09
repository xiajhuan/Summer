package me.xiajhuan.summer.common.security.oauth2;

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
    public Oauth2Token(String token) {
        this.token = token;
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
