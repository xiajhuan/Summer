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
 * 对象存储支持枚举
 *
 * @author xiajhuan
 * @date 2023/4/28
 */
@Getter
@AllArgsConstructor
public enum OssSupportEnum {

    /**
     * 本地
     */
    LOCAL(0, "本地", "本地服务器存储"),

    /**
     * MinIo
     */
    MINIO(1, "MinIo", "MinIo对象存储"),

    /**
     * Qiniu
     */
    QINIU(2, "七牛云", "七牛云对象存储");

    private final int value;

    private final String name;

    private final String desc;

}