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

package me.xiajhuan.summer.system.security.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.*;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.enums.LoginOperationEnum;
import me.xiajhuan.summer.core.enums.LoginStatusEnum;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.*;

import me.xiajhuan.summer.system.log.service.LogLoginService;
import me.xiajhuan.summer.system.monitor.service.MonitorOnlineService;
import me.xiajhuan.summer.system.security.dto.LoginDto;
import me.xiajhuan.summer.system.security.dto.TokenDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.enums.CaptchaTypeEnum;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleDeptMapper;
import me.xiajhuan.summer.system.security.service.SecurityDeptService;
import me.xiajhuan.summer.system.security.service.SecurityMenuService;
import me.xiajhuan.summer.system.security.service.SecurityService;
import me.xiajhuan.summer.system.security.service.SecurityUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 权限管理 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/2/28
 * @see AbstractCaptcha
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityServiceImpl implements SecurityService {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    @Resource
    private SecurityUserService securityUserService;

    @Resource
    private SecurityMenuService securityMenuService;

    @Resource
    private SecurityDeptService securityDeptService;

    @Resource
    private LogLoginService logLoginService;

    @Resource
    private MonitorOnlineService monitorOnlineService;

    @Resource
    private SecurityRoleDeptMapper securityRoleDeptMapper;

    @Override
    public void buildCaptchaAndCache(HttpServletResponse response, String uuid) {
        // 构建验证码
        AbstractCaptcha captcha = buildGraphicCaptcha();

        // 响应验证码
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            captcha.write(response.getOutputStream());
        } catch (IOException e) {
            throw FileDownloadException.of(e, ErrorCode.CAPTCHA_GET_FAILURE);
        }

        // 缓存验证码
        CacheServerFactory.getCacheServer()
                .setString(captchaCode(uuid), captcha.getCode(),
                        setting.getInt("captcha.cache-ttl", "Security", 5) * TimeUnitConst.MINUTE);
    }

    @Override
    public boolean validateCaptcha(String uuid, String captcha) {
        CacheServer cacheServer = CacheServerFactory.getCacheServer();

        // 获取缓存的验证码
        String captchaKey = captchaCode(uuid);
        String captchaCode = cacheServer.getString(captchaKey);
        if (captchaCode != null) {
            cacheServer.delete(captchaKey);
        }
        // 校验
        return captcha.equalsIgnoreCase(captchaCode);
    }

    @Override
    public TokenDto login(LoginDto loginDto, HttpServletRequest request) {
        if (setting.getBool("enable-captcha", "Security", true)) {
            // 校验验证码
            AssertUtil.isNotBlank("uuid", loginDto.getUuid());
            if (StrUtil.isBlank(loginDto.getCaptcha())) {
                throw ValidationException.of(ErrorCode.CAPTCHA_NOT_NULL);
            }
            if (!validateCaptcha(loginDto.getUuid(), loginDto.getCaptcha())) {
                throw ValidationException.of(ErrorCode.CAPTCHA_ERROR);
            }
        }

        // 查询用户
        SecurityUserEntity entity = securityUserService.getByUsername(loginDto.getUsername());

        // 登录处理
        // 用户不存在
        if (entity == null) {
            logLoginService.saveAsync(loginDto.getUsername(), LoginOperationEnum.LOGIN.getValue(),
                    LoginStatusEnum.FAIL.getValue(), request);
            throw ValidationException.of(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 密码错误
        if (!SecurityUtil.matches(loginDto.getPassword(), entity.getPassword())) {
            logLoginService.saveAsync(entity.getUsername(), LoginOperationEnum.LOGIN.getValue(),
                    LoginStatusEnum.FAIL.getValue(), request);
            throw ValidationException.of(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 用户账号已停用
        if (entity.getStatus() == StatusEnum.DISABLE.getValue()) {
            logLoginService.saveAsync(entity.getUsername(), LoginOperationEnum.LOGIN.getValue(),
                    LoginStatusEnum.DISABLE.getValue(), request);
            throw ValidationException.of(ErrorCode.ACCOUNT_DISABLE);
        }

        // 登录成功
        // 记录日志
        logLoginService.saveAsync(entity.getUsername(), LoginOperationEnum.LOGIN.getValue(),
                LoginStatusEnum.SUCCESS.getValue(), request);

        // 生成Token并缓存
        TokenDto dto = generateTokenAndCache(entity);

        if (isGeneralUser(entity.getUserType())) {
            // 记录在线用户
            monitorOnlineService.saveOrUpdateAsync(entity, dto.getExpireTime());
        }

        return dto;
    }

    @Override
    public void logoutAndLog(LoginUser loginUser, HttpServletRequest request) {
        String loginUsername = loginUser.getUsername();
        if (logout(loginUser.getId(), true)) {
            // 用户退出成功
            logLoginService.saveAsync(loginUsername, LoginOperationEnum.LOGOUT.getValue(),
                    LoginStatusEnum.SUCCESS.getValue(), request);
        } else {
            // 用户退出失败
            logLoginService.saveAsync(loginUsername, LoginOperationEnum.LOGOUT.getValue(),
                    LoginStatusEnum.FAIL.getValue(), request);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logout(Long userId, boolean delOnline) {
        if (delOnline) {
            // 删除在线用户
            monitorOnlineService.delete(userId);
        }

        CacheServer cacheServer = CacheServerFactory.getCacheServer();
        String loginInfoKey = loginInfo(userId);
        if (cacheServer.hasHash(loginInfoKey)) {
            // accessToken
            String accessToken = String.valueOf(cacheServer.getHash(loginInfoKey, "accessToken"));

            // 删除用户ID
            cacheServer.delete(userId(accessToken));

            // 删除登录信息
            cacheServer.delete(loginInfoKey, CacheConst.Value.HASH);

            // 删除用户权限集合
            cacheServer.delete(permissions(userId), CacheConst.Value.LIST);

            return true;
        }
        return false;
    }

    /**
     * 构建图形验证码
     *
     * @return {@link AbstractCaptcha}
     * @see CaptchaTypeEnum
     */
    private AbstractCaptcha buildGraphicCaptcha() {
        // 验证码类型
        String type = setting.getByGroupWithLog("captcha.type", "Security");
        if (StrUtil.isBlank(type)) {
            // 没有配置则默认为：Line
            type = CaptchaTypeEnum.Line.getValue();
        }
        // 验证码宽/高
        int width = setting.getInt("captcha.width", "Security", 150);
        int height = setting.getInt("captcha.height", "Security", 40);
        // 验证码字符个数
        int charNum = setting.getInt("captcha.char-num", "Security", 5);
        switch (type) {
            case "Line":
                return CaptchaUtil.createLineCaptcha(width, height, charNum,
                        setting.getInt("captcha.line-num", "Security", 10));
            case "Circle":
                return CaptchaUtil.createCircleCaptcha(width, height, charNum,
                        setting.getInt("captcha.circle-num", "Security", 10));
            case "Shear":
                return CaptchaUtil.createShearCaptcha(width, height, charNum,
                        setting.getInt("captcha.shear-width", "Security", 4));
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的验证码类型【{}】", type));
        }
    }

    /**
     * 生成Token并缓存
     *
     * @param entity 用户Entity
     * @return TokenDto
     */
    private TokenDto generateTokenAndCache(SecurityUserEntity entity) {
        CacheServer cacheServer = CacheServerFactory.getCacheServer();

        // 生成accessToken
        String accessToken = SecurityUtil.generateToken();
        // Token过期时间（h）
        int tokenExpire = setting.getInt("token-expire", "Security", 12);
        // 缓存过期时间（ms）
        long cacheTtl = tokenExpire * TimeUnitConst.HOUR;
        // 缓存用户ID
        long userId = entity.getId();
        cacheServer.setString(userId(accessToken), String.valueOf(userId), cacheTtl);

        // 登录信息
        String loginInfoKey = loginInfo(userId);
        Map<String, Object> loginInfo = cacheServer.getHash(loginInfoKey);
        if (loginInfo == null) {
            // 未登录或登录失效
            loginInfo = MapUtil.newHashMap(2, true);
        } else {
            // 让原来的accessToken失效
            cacheServer.delete(userId(String.valueOf(loginInfo.get("accessToken"))));
        }
        // 缓存登录信息（覆盖）
        loginInfo.put("accessToken", accessToken);
        LoginUser loginUser = getLoginUser(entity);
        loginInfo.put("loginUser", loginUser);
        cacheServer.setHash(loginInfoKey, loginInfo, cacheTtl);

        // 缓存用户权限集合（覆盖）
        Set<String> permissions = securityMenuService.getPermissions(loginUser);
        if (permissions == null) {
            permissions = CollUtil.newHashSet(StrUtil.EMPTY);
        }
        cacheServer.setList(permissions(userId),
                ListUtil.toList(permissions), cacheTtl);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setExpireTime(tokenExpire * 3600);

        return tokenDto;
    }

    /**
     * 获取登录用户信息
     *
     * @param entity 用户Entity
     * @return 登录用户信息
     */
    private LoginUser getLoginUser(SecurityUserEntity entity) {
        LoginUser loginUser = BeanUtil.convert(entity, LoginUser.class);

        if (isGeneralUser(entity.getUserType())) {
            // 部门ID集合（用户所有角色关联的所有部门ID）
            loginUser.setDeptIdRoleBasedSet(securityRoleDeptMapper.getDeptIdRoleBasedSet(loginUser.getId()));

            // 本部门及本部门下子部门ID
            Long deptId = loginUser.getDeptId();
            Set<Long> deptIdSet = securityDeptService.getChildIdSet(deptId);
            deptIdSet.add(deptId);
            loginUser.setDeptAndChildIdSet(deptIdSet);
        }

        return loginUser;
    }

    /**
     * 是否为普通用户
     *
     * @param userType 用户类型，参考{@link UserTypeEnum}
     * @return 是否为普通用户，true：是 false：否
     */
    private boolean isGeneralUser(int userType) {
        return UserTypeEnum.GENERAL.getValue() == userType;
    }

}
