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
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.*;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.*;

import me.xiajhuan.summer.system.security.dto.PasswordDto;
import me.xiajhuan.summer.system.security.dto.TokenDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.enums.CaptchaTypeEnum;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleDeptMapper;
import me.xiajhuan.summer.system.security.service.SecurityDeptService;
import me.xiajhuan.summer.system.security.service.SecurityMenuService;
import me.xiajhuan.summer.system.security.service.SecurityService;
import me.xiajhuan.summer.system.security.service.SecurityUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 权限相关 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/2/28
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
    private SecurityRoleDeptMapper securityRoleDeptMapper;

    @Override
    public void buildCaptchaAndCache(HttpServletResponse response, String uuid) {
        // 构建验证码
        AbstractCaptcha captcha = buildGraphicCaptcha();

        // 响应验证码
        response.setContentType("image/png");
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
    public TokenDto generateTokenAndCache(SecurityUserEntity entity) {
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

        // 获取登录信息
        String loginInfoKey = loginInfo(userId);
        Map<String, Object> loginInfo = cacheServer.getHash(loginInfoKey);
        if (loginInfo == null) {
            // 未登录或登录失效
            loginInfo = MapUtil.newHashMap(2, true);
        } else {
            // 让原来的accessToken失效
            cacheServer.delete(userId(String.valueOf(loginInfo.get(SecurityConst.LoginInfo.ACCESS_TOKEN))));
        }
        // 缓存登录信息（覆盖刷新）
        loginInfo.put(SecurityConst.LoginInfo.ACCESS_TOKEN, accessToken);
        LoginUser loginUser = getLoginUser(entity);
        loginInfo.put(SecurityConst.LoginInfo.LOGIN_USER, loginUser);
        cacheServer.setHash(loginInfoKey, loginInfo, cacheTtl);

        // 缓存用户权限集合（覆盖刷新）
        Set<String> permissions = securityMenuService.getPermissions(loginUser);
        if (permissions == null) {
            permissions = CollUtil.newHashSet(StrUtil.EMPTY);
        }
        cacheServer.setList(permissions(userId),
                ListUtil.toList(permissions), cacheTtl);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setExpireTime(tokenExpire);

        return tokenDto;
    }

    @Override
    public void updatePassword(PasswordDto dto, LoginUser loginUser) {
        if (!SecurityUtil.matches(dto.getOldPassword(), loginUser.getPassword())) {
            // 原密码不正确
            throw ValidationException.of(ErrorCode.PASSWORD_ERROR);
        }

        String newPassword = dto.getNewPassword();
        if (!newPassword.equals(dto.getConfirmPassword())) {
            // 密码和确认密码不一致
            throw ValidationException.of(ErrorCode.PASSWORD_CONFIRM_ERROR);
        }

        LambdaUpdateWrapper<SecurityUserEntity> updateWrapper = addUserSetField(
                SecurityUtil.encode(newPassword), loginUser.getUsername());
        updateWrapper.eq(SecurityUserEntity::getId, loginUser.getId());
        securityUserService.update(updateWrapper);
    }

    @Override
    public String resetPassword(Long[] ids) {
        String passwordReset = setting.getByGroupWithLog("password-reset", "Security");
        if (StrUtil.isBlank(passwordReset)) {
            // 没有配置则默认为：123456
            passwordReset = "123456";
        }

        LambdaUpdateWrapper<SecurityUserEntity> updateWrapper = addUserSetField(SecurityUtil.encode(passwordReset), "superAdmin");
        updateWrapper.in(SecurityUserEntity::getId, ids);
        securityUserService.update(updateWrapper);

        return passwordReset;
    }

    @Override
    public void logout(Long userId) {
        CacheServer cacheServer = CacheServerFactory.getCacheServer();

        String loginInfoKey = loginInfo(userId);
        if (cacheServer.hasHash(loginInfoKey)) {
            // 获取accessToken
            String accessToken = String.valueOf(cacheServer.getHash(loginInfoKey, SecurityConst.LoginInfo.ACCESS_TOKEN));

            // 删除用户ID
            cacheServer.delete(userId(accessToken));

            // 删除登录信息
            cacheServer.delete(loginInfoKey, CacheConst.Value.HASH);

            // 删除用户权限集合
            cacheServer.delete(permissions(userId), CacheConst.Value.LIST);
        }
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
        // 验证码宽高
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
     * 获取登录用户信息
     *
     * @param entity 用户Entity
     * @return 登录用户信息
     */
    private LoginUser getLoginUser(SecurityUserEntity entity) {
        LoginUser loginUser = BeanUtil.convert(entity, LoginUser.class);

        if (UserTypeEnum.GENERAL.getValue() == entity.getUserType()) {
            loginUser.setDeptIdRoleBasedSet(getDeptIdRoleBasedSet(loginUser.getId()));

            loginUser.setDeptAndChildIdSet(getDeptAndChildIdSet(loginUser.getDeptId()));
        }

        return loginUser;
    }

    /**
     * 获取部门ID集合（这里指用户所有角色关联的所有部门ID）
     *
     * @param userId 用户ID
     * @return 部门ID集合
     */
    private Set<Long> getDeptIdRoleBasedSet(Long userId) {
        return securityRoleDeptMapper.getDeptIdRoleBasedSet(userId);
    }

    /**
     * 获取本部门及本部门下子部门ID集合
     *
     * @param deptId 所属部门ID
     * @return 本部门及本部门下子部门ID集合
     */
    private Set<Long> getDeptAndChildIdSet(Long deptId) {
        // 获取子部门ID集合
        Set<Long> deptIdSet = securityDeptService.getChildIdSet(deptId);
        deptIdSet.add(deptId);

        return deptIdSet;
    }

    /**
     * 获取 {@link LambdaUpdateWrapper}（修改密码时用户的set字段）
     *
     * @param newPassword 新密码（密文）
     * @param username    用户名
     * @return {@link LambdaUpdateWrapper}
     */
    private LambdaUpdateWrapper<SecurityUserEntity> addUserSetField(String newPassword, String username) {
        // note：通过 update(LambdaUpdateWrapper) 更新时基础字段自动填充不会生效
        LambdaUpdateWrapper<SecurityUserEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(SecurityUserEntity::getPassword, newPassword);
        updateWrapper.set(SecurityUserEntity::getUpdateBy, username);
        updateWrapper.set(SecurityUserEntity::getUpdateTime, DateUtil.date());

        return updateWrapper;
    }

}
