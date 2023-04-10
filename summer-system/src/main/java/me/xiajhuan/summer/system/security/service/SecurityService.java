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
import me.xiajhuan.summer.system.security.dto.PasswordDto;
import me.xiajhuan.summer.system.security.dto.TokenDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * 权限相关 Service
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityService {

    //*******************验证码********************

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
     * @return 校验结果，true：成功 false：失败
     */
    boolean validateCaptcha(String uuid, String captcha);

    //*******************权限相关********************

    /**
     * 生成Token并缓存
     *
     * @param dto 用户Entity
     * @return Token
     */
    TokenDto generateTokenAndCache(SecurityUserEntity dto);

    /**
     * 修改密码
     *
     * @param dto       密码Dto
     * @param loginUser 登录用户信息
     */
    void updatePassword(PasswordDto dto, LoginUser loginUser);

    /**
     * 重置密码
     *
     * @param ids ID数组
     * @return 重置的密码
     */
    String resetPassword(Long[] ids);

    /**
     * 用户退出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

}
