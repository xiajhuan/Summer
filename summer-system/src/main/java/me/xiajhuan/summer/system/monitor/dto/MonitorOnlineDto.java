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

package me.xiajhuan.summer.system.monitor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.PageSortDto;

import java.util.Date;

/**
 * 在线用户 Dto
 *
 * @author xiajhuan
 * @date 2023/4/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MonitorOnlineDto extends PageSortDto {

    /**
     * 创建者（用户名）
     */
    private String createBy;

    /**
     * 创建时间
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
