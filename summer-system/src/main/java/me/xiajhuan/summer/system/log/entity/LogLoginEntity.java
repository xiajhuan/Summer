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
import me.xiajhuan.summer.core.enums.LoginOperationEnum;
import me.xiajhuan.summer.core.enums.LoginStatusEnum;
import me.xiajhuan.summer.core.enums.NonLoggedUserEnum;

import java.util.Date;

/**
 * 登录日志 Entity
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("log_login")
public class LogLoginEntity extends SimpleEntity {

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
     * 登录用户名
     */
    private String loginUser;

    /**
     * 用户操作
     *
     * @see LoginOperationEnum
     */
    private Integer operation;

    /**
     * 登录状态
     *
     * @see LoginStatusEnum
     */
    private Integer status;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作ip
     */
    private String ip;

}
