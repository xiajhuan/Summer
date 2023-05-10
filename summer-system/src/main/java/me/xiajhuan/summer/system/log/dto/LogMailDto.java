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
import me.xiajhuan.summer.core.base.dto.ExcelDto;
import me.xiajhuan.summer.core.constant.DateFormatConst;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;
import me.xiajhuan.summer.core.excel.converter.OperationStatusConverter;

import java.util.Date;

/**
 * 邮件日志 Dto
 *
 * @author xiajhuan
 * @date 2023/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogMailDto extends ExcelDto {

    /**
     * 邮件名称
     */
    @ExcelProperty(value = "邮件名称", index = 0)
    private String mailName;

    /**
     * 发件人
     */
    @ExcelProperty(value = "发件人", index = 1)
    private String sender;

    /**
     * 收件人
     */
    @ColumnWidth(60)
    @ExcelProperty(value = "收件人", index = 2)
    private String receiversTo;

    /**
     * 抄送人
     */
    @ColumnWidth(60)
    @ExcelProperty(value = "抄送人", index = 3)
    private String receiversCc;

    /**
     * 密送人
     */
    @ColumnWidth(60)
    @ExcelProperty(value = "密送人", index = 4)
    private String receiversBcc;

    /**
     * 邮件标题
     */
    @ExcelProperty(value = "邮件标题", index = 5)
    private String subject;

    /**
     * 邮件正文
     */
    @ColumnWidth(100)
    @ExcelProperty(value = "邮件正文", index = 6)
    private String content;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    @ExcelProperty(value = "状态", index = 7, converter = OperationStatusConverter.class)
    private Integer status;

    /**
     * 创建时间
     */
    @DateTimeFormat(DateFormatConst.DATE_TIME)
    @ExcelProperty(value = "创建时间", index = 8)
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
