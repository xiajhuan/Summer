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

package me.xiajhuan.summer.system.security.cache;

import cn.hutool.core.util.StrUtil;

/**
 * security模块缓存Key
 *
 * @author xiajhuan
 * @date 2023/3/17
 */
public class SecurityCacheKey {

    /**
     * 验证码 Key
     *
     * @param keyParam 填充参数
     * @return 验证码 Key
     */
    public static String captchaCode(String keyParam) {
        return StrUtil.format("CAPTCHA_CODE_{}", keyParam);
    }

    /**
     * 用户ID Key
     *
     * @param keyParam 填充参数
     * @return 用户ID Key
     */
    public static String userId(String keyParam) {
        return StrUtil.format("USER_ID_{}", keyParam);
    }

    /**
     * 登录信息 Key
     *
     * @param keyParam 填充参数
     * @return 登录信息 Key
     */
    public static String loginInfo(String keyParam) {
        return StrUtil.format("LOGIN_INFO_{}", keyParam);
    }

    /**
     * 用户权限集合 Key
     *
     * @param keyParam 填充参数
     * @return 用户权限集合 Key
     */
    public static String permissions(String keyParam) {
        return StrUtil.format("PERMISSIONS_{}", keyParam);
    }

}
