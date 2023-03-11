package me.xiajhuan.summer.admin;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.utils.WildcardUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 通配符工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
@SpringBootTest
public class WildcardUtilTest {

    /**
     * @see WildcardUtil#matches(String, String)
     */
    @Test
    public void test() {
        String source1 = "log_operation";
        String pattern1 = "log_*";
        assertTrue(WildcardUtil.matches(source1, pattern1),
                StrUtil.format(StrTemplateConst.MATCHES_FAIL_MSG, source1, pattern1));

        String source2 = "security_user_token";
        String pattern2 = "*_token";
        assertTrue(WildcardUtil.matches(source2, pattern2),
                StrUtil.format(StrTemplateConst.MATCHES_FAIL_MSG, source2, pattern2));

        String source3 = "security_role_user";
        String pattern3 = "security_role_*";
        assertTrue(WildcardUtil.matches(source3, pattern3),
                StrUtil.format(StrTemplateConst.MATCHES_FAIL_MSG, source3, pattern3));

        Console.log("Successful test!");
    }

}
