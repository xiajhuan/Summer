package me.xiajhuan.summer.server;

import cn.hutool.core.lang.Console;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import me.xiajhuan.summer.common.utils.WildcardUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SummerServerApplicationTests {

    private static final String template = "{} matches {},result:{}\n";

    @Test
    void securityUtilTest() {
        Console.log("生成的Token：{}\n", SecurityUtil.generateToken());

        String plainText = "16042XJH";
        String hashed = SecurityUtil.encode(plainText);
        Console.log(template, plainText, hashed, SecurityUtil.matches(plainText, hashed));
    }

    @Test
    void wildcardUtilTest() {
        String source = "c_log_operation";
        String pattern1 = "*_log_*";
        String pattern2 = "?_log_*";
        Console.log(template, source, pattern1, WildcardUtil.matches(source, pattern1));
        Console.log(template, source, pattern2, WildcardUtil.matches(source, pattern2));
    }

}
