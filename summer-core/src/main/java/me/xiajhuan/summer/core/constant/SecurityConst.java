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

package me.xiajhuan.summer.core.constant;

/**
 * 权限相关常量
 *
 * @author xiajhuan
 * @date 2023/2/27
 */
public class SecurityConst {

    /**
     * 内部接口
     */
    public static class Inner {

        /**
         * 携带accessToken的请求头名称
         */
        public static final String TOKEN_HEADER = "AccessToken";

    }

    /**
     * 开放接口
     */
    public static class Open {

        /**
         * 携带签名的请求头名称
         */
        public static final String SIGNATURE_HEADER = "Signature";

    }

    /**
     * 登录信息（Hash缓存）
     */
    public static class LoginInfo {

        /**
         * Field-accessToken
         */
        public static final String ACCESS_TOKEN = "accessToken";

        /**
         * Field-loginUser
         */
        public static final String LOGIN_USER = "loginUser";

    }

}
