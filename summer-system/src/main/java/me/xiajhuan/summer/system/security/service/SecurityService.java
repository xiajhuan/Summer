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

package me.xiajhuan.summer.system.security.service;

import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.system.security.dto.LoginDto;
import me.xiajhuan.summer.system.security.dto.TokenDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限管理 Service
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityService {

    //*******************验证码********************

    /**
     * 是否开启登录验证码校验
     *
     * @return 是否开启登录验证码校验，true：是 false：否
     */
    boolean isEnableCaptcha();

    /**
     * 构建图形验证码并缓存
     *
     * @param response {@link HttpServletResponse}
     * @param uuid     唯一标识，作为验证码 Key的一部分
     */
    void buildCaptchaAndCache(HttpServletResponse response, String uuid);

    /**
     * 校验验证码
     *
     * @param uuid    唯一标识，作为验证码 Key的一部分
     * @param captcha 验证码
     * @return 是否校验成功，true：是 false：否
     */
    boolean validateCaptcha(String uuid, String captcha);

    //*******************登录/退出********************

    /**
     * 用户登录
     *
     * @param loginDto 用户登录Dto
     * @param request  {@link HttpServletRequest}
     * @return TokenDto
     */
    TokenDto login(LoginDto loginDto, HttpServletRequest request);

    /**
     * 用户退出并记录日志
     *
     * @param loginUser 登录用户信息
     * @param request   {@link HttpServletRequest}
     */
    void logoutAndLog(LoginUser loginUser, HttpServletRequest request);

    /**
     * 用户退出
     *
     * @param userId    用户ID
     * @param delOnline 是否删除在线用户，true：是 false：否
     * @return 是否退出成功，true：是 false：否
     */
    boolean logout(Long userId, boolean delOnline);

}
