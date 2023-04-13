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

package me.xiajhuan.summer.system.monitor.service.impl;

import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.SystemPropsUtil;
import com.sun.management.OperatingSystemMXBean;
import me.xiajhuan.summer.system.monitor.dto.MonitorSystemDto;
import me.xiajhuan.summer.system.monitor.service.MonitorSystemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * 系统 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/13
 * @see OperatingSystemMXBean
 * @see SystemPropsUtil
 * @see RuntimeUtil
 */
@Service
public class MonitorSystemServiceImpl implements MonitorSystemService {

    /**
     * 应用名称格式
     */
    private static final String appNameFormat = "{}【{}】";

    /**
     * 项目描述
     */
    @Value("${project.description}")
    private String projectDescription;

    /**
     * 项目LICENSE
     */
    @Value("${project.license}")
    private String projectLicense;

    /**
     * 项目版本
     */
    @Value("${project.version}")
    private String projectVersion;

    @Override
    public MonitorSystemDto info() {
        // 操作系统管理
        OperatingSystemMXBean osMX = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 系统内存
        long totalMemory = osMX.getTotalPhysicalMemorySize();
        // 系统剩余内存
        long freeMemory = osMX.getFreePhysicalMemorySize();
        // 内存使用率：(系统内存 - 系统剩余内存) / 系统内存 * 100
        double memoryRate = BigDecimal.valueOf(NumberUtil.div(totalMemory - freeMemory, totalMemory) * 100.0)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // CPU负载
        double cpuLoad = BigDecimal.valueOf(osMX.getSystemCpuLoad() * 100.0)
                .setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 构建系统信息
        MonitorSystemDto dto = MonitorSystemDto.builder()
                .appName(StrUtil.format(appNameFormat, projectDescription, projectLicense))
                .appVersion(projectVersion)
                .osName(SystemPropsUtil.get("os.name"))
                .osVersion(SystemPropsUtil.get("os.version"))
                .osArchitecture(SystemPropsUtil.get("os.arch"))
                .cpuCoreNum(RuntimeUtil.getProcessorCount())
                .sysTotalMemory(DataSizeUtil.format(totalMemory))
                .sysFreeMemory(DataSizeUtil.format(freeMemory))
                .sysMemoryRate(memoryRate + "%")
                .sysLocale(Locale.getDefault().toString())
                .jvmName(SystemPropsUtil.get("java.vm.name"))
                .jvmVersion(SystemPropsUtil.get("java.version"))
                .javaHome(SystemPropsUtil.get("java.home"))
                .workDirectory(SystemPropsUtil.get("user.dir"))
                .jvmOccupyMemory(DataSizeUtil.format(RuntimeUtil.getTotalMemory()))
                .jvmFreeMemory(DataSizeUtil.format(RuntimeUtil.getFreeMemory()))
                .jvmMaxMemory(DataSizeUtil.format(RuntimeUtil.getMaxMemory()))
                .sysCurrentUser(SystemPropsUtil.get("user.name"))
                .cpuLoad(cpuLoad + "%")
                .sysTimezone(SystemPropsUtil.get("user.timezone")).build();

        return dto;
    }

}
