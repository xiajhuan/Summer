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

package me.xiajhuan.summer.system.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.core.base.entity.SimpleEntity;

import java.util.Date;

/**
 * 在线用户 Entity
 *
 * @author xiajhuan
 * @date 2023/4/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("monitor_online")
public class MonitorOnlineEntity extends SimpleEntity {

    /**
     * 创建者（用户名）<br>
     * note：不使用字段自动填充
     */
    private String createBy;

    /**
     * 创建时间<br>
     * note：不使用字段自动填充
     */
    private Date createTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 本部门名称
     */
    private String deptName;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * Token过期时间
     */
    private Date expireTime;

}
