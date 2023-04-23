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

package me.xiajhuan.summer.system.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.core.base.entity.CommonEntity;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.TaskTypeEnum;
import org.springframework.stereotype.Component;

/**
 * 定时任务 Entity
 *
 * @author xiajhuan
 * @date 2023/4/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("schedule_task")
public class ScheduleTaskEntity extends CommonEntity {

    /**
     * 主键ID，默认策略：分配ID（雪花算法）
     *
     * @see IdType
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 部门ID
     */
    @TableField(exist = false)
    private Long deptId;

    /**
     * Bean名称<br>
     * note：值为“简单类名（首字母小写）”或 {@link Component} 指定的名称
     */
    private String beanName;

    /**
     * 参数（Json格式）
     */
    private String json;

    /**
     * Cron表达式
     */
    private String cronExpression;

    /**
     * 类型
     *
     * @see TaskTypeEnum
     */
    private Integer type;

    /**
     * 状态
     *
     * @see StatusEnum
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

}
