package me.xiajhuan.summer.common.security.controller;

import me.xiajhuan.summer.common.data.Result;
import me.xiajhuan.summer.common.enums.CommonStatusEnum;
import me.xiajhuan.summer.common.enums.LoginOperationEnum;
import me.xiajhuan.summer.common.enums.LoginStatusEnum;
import me.xiajhuan.summer.common.exception.BusinessException;
import me.xiajhuan.summer.common.exception.ErrorCode;
import me.xiajhuan.summer.common.log.entity.LogLoginEntity;
import me.xiajhuan.summer.common.log.service.LogLoginService;
import me.xiajhuan.summer.common.security.dto.UserDto;
import me.xiajhuan.summer.common.security.dto.UserTokenDto;
import me.xiajhuan.summer.common.security.login.LoginDto;
import me.xiajhuan.summer.common.security.login.LoginUser;
import me.xiajhuan.summer.common.security.service.SecurityService;
import me.xiajhuan.summer.common.security.service.UserService;
import me.xiajhuan.summer.common.security.service.UserTokenService;
import me.xiajhuan.summer.common.utils.AssertUtil;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import me.xiajhuan.summer.common.utils.IpUtil;
import me.xiajhuan.summer.common.utils.SecurityUtil;
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
    private UserService userService;

    @Resource
    private UserTokenService userTokenService;

    @Resource
    private LogLoginService logLoginService;

    /**
     * 获取验证码
     *
     * @param uuid     uuid
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
     * @param loginDto 登录Dto
     * @param request  {@link HttpServletRequest}
     * @return 响应结果
     */
    @PostMapping("login")
    public Result<UserTokenDto> login(@Validated LoginDto loginDto, HttpServletRequest request) {
        // 校验验证码
        if (!mainService.validateCaptcha(loginDto.getUuid(), loginDto.getCaptcha())) {
            return Result.ofFail(ErrorCode.CAPTCHA_ERROR);
        }

        // 用户信息
        UserDto userDto = userService.getByUsername(loginDto.getUsername());

        // 登录处理
        // 用户不存在
        if (userDto == null) {
            saveLoginLog(loginDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.FAIL, request);
            return Result.ofFail(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 密码错误
        if (!SecurityUtil.matches(loginDto.getPassword(), userDto.getPassword())) {
            saveLoginLog(userDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.FAIL, request);
            return Result.ofFail(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 用户账号已停用
        if (userDto.getStatus() == CommonStatusEnum.DISABLE.getValue()) {
            saveLoginLog(userDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.LOCK, request);
            return Result.ofFail(ErrorCode.ACCOUNT_DISABLE);
        }

        // 登录成功
        saveLoginLog(userDto.getUsername(), LoginOperationEnum.LOGIN, LoginStatusEnum.SUCCESS, request);

        // 生成accessToken
        return Result.ofSuccess(userTokenService.generateToken(userDto.getId()));
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

        userTokenService.logout(loginUser.getId());

        saveLoginLog(loginUser.getUsername(), LoginOperationEnum.LOGOUT, LoginStatusEnum.SUCCESS, request);

        return Result.ofSuccess();
    }

    /**
     * 保存登录日志
     *
     * @param loginUser     登录用户
     * @param operationEnum 登录操作枚举
     * @param statusEnum    登录状态枚举
     * @param request       {@link HttpServletRequest}
     */
    private void saveLoginLog(String loginUser, LoginOperationEnum operationEnum, LoginStatusEnum statusEnum, HttpServletRequest request) {
        // 构建登录日志
        LogLoginEntity log = LogLoginEntity.builder()
                .loginUser(loginUser)
                .operation(operationEnum.getValue())
                .status(statusEnum.getValue())
                .userAgent(HttpContextUtil.getUserAgent(request))
                .ip(IpUtil.getRequestOriginIp(request)).build();

        // 异步保存登录日志
        logLoginService.saveLogAsync(log);
    }

}
