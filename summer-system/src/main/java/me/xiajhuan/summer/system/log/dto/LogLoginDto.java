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
import me.xiajhuan.summer.core.enums.LoginOperationEnum;
import me.xiajhuan.summer.core.enums.LoginStatusEnum;
import me.xiajhuan.summer.system.log.excel.converter.LoginOperationConverter;
import me.xiajhuan.summer.system.log.excel.converter.LoginStatusConverter;

import java.util.Date;

/**
 * 登录日志 Dto
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogLoginDto extends ExcelDto {

    /**
     * 登录用户名
     */
    @ExcelProperty(value = "登录用户名", index = 0)
    private String loginUser;

    /**
     * 用户操作
     *
     * @see LoginOperationEnum
     */
    @ExcelProperty(value = "用户操作", index = 1, converter = LoginOperationConverter.class)
    private Integer operation;

    /**
     * 登录状态
     *
     * @see LoginStatusEnum
     */
    @ExcelProperty(value = "登录状态", index = 2, converter = LoginStatusConverter.class)
    private Integer status;

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