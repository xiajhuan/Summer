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

package me.xiajhuan.summer.admin.common.security.service;

import me.xiajhuan.summer.admin.common.security.cache.SecurityCacheKey;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserTokenEntity;
import me.xiajhuan.summer.core.data.LoginUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 权限相关 Service
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityService {

    //*******************认证授权********************

    /**
     * 获取用户权限集合
     *
     * @param loginUser 登录用户信息
     * @return 用户权限集合
     */
    Set<String> getPermissions(LoginUser loginUser);

    /**
     * 获取用户Token
     *
     * @param accessToken accessToken
     * @return 用户Token
     */
    SecurityUserTokenEntity getByAccessToken(String accessToken);

    /**
     * 根据用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    SecurityUserEntity getUserById(Long userId);

    /**
     * 获取部门ID集合（这里指用户所有角色关联的所有部门ID）
     *
     * @param userId 用户ID
     * @return 部门ID集合
     */
    Set<Long> getDeptIdSet(Long userId);

    /**
     * 获取本部门及本部门下子部门ID集合
     *
     * @param deptId 所属部门ID
     * @return 本部门及本部门下子部门ID集合
     */
    Set<Long> getDeptAndChildIdSet(Long deptId);

    //*******************验证码********************

    /**
     * 构建图形验证码并缓存
     *
     * @param response {@link HttpServletResponse}
     * @param uuid     唯一标识，作为验证码 Key的一部分
     * @throws IOException I/O异常
     * @see SecurityCacheKey#captchaCode(String)
     */
    void buildCaptchaAndCache(HttpServletResponse response, String uuid) throws IOException;

    /**
     * 校验验证码
     *
     * @param uuid    唯一标识，作为验证码 Key的一部分
     * @param captcha 验证码
     * @return 校验结果 true：成功 false：失败
     * @see SecurityCacheKey#captchaCode(String)
     */
    boolean validateCaptcha(String uuid, String captcha);

}
