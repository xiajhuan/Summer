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

package me.xiajhuan.summer.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 非登录用户枚举
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Getter
@AllArgsConstructor
public enum NonLoggedUserEnum {

    /**
     * 系统用户
     */
    SYSTEM_USER("systemUser", "系统用户"),

    /**
     * 定时任务
     */
    QUARTZ_TASK("quartzTask", "定时任务"),

    /**
     * 第三方用户
     */
    THIRD_PART("thirdPart", "第三方用户");

    private final String value;

    private final String name;

}
