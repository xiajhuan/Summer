package me.xiajhuan.summer.admin;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import me.xiajhuan.summer.common.utils.WildcardUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 自定义工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@SpringBootTest
class CustomUtilsTest {

    private static final String template = "{} matches {} failed!";

    /**
     * 权限相关工具测试
     *
     * @see SecurityUtil#generateToken()
     * @see SecurityUtil#encode(String)
     * @see SecurityUtil#matches(String, String)
     */
    @Test
    void securityUtilTest() {
        String token = SecurityUtil.generateToken();
        assertNotNull(token, "生成Token失败！");
        assertTrue(token.length() == 32, "生成的Token长度不为32！");
        Console.log("生成的Token：{}", token);

        String plainText = "16042XJH";
        String hashed = SecurityUtil.encode(plainText);
        assertTrue(SecurityUtil.matches(plainText, hashed),
                StrUtil.format(template, plainText, hashed));
    }

    /**
     * 通配符工具测试
     *
     * @see WildcardUtil#matches(String, String)
     */
    @Test
    void wildcardUtilTest() {
        String source1 = "log_operation";
        String pattern1 = "log_*";
        assertTrue(WildcardUtil.matches(source1, pattern1),
                StrUtil.format(template, source1, pattern1));

        String source2 = "security_user_token";
        String pattern2 = "*_token";
        assertTrue(WildcardUtil.matches(source2, pattern2),
                StrUtil.format(template, source2, pattern2));

        String source3 = "security_role_user";
        String pattern3 = "security_role_*";
        assertTrue(WildcardUtil.matches(source3, pattern3),
                StrUtil.format(template, source3, pattern3));
    }

}
