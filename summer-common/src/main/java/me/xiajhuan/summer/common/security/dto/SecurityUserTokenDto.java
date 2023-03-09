package me.xiajhuan.summer.common.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户Token Dto
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@Data
public class SecurityUserTokenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * accessToken
     */
    private String token;

    /**
     * 过期时间（s）
     */
    private Integer expireTime;

}
