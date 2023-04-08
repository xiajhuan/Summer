/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.system.log.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.constant.DateFormatConst;
import me.xiajhuan.summer.core.base.dto.ExcelDto;
import me.xiajhuan.summer.core.enums.OperationGroupEnum;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;
import me.xiajhuan.summer.system.log.excel.converter.OperationGroupConverter;
import me.xiajhuan.summer.system.log.excel.converter.OperationStatusConverter;

import java.util.Date;

/**
 * 操作日志 Dto
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogOperationDto extends ExcelDto {

    /**
     * 用户操作
     */
    @ColumnWidth(40)
    @ExcelProperty(value = "用户操作", index = 0)
    private String operation;

    /**
     * 操作分组
     *
     * @see OperationGroupEnum
     */
    @ExcelProperty(value = "操作分组", index = 1, converter = OperationGroupConverter.class)
    private Integer operationGroup;

    /**
     * 请求URI
     */
    @ColumnWidth(40)
    @ExcelProperty(value = "请求URI", index = 2)
    private String requestUri;

    /**
     * 请求方式
     */
    @ExcelProperty(value = "请求方式", index = 3)
    private String requestMethod;

    /**
     * 请求参数
     */
    @ColumnWidth(100)
    @ExcelProperty(value = "请求参数", index = 4)
    private String requestParams;

    /**
     * 请求时长（ms）
     */
    @ExcelProperty(value = "请求时长（ms）", index = 5)
    private Integer requestTime;

    /**
     * 用户代理
     */
    @ColumnWidth(40)
    @ExcelProperty(value = "用户代理", index = 6)
    private String userAgent;

    /**
     * 操作IP
     */
    @ExcelProperty(value = "操作IP", index = 7)
    private String ip;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    @ExcelProperty(value = "状态", index = 8, converter = OperationStatusConverter.class)
    private Integer status;

    /**
     * 操作人
     */
    @ExcelProperty(value = "操作人", index = 9)
    private String operateBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 10)
    @DateTimeFormat(DateFormatConst.DATE_TIME)
    private Date createTime;

    //*******************查询参数********************

    /**
     * 创建时间区间（开始）
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createTimeStart;

    /**
     * 创建时间区间（结束）
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createTimeEnd;

}