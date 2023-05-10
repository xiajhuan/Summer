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
import me.xiajhuan.summer.core.enums.OperationStatusEnum;

/**
 * 邮件日志 Entity
 *
 * @author xiajhuan
 * @date 2023/5/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("log_mail")
public class LogMailEntity extends SimpleEntity {

    /**
     * 邮件名称
     */
    private String mailName;

    /**
     * 发件人
     */
    private String sender;

    /**
     * 收件人
     */
    private String receiversTo;

    /**
     * 抄送人
     */
    private String receiversCc;

    /**
     * 密送人
     */
    private String receiversBcc;

    /**
     * 邮件标题
     */
    private String subject;

    /**
     * 邮件正文
     */
    private String content;

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
