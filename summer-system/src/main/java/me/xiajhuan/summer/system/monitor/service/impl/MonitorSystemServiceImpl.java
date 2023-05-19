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
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.utils.SystemUtil;
import me.xiajhuan.summer.system.monitor.dto.MonitorSystemDto;
import me.xiajhuan.summer.system.monitor.service.MonitorSystemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 系统 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/13
 */
@Service
public class MonitorSystemServiceImpl implements MonitorSystemService {

    /**
     * 应用名称格式
     */
    private static final String APP_NAME_FORMAT = "{}【{}】";

    /**
     * 项目描述
     */
    @Value("${info.description}")
    private String projectDescription;

    /**
     * 项目LICENSE
     */
    @Value("${info.license}")
    private String projectLicense;

    /**
     * 项目版本
     */
    @Value("${info.version}")
    private String projectVersion;

    @Override
    public MonitorSystemDto info() {
        // 系统总内存
        long totalMemory = SystemUtil.getTotalMemory();
        // 系统剩余内存
        long freeMemory = SystemUtil.getFreeMemory();
        // 系统内存使用率：(系统总内存 - 系统剩余内存) / 系统总内存 * 100
        double memoryRate = BigDecimal.valueOf(NumberUtil.div(totalMemory - freeMemory, totalMemory) * 100.0)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // CPU负载
        double cpuLoad = BigDecimal.valueOf(SystemUtil.getCpuLoad() * 100.0)
                .setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 操作系统信息
        Dict osInfo = SystemUtil.getOsInfo();

        // Java相关信息
        Dict javaInfo = SystemUtil.getJavaInfo();

        // 构建系统信息
        return MonitorSystemDto.builder()
                .appName(StrUtil.format(APP_NAME_FORMAT, projectDescription, projectLicense))
                .appVersion(projectVersion)
                .osName(osInfo.getStr("osName"))
                .osVersion(osInfo.getStr("osVersion"))
                .osArchitecture(osInfo.getStr("osArchitecture"))
                .cpuCoreNum(SystemUtil.getCpuCoreNum())
                .sysTotalMemory(DataSizeUtil.format(totalMemory))
                .sysFreeMemory(DataSizeUtil.format(freeMemory))
                .sysMemoryRate(memoryRate + "%")
                .sysLocale(Locale.getDefault().toString())
                .jvmName(javaInfo.getStr("jvmName"))
                .jvmVersion(javaInfo.getStr("jvmVersion"))
                .javaHome(javaInfo.getStr("javaHome"))
                .workDirectory(javaInfo.getStr("workDirectory"))
                .jvmOccupyMemory(DataSizeUtil.format(javaInfo.getLong("jvmOccupyMemory")))
                .jvmFreeMemory(DataSizeUtil.format(javaInfo.getLong("jvmFreeMemory")))
                .jvmMaxMemory(DataSizeUtil.format(javaInfo.getLong("jvmMaxMemory")))
                .sysCurrentUser(osInfo.getStr("sysCurrentUser"))
                .cpuLoad(cpuLoad + "%")
                .sysTimezone(osInfo.getStr("sysTimezone")).build();
    }

}
