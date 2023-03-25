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

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.constant.DateFormatConst;
import me.xiajhuan.summer.core.dto.BaseDto;

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
    @ColumnWidth(40)
    @ExcelProperty(value = "请求URI", index = 0)
    private String requestUri;

    /**
     * 请求方式
     */
    @ExcelProperty(value = "请求方式", index = 1)
    private String requestMethod;

    /**
     * 请求参数
     */
    @ColumnWidth(100)
    @ExcelProperty(value = "请求参数", index = 2)
    private String requestParams;

    /**
     * 用户代理
     */
    @ColumnWidth(40)
    @ExcelProperty(value = "用户代理", index = 3)
    private String userAgent;

    /**
     * 操作IP
     */
    @ExcelProperty(value = "操作IP", index = 4)
    private String ip;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 5)
    @DateTimeFormat(DateFormatConst.DATE_TIME)
    private Date createTime;

    /**
     * 异常堆栈信息
     */
    private String errorInfo;

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