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

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.LoginOperationEnum;
import me.xiajhuan.summer.core.enums.LoginStatusEnum;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.utils.*;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import me.xiajhuan.summer.system.log.entity.LogLoginEntity;
import me.xiajhuan.summer.system.log.service.LogLoginService;
import me.xiajhuan.summer.system.security.dto.LoginDto;
import me.xiajhuan.summer.system.security.dto.PasswordDto;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.dto.TokenDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.service.SecurityService;
import me.xiajhuan.summer.system.security.service.SecurityUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 权限相关 Controller
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    @Resource
    private SecurityService mainService;

    @Resource
    private SecurityUserService securityUserService;

    @Resource
    private LogLoginService logLoginService;

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
        if (setting.getBool("enable-captcha", "Security", true)) {
            // 校验验证码
            AssertUtil.isNotBlank("uuid", loginDto.getUuid());
            if (StrUtil.isBlank(loginDto.getCaptcha())) {
                return Result.ofFail(ErrorCode.CAPTCHA_NOT_NULL);
            }
            if (!mainService.validateCaptcha(loginDto.getUuid(), loginDto.getCaptcha())) {
                return Result.ofFail(ErrorCode.CAPTCHA_ERROR);
            }
        }

        // 查询用户
        SecurityUserEntity entity = securityUserService.getByUsername(loginDto.getUsername());

        // 登录处理
        // 用户不存在
        if (entity == null) {
            saveLog(loginDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.FAIL, request);
            return Result.ofFail(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 密码错误
        if (!SecurityUtil.matches(loginDto.getPassword(), entity.getPassword())) {
            saveLog(entity.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.FAIL, request);
            return Result.ofFail(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 用户账号已停用
        if (entity.getStatus() == StatusEnum.DISABLE.getValue()) {
            saveLog(entity.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.DISABLE, request);
            return Result.ofFail(ErrorCode.ACCOUNT_DISABLE);
        }

        // 登录成功
        saveLog(entity.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.SUCCESS, request);

        // 生成Token并缓存
        return Result.ofSuccess(mainService.generateTokenAndCache(entity));
    }

    /**
     * 登录信息
     *
     * @return 响应结果
     */
    @GetMapping("login/info")
    public Result<SecurityUserDto> loginInfo() {
        return Result.ofSuccess(BeanUtil.convert(SecurityUtil.getLoginUser(), SecurityUserDto.class));
    }

    /**
     * 修改密码
     *
     * @param dto 密码Dto
     * @return 响应结果
     */
    @PutMapping("password")
    public Result password(@Validated(DefaultGroup.class) PasswordDto dto) {
        LoginUser loginUser = SecurityUtil.getLoginUser();

        // 修改密码
        mainService.updatePassword(dto, loginUser);

        // 修改成功后自动退出
        mainService.logout(loginUser.getId());

        return Result.ofSuccess();
    }

    /**
     * 重置密码
     *
     * @param ids ID数组
     * @return 响应结果
     */
    @PutMapping("reset")
    public Result reset(Long[] ids) {
        AssertUtil.isNotEmpty("ids", ids);

        LoginUser loginUser = SecurityUtil.getLoginUser();
        if (UserTypeEnum.SUPER_ADMIN.getValue() != loginUser.getUserType()) {
            // 只有超级管理员可以重置密码
            return Result.ofFail(ErrorCode.PASSWORD_RESET_ERROR);
        }

        // 重置密码
        String passwordReset = mainService.resetPassword(ids);

        // 重置成功后还在线的用户自动退出
        Arrays.stream(ids).forEach(mainService::logout);

        return Result.ofSuccess(null, Result.SuccessCode.PASSWORD_RESET, passwordReset);
    }

    /**
     * 用户退出
     *
     * @param request {@link HttpServletRequest}
     * @return 响应结果
     */
    @PostMapping("logout")
    public Result logout(HttpServletRequest request) {
        LoginUser loginUser = SecurityUtil.getLoginUser();

        mainService.logout(loginUser.getId());

        saveLog(loginUser.getUsername(), LoginOperationEnum.LOGOUT, LoginStatusEnum.SUCCESS, request);

        return Result.ofSuccess();
    }

    /**
     * 保存登录日志
     *
     * @param loginUser     登录用户名
     * @param operationEnum 登录操作枚举
     * @param statusEnum    登录状态枚举
     * @param request       {@link HttpServletRequest}
     */
    private void saveLog(String loginUser, LoginOperationEnum operationEnum, LoginStatusEnum statusEnum, HttpServletRequest request) {
        // 构建登录日志
        LogLoginEntity entity = LogLoginEntity.builder()
                .loginUser(loginUser)
                .operation(operationEnum.getValue())
                .status(statusEnum.getValue())
                .userAgent(ServletUtil.getUserAgent(request))
                .ip(ServletUtil.getClientIP(request)).build();

        // 异步保存日志
        logLoginService.saveAsync(entity);
    }

}
