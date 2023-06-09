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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.CommonMsg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 权限管理工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
@DisplayName("SecurityUtil")
public class SecurityUtilTest implements CommonMsg {

    /**
     * 明文
     */
    private static final String PLAIN_TEXT = "16042XJH";

    /**
     * 密文
     */
    private static String HASHED;

    /**
     * @see SecurityUtil#generateToken()
     */
    @Test
    void generateToken() {
        String methodSignature = "SecurityUtil#generateToken()";
        Console.log(startMsg(methodSignature));

        String token = SecurityUtil.generateToken();
        assertNotNull(token, "生成Token失败！");
        assertEquals(32, token.length(), "生成的Token长度不为32！");
        Console.log("生成的Token：{}", token);

        Console.log(successMsg(methodSignature));
    }

    /**
     * @see SecurityUtil#encode(String)
     */
    @Test
    void encode() {
        String methodSignature = "SecurityUtil#encode(String)";
        Console.log(startMsg(methodSignature));

        HASHED = SecurityUtil.encode(PLAIN_TEXT);
        assertNotNull(HASHED, "加密失败！");
        assertEquals(60, HASHED.length(), "密文长度不为60！");
        Console.log("加密后的密文：{}", HASHED);

        Console.log(successMsg(methodSignature));
    }

    /**
     * @see SecurityUtil#matches(String, String)
     */
    @Test
    void matches() {
        String methodSignature = "SecurityUtil#matches(String, String)";
        Console.log(startMsg(methodSignature));

        assertTrue(SecurityUtil.matches(PLAIN_TEXT, HASHED),
                StrUtil.format(MATCHES_FAIL_MSG, PLAIN_TEXT, HASHED));

        Console.log(successMsg(methodSignature));
    }

}
