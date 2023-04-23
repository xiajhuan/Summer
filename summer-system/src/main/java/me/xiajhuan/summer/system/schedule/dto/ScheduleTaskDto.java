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

package me.xiajhuan.summer.system.schedule.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.TaskTypeEnum;
import me.xiajhuan.summer.core.validation.annotation.Json;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 定时任务 Dto
 *
 * @author xiajhuan
 * @date 2023/4/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleTaskDto extends PageSortDto {

    /**
     * Bean名称<br>
     * note：值为“简单类名（首字母小写）”或 {@link Component} 指定的名称
     */
    @Null(message = "{schedule.task.beanName.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{schedule.task.beanName.require}", groups = AddGroup.class)
    private String beanName;

    /**
     * 参数（Json格式）
     */
    @Json(groups = {AddGroup.class, UpdateGroup.class})
    private String json;

    /**
     * Cron表达式
     */
    @NotBlank(message = "{schedule.task.cronExpression.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String cronExpression;

    /**
     * 类型
     *
     * @see TaskTypeEnum
     */
    @Range(min = 0, max = 2, message = "{schedule.task.type.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{schedule.task.type.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer type;

    /**
     * 状态
     *
     * @see StatusEnum
     */
    @Range(min = 0, max = 1, message = "{status.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{status.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

}
