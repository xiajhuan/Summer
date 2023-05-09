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

package me.xiajhuan.summer.system.message.dto;

import lombok.Getter;
import lombok.Setter;
import me.xiajhuan.summer.core.validation.annotation.Json;
import me.xiajhuan.summer.core.validation.group.DefaultGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 发送邮件 Dto
 *
 * @author xiajhuan
 * @date 2023/5/9
 */
@Setter
@Getter
public class SendMailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮件ID
     */
    @NotNull(message = "{message.mail.mailId.require}", groups = DefaultGroup.class)
    private Long mailId;

    /**
     * 收件人，多个以“,”分隔
     */
    @NotBlank(message = "{message.mail.receiversTo.require}", groups = DefaultGroup.class)
    private String receiversTo;

    /**
     * 抄送人，多个以“,”分隔
     */
    private String receiversCc;

    /**
     * 密送人，多个以“,”分隔
     */
    private String receiversBcc;

    /**
     * 参数（Json格式）
     */
    @Json(groups = DefaultGroup.class)
    private String json;

}
