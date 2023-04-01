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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.CommonMsg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 通配符工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
@DisplayName("WildcardUtil")
public class WildcardUtilTest implements CommonMsg {

    /**
     * @see WildcardUtil#matches(String, String)
     */
    @Test
    void matches() {
        String methodSignature = "WildcardUtil#matches(String, String)";
        Console.log(startMsg(methodSignature));

        String source1 = "log_operation";
        String pattern1 = "log_*";
        assertTrue(WildcardUtil.matches(source1, pattern1),
                StrUtil.format(MATCHES_FAIL_MSG, source1, pattern1));

        String source2 = "security_role_dept";
        String pattern2 = "security_*";
        assertTrue(WildcardUtil.matches(source2, pattern2),
                StrUtil.format(MATCHES_FAIL_MSG, source2, pattern2));

        Console.log(successMsg(methodSignature));
    }

}
