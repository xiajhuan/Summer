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

package me.xiajhuan.summer.common.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.common.security.login.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import me.xiajhuan.summer.common.enums.DefaultUserEnum;

/**
 * 权限相关工具
 *
 * @author xiajhuan
 * @date 2023/02/27
 * @see SecureUtil
 * @see BCrypt
 * @see SecurityUtils
 */
public class SecurityUtil {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 生成Token
     *
     * @return Token
     */
    public static String generateToken() {
        return SecureUtil.md5(UUID.randomUUID().toString());
    }

    /**
     * 加密（BCrypt加密算法）
     *
     * @param plaintext 明文
     * @return 密文
     */
    public static String encode(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    /**
     * 检查明文是否匹配密文
     *
     * @param plaintext 明文
     * @param hashed    密文
     * @return 是否匹配 true：匹配 false：不匹配
     */
    public static boolean matches(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID
     */
    public static Long getCurrentUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取当前用户名，若不存在则默认为：systemUser
     *
     * @return 当前用户名
     * @see DefaultUserEnum
     */
    public static String getCurrentUsername() {
        return getCurrentUsername(DefaultUserEnum.SYSTEM_USER.getValue());
    }

    /**
     * 获取当前用户名
     *
     * @param defaultUsername 默认用户名
     * @return 当前用户名
     */
    public static String getCurrentUsername(String defaultUsername) {
        LoginUser loginUser = getLoginUser();

        if (loginUser.getId() == null) {
            return StrUtil.isNotBlank(defaultUsername) ? defaultUsername : DefaultUserEnum.SYSTEM_USER.getValue();
        } else {
            return loginUser.getUsername();
        }
    }

    /**
     * 获取当前用户真实姓名，若不存在则默认为：系统用户
     *
     * @return 当前用户真实姓名
     * @see DefaultUserEnum
     */
    public static String getCurrentRealName() {
        return getCurrentRealName(DefaultUserEnum.SYSTEM_USER.getName());
    }

    /**
     * 获取当前用户真实姓名
     *
     * @param defaultRealName 默认真实姓名
     * @return 当前用户真实姓名
     */
    public static String getCurrentRealName(String defaultRealName) {
        LoginUser loginUser = getLoginUser();

        if (loginUser.getId() == null) {
            return StrUtil.isNotBlank(defaultRealName) ? defaultRealName : DefaultUserEnum.SYSTEM_USER.getName();
        } else {
            return loginUser.getRealName();
        }
    }

    /**
     * 获取当前用户部门ID
     *
     * @return 当前用户部门ID
     */
    public static Long getCurrentDeptId() {
        return getLoginUser().getDeptId();
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    public static LoginUser getLoginUser() {
        Subject subject = getSubject();
        if (subject == null) {
            return new LoginUser();
        }

        LoginUser loginUser = (LoginUser) subject.getPrincipal();
        if (loginUser == null) {
            return new LoginUser();
        }

        return loginUser;
    }

    /**
     * 获取 {@link Subject}<br>
     * Subject：表示单个应用程序用户的状态和安全操作
     *
     * @return {@link Subject}
     */
    private static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        } catch (Exception e) {
            LOGGER.error(e, "获取Subject失败【{}】", e.getMessage());

            return null;
        }
    }

}