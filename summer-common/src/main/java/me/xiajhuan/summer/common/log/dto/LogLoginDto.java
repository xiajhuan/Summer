package me.xiajhuan.summer.common.log.dto;

import lombok.Data;
import me.xiajhuan.summer.common.enums.LoginOperationEnum;
import me.xiajhuan.summer.common.enums.LoginStatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志 Dto
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
public class LogLoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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