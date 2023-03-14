/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.log.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.dto.BaseDto;
import me.xiajhuan.summer.core.enums.OperationGroupEnum;
import me.xiajhuan.summer.admin.common.log.enums.OperationStatusEnum;

import java.util.Date;

/**
 * 操作日志 Dto
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogOperationDto extends BaseDto {

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 操作分组
     *
     * @see OperationGroupEnum
     */
    private Integer operationGroup;

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
     * 请求时长（ms）
     */
    private Integer requestTime;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    private Integer status;

    /**
     * 操作人
     */
    private String operateBy;

    /**
     * 创建时间
     */
    private Date createTime;

}