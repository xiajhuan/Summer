package me.xiajhuan.summer.common.log.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.dto.BaseDto;

import java.util.Date;

/**
 * 错误日志 Dto
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogErrorDto extends BaseDto {

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

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

    /**
     * 异常堆栈信息
     */
    private String errorInfo;

}