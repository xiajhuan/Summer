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

package me.xiajhuan.summer.admin.common.security.controller;

import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.admin.common.security.enums.LoginOperationEnum;
import me.xiajhuan.summer.admin.common.security.enums.LoginStatusEnum;
import me.xiajhuan.summer.core.exception.BusinessException;
import me.xiajhuan.summer.core.exception.ErrorCode;
import me.xiajhuan.summer.admin.common.log.entity.LogLoginEntity;
import me.xiajhuan.summer.admin.common.log.service.LogLoginService;
import me.xiajhuan.summer.admin.common.security.dto.SecurityUserDto;
import me.xiajhuan.summer.admin.common.security.dto.SecurityUserTokenDto;
import me.xiajhuan.summer.admin.common.security.dto.LoginDto;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.admin.common.security.service.SecurityService;
import me.xiajhuan.summer.admin.common.security.service.SecurityUserService;
import me.xiajhuan.summer.admin.common.security.service.SecurityUserTokenService;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import me.xiajhuan.summer.core.utils.IpUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限相关 Controller
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @Resource
    private SecurityService mainService;

    @Resource
    private SecurityUserService securityUserService;

    @Resource
    private SecurityUserTokenService securityUserTokenService;

    @Resource
    private LogLoginService logLoginService;

    /**
     * 获取验证码
     *
     * @param uuid     唯一标识，作为验证码 Key的一部分
     * @param response {@link HttpServletResponse}
     * @throws BusinessException 业务异常
     * @throws IOException       I/O异常
     */
    @GetMapping("captcha")
    public void captcha(String uuid, HttpServletResponse response) throws BusinessException, IOException {
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
    public Result<SecurityUserTokenDto> login(@Validated(DefaultGroup.class) LoginDto loginDto, HttpServletRequest request) {
        // 校验验证码
        if (!mainService.validateCaptcha(loginDto.getUuid(), loginDto.getCaptcha())) {
            return Result.ofFail(ErrorCode.CAPTCHA_ERROR);
        }

        // 查询用户
        SecurityUserDto securityUserDto = securityUserService.getByUsername(loginDto.getUsername());

        // 登录处理
        // 用户不存在
        if (securityUserDto == null) {
            saveLog(loginDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.FAIL, request);
            return Result.ofFail(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 密码错误
        if (!SecurityUtil.matches(loginDto.getPassword(), securityUserDto.getPassword())) {
            saveLog(securityUserDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.FAIL, request);
            return Result.ofFail(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 用户账号已停用
        if (securityUserDto.getStatus() == StatusEnum.DISABLE.getValue()) {
            saveLog(securityUserDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.DISABLE, request);
            return Result.ofFail(ErrorCode.ACCOUNT_DISABLE);
        }

        // 登录成功
        saveLog(securityUserDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.SUCCESS, request);

        // 生成用户Token
        return Result.ofSuccess(securityUserTokenService.generateUserToken(securityUserDto.getId()));
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

        securityUserTokenService.logout(loginUser.getId());

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
        LogLoginEntity log = LogLoginEntity.builder()
                .loginUser(loginUser)
                .operation(operationEnum.getValue())
                .status(statusEnum.getValue())
                .userAgent(HttpContextUtil.getUserAgent(request))
                .ip(IpUtil.getRequestIp(request)).build();

        // 异步保存日志
        logLoginService.saveAsync(log);
    }

}
