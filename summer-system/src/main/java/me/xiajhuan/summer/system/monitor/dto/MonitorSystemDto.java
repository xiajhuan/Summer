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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统 Dto
 *
 * @author xiajhuan
 * @date 2023/4/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorSystemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用版本
     */
    private String appVersion;

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 操作系统架构
     */
    private String osArchitecture;

    /**
     * CPU核数
     */
    private Integer cpuCoreNum;

    /**
     * 系统内存（MB）
     */
    private String sysTotalMemory;

    /**
     * 系统剩余内存（MB）
     */
    private String sysFreeMemory;

    /**
     * 内存使用率（%），保留2位小数
     */
    private String sysMemoryRate;

    /**
     * 系统地区语言
     */
    private String sysLocale;

    /**
     * JVM名称
     */
    private String jvmName;

    /**
     * JVM版本
     */
    private String jvmVersion;

    /**
     * JAVA_HOME
     */
    private String javaHome;

    /**
     * 工作目录
     */
    private String workDirectory;

    /**
     * JVM占用内存（MB）
     */
    private String jvmOccupyMemory;

    /**
     * JVM空闲内存（MB）
     */
    private String jvmFreeMemory;

    /**
     * JVM最大内存（MB），-Xmx
     */
    private String jvmMaxMemory;

    /**
     * 当前系统用户
     */
    private String sysCurrentUser;

    /**
     * CPU负载（%），保留1位小数
     */
    private String cpuLoad;

    /**
     * 系统时区
     */
    private String sysTimezone;

}
