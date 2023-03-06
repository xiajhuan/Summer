package me.xiajhuan.summer.common.security.login;

import lombok.Data;
import me.xiajhuan.summer.common.constant.CacheConst;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录 Dto
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "{c.security.user.username.require}")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "{c.security.user.password.require}")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "{c.security.captcha.require}")
    private String captcha;

    /**
     * uuid<br>
     * 唯一标识，作为验证码缓存Key的一部分
     *
     * @see CacheConst#CAPTCHA_CODE
     */
    @NotBlank(message = "{c.security.uuid.require}")
    private String uuid;

}
