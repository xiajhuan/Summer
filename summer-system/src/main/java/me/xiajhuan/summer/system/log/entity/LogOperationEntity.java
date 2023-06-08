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
import me.xiajhuan.summer.system.log.enums.OperationGroupEnum;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;

import java.util.Date;

/**
 * 操作日志 Entity
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("log_operation")
public class LogOperationEntity extends SimpleEntity {

    /**
     * 创建者，固定为systemUser
     *
     * @see NonLoggedUserEnum#SYSTEM_USER
     */
    private String createBy;

    /**
     * 创建时间<br>
     * note：不使用字段自动填充
     */
    private Date createTime;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 操作分组
     *
     * @see OperationGroupEnum
     */
    private Integer operationGroup;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求耗时（ms）
     */
    private Integer requestTime;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    private Integer status;

    /**
     * 操作人
     */
    private String operateBy;

}