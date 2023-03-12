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

package me.xiajhuan.summer.admin.common.log.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xiajhuan.summer.core.constant.DateFormatConst;

import java.io.Serializable;
import java.util.Date;

/**
 * 错误日志 Excel
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(25)
public class LogErrorExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求URI
     */
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
    @ExcelProperty(value = "请求参数", index = 2)
    private String requestParams;

    /**
     * 用户代理
     */
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
    @DateTimeFormat(DateFormatConst.DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 异常堆栈信息
     */
    @ColumnWidth(150)
    @ExcelProperty(value = "异常堆栈信息", index = 6)
    private String errorInfo;

}
