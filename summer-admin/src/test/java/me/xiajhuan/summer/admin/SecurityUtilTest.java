package me.xiajhuan.summer.admin;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.utils.SecurityUtil;
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
