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

package me.xiajhuan.summer.admin.common;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.constant.StrTemplateConst;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 权限相关工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
@SpringBootTest
public class SecurityUtilTest {

    /**
     * @see SecurityUtil#generateToken()
     * @see SecurityUtil#encode(String)
     * @see SecurityUtil#matches(String, String)
     */
    @Test
    public void test() {
        String token = SecurityUtil.generateToken();
        assertNotNull(token, "生成Token失败！");
        assertTrue(token.length() == 32, "生成的Token长度不为32！");
        Console.log("生成的Token：{}", token);

        String plainText = "16042XJH";
        String hashed = SecurityUtil.encode(plainText);
        assertTrue(SecurityUtil.matches(plainText, hashed),
                StrUtil.format(StrTemplateConst.MATCHES_FAIL_MSG, plainText, hashed));

        Console.log("Successful test!");
    }

}
