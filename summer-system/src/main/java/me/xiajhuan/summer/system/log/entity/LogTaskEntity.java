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

package me.xiajhuan.summer.system.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.core.base.entity.SimpleEntity;
import me.xiajhuan.summer.core.enums.NonLoggedUserEnum;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;
import me.xiajhuan.summer.core.enums.TaskTypeEnum;

import java.util.Date;

/**
 * 任务日志 Entity
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("log_task")
public class LogTaskEntity extends SimpleEntity {

    /**
     * 创建者，固定为“quartzTask”
     *
     * @see NonLoggedUserEnum#QUARTZ_TASK
     */
    private String createBy;

    /**
     * 创建时间<br>
     * note：不使用字段自动填充
     */
    private Date createTime;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * Bean名称
     */
    private String beanName;

    /**
     * 参数（Json格式）
     */
    private String json;

    /**
     * 任务时长（ms）
     */
    private Integer taskTime;

    /**
     * 类型
     *
     * @see TaskTypeEnum
     */
    private Integer type;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    private Integer status;

    /**
     * 异常堆栈信息
     */
    private String errorInfo;

}