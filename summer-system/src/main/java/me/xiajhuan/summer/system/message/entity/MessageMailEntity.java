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

package me.xiajhuan.summer.system.message.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.entity.CommonEntity;
import me.xiajhuan.summer.core.enums.MailContentTypeEnum;

/**
 * 邮件 Entity
 *
 * @author xiajhuan
 * @date 2023/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("message_mail")
public class MessageMailEntity extends CommonEntity {

    /**
     * 邮件名称
     */
    private String name;

    /**
     * 邮件标题
     */
    private String subject;

    /**
     * 邮件正文
     */
    private String content;

    /**
     * 正文类型
     *
     * @see MailContentTypeEnum
     */
    private String contentType;

}
