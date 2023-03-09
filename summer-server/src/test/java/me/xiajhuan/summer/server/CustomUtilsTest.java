package me.xiajhuan.summer.server;

import cn.hutool.core.lang.Console;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import me.xiajhuan.summer.common.utils.WildcardUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 自定义工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@SpringBootTest
class CustomUtilsTest {

    private static final String template = "{} matches {},result:{}\n";

    /**
     * 权限相关工具测试
     *
     * @see SecurityUtil#generateToken()
     * @see SecurityUtil#encode(String)
     * @see SecurityUtil#matches(String, String)
     */
    @Test
    void securityUtilTest() {
        Console.log("生成的Token：{}\n", SecurityUtil.generateToken());

        String plainText = "16042XJH";
        String hashed = SecurityUtil.encode(plainText);
        Console.log(template, plainText, hashed, SecurityUtil.matches(plainText, hashed));
    }

    /**
     * 通配符工具测试
     *
     * @see WildcardUtil#matches(String, String)
     */
    @Test
    void wildcardUtilTest() {
        String source = "log_operation";
        String pattern1 = "*log_*";
        String pattern2 = "?log_*";
        Console.log(template, source, pattern1, WildcardUtil.matches(source, pattern1));
        Console.log(template, source, pattern2, WildcardUtil.matches(source, pattern2));
    }

}
