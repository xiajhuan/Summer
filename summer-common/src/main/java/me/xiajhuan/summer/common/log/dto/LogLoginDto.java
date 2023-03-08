package me.xiajhuan.summer.common.log.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.dto.BaseDto;
import me.xiajhuan.summer.common.enums.LoginOperationEnum;
import me.xiajhuan.summer.common.enums.LoginStatusEnum;

import java.util.Date;

/**
 * 登录日志 Dto
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogLoginDto extends BaseDto {

    /**
     * 登录用户
     */
    private String loginUser;

    /**
     * 用户操作
     *
     * @see LoginOperationEnum
     */
    private Integer operation;

    /**
     * 登录状态
     *
     * @see LoginStatusEnum
     */
    private Integer status;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;

}