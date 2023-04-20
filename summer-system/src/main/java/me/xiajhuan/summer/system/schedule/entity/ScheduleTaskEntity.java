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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.entity.CommonEntity;
import me.xiajhuan.summer.core.enums.StatusEnum;
import me.xiajhuan.summer.core.enums.TaskTypeEnum;

/**
 * 任务 Entity
 *
 * @author xiajhuan
 * @date 2023/4/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("schedule_task")
public class ScheduleTaskEntity extends CommonEntity {

    /**
     * 部门ID
     */
    @TableField(exist = false)
    private Long deptId;

    /**
     * Bean名称
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
