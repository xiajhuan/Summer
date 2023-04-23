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
import me.xiajhuan.summer.core.enums.TaskTypeEnum;
import me.xiajhuan.summer.system.log.excel.converter.OperationStatusConverter;
import me.xiajhuan.summer.system.log.excel.converter.TaskTypeConverter;

import java.util.Date;

/**
 * 定时任务日志 Dto
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogTaskDto extends ExcelDto {

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * Bean名称
     */
    @ExcelProperty(value = "Bean名称", index = 0)
    private String beanName;

    /**
     * 参数（Json格式）
     */
    @ColumnWidth(100)
    @ExcelProperty(value = "参数（Json格式）", index = 1)
    private String json;

    /**
     * 任务时长（ms）
     */
    @ExcelProperty(value = "任务时长（ms）", index = 2)
    private Integer taskTime;

    /**
     * 类型
     *
     * @see TaskTypeEnum
     */
    @ExcelProperty(value = "类型", index = 3, converter = TaskTypeConverter.class)
    private Integer type;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    @ExcelProperty(value = "状态", index = 4, converter = OperationStatusConverter.class)
    private Integer status;

    /**
     * 创建时间
     */
    @DateTimeFormat(DateFormatConst.DATE_TIME)
    @ExcelProperty(value = "创建时间", index = 5)
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