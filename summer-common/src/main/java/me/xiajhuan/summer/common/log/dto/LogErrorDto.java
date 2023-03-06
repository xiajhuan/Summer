package me.xiajhuan.summer.common.log.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 错误日志 Dto
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
public class LogErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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

    //*******************查询专用********************

    /**
     * 创建时间区间（开始）
     */
    private Date createTimeStart;

    /**
     * 创建时间区间（结束）
     */
    private Date createTimeEnd;

}