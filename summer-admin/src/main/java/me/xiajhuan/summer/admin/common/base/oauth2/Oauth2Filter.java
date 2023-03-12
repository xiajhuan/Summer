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

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.constant.SecurityConst;
import me.xiajhuan.summer.core.constant.StrTemplateConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.ErrorCode;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Oauth2Filter
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see AuthenticatingFilter
 */
public class Oauth2Filter extends AuthenticatingFilter {

    private static final Log LOGGER = LogFactory.get();

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 获取请求携带token
        String token = getRequestToken((HttpServletRequest) request);

        if (StrUtil.isBlank(token)) {
            return null;
        }
        return new Oauth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求携带token，若token不存在则直接返回401
        String token = getRequestToken((HttpServletRequest) request);

        if (StrUtil.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            setResponseHeader((HttpServletRequest) request, httpResponse);

            HttpContextUtil.makeResponse(httpResponse, StrUtil.format(StrTemplateConst.MEDIA_TYPE, "application/json", "charset=utf-8"),
                    ErrorCode.UNAUTHORIZED, Result.ofFail(ErrorCode.UNAUTHORIZED));

            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException authException, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        setResponseHeader((HttpServletRequest) request, httpResponse);
        try {
            // 处理登录失败的异常
            Throwable cause = authException.getCause() == null ? authException : authException.getCause();

            HttpContextUtil.makeResponse(httpResponse, StrUtil.format(StrTemplateConst.MEDIA_TYPE, "application/json", "charset=utf-8"),
                    ErrorCode.UNAUTHORIZED, Result.ofFail(ErrorCode.UNAUTHORIZED, cause.getMessage()));
        } catch (IOException e) {
            LOGGER.error(e, e.getMessage());
        }
        return false;
    }

    /**
     * 设置响应头
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    private void setResponseHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin(request));
    }

    /**
     * 获取请求携带Token
     *
     * @param httpRequest {@link HttpServletRequest}
     * @return 请求携带Token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        // 从header中获取token
        String token = httpRequest.getHeader(SecurityConst.TOKEN_HEADER);

        // 如果header中不存在token，则从参数中获取token
        if (StrUtil.isBlank(token)) {
            token = httpRequest.getParameter(SecurityConst.TOKEN_HEADER);
        }
        return token;
    }

}