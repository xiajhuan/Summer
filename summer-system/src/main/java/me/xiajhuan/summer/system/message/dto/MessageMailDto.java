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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.enums.MailContentTypeEnum;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 邮件 Dto
 *
 * @author xiajhuan
 * @date 2023/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageMailDto extends PageSortDto {

    /**
     * 邮件名称
     */
    @NotBlank(message = "{message.mail.name.require}", groups = AddGroup.class)
    @Null(message = "{message.mail.name.null}", groups = UpdateGroup.class)
    private String name;

    /**
     * 邮件标题
     */
    @NotBlank(message = "{message.mail.subject.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String subject;

    /**
     * 邮件正文
     */
    @NotBlank(message = "{message.mail.content.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String content;

    /**
     * 正文类型
     *
     * @see MailContentTypeEnum
     */
    @NotNull(message = "{message.mail.contentType.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String contentType;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;

}
