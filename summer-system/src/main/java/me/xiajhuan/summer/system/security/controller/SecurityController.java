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

package me.xiajhuan.summer.system.security.controller;

import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.*;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import me.xiajhuan.summer.system.security.dto.LoginDto;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.dto.TokenDto;
import me.xiajhuan.summer.system.security.service.SecurityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限管理 Controller
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @Resource
    private SecurityService mainService;

    /**
     * 获取验证码
     *
     * @param uuid     唯一标识，作为验证码 Key的一部分
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("captcha")
    @RateLimiter(1)
    public void captcha(String uuid, HttpServletResponse response) {
        AssertUtil.isNotBlank("uuid", uuid);
        mainService.buildCaptchaAndCache(response, uuid);
    }

    /**
     * 用户登录
     *
     * @param loginDto 用户登录Dto
     * @param request  {@link HttpServletRequest}
     * @return 响应结果
     */
    @PostMapping("login")
    @RateLimiter(0.5)
    public Result<TokenDto> login(@Validated(DefaultGroup.class) LoginDto loginDto, HttpServletRequest request) {
        return Result.ofSuccess(mainService.login(loginDto, request));
    }

    /**
     * 登录信息
     *
     * @return 响应结果
     */
    @GetMapping("info")
    public Result<SecurityUserDto> info() {
        return Result.ofSuccess(BeanUtil.convert(SecurityUtil.getLoginUser(), SecurityUserDto.class));
    }

    /**
     * 用户退出
     *
     * @param request {@link HttpServletRequest}
     * @return 响应结果
     */
    @PostMapping("logout")
    public Result logout(HttpServletRequest request) {
        mainService.logoutAndLog(SecurityUtil.getLoginUser(), request);
        return Result.ofSuccess();
    }

}
