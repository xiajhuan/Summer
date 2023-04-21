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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.SystemPropsUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 系统工具
 *
 * @author xiajhuan
 * @date 2023/4/19
 * @see OperatingSystemMXBean
 * @see Inet4Address
 * @see SpringUtil
 * @see RuntimeUtil
 * @see SystemPropsUtil
 */
public class SystemUtil {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 构造SystemUtil（不允许实例化）
     */
    private SystemUtil() {
    }

    /**
     * 操作系统管理
     */
    private static final OperatingSystemMXBean osMX = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * 获取IP（IPv4）
     *
     * @return IP（IPv4）或 {@code null}
     */
    public static String getIp() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error(e, "获取IP失败【{}】", e.getMessage());
            return null;
        }
    }

    /**
     * 获取端口（server.port）
     *
     * @return 端口或 {@code null}
     */
    public static String getPort() {
        return SpringUtil.getProperty("server.port");
    }

    /**
     * 获取应用名称（spring.application.name）
     *
     * @return 应用名称或 {@code null}
     */
    public static String getApplicationName() {
        return SpringUtil.getApplicationName();
    }

    /**
     * 获取环境（spring.profiles.active）<br>
     * note：多个环境只取第一个
     *
     * @return 环境或 {@code null}
     */
    public static String getProfile() {
        return SpringUtil.getActiveProfile();
    }

    /**
     * 获取CPU核心数<br>
     * note：如果获取不到则返回 7，真实的CPU基本都是偶数，方便区分
     *
     * @return CPU核心数或 {@code 7}
     */
    public static int getCpuCoreNum() {
        return RuntimeUtil.getProcessorCount();
    }

    /**
     * 获取CPU负载（0-1）
     *
     * @return CPU负载（0-1）
     */
    public static double getCpuLoad() {
        return osMX.getSystemCpuLoad();
    }

    /**
     * 获取总内存（B）
     *
     * @return 总内存（B）
     */
    public static long getTotalMemory() {
        return osMX.getTotalPhysicalMemorySize();
    }

    /**
     * 获取剩余内存（B）
     *
     * @return 剩余内存（B）
     */
    public static long getFreeMemory() {
        return osMX.getFreePhysicalMemorySize();
    }

    /**
     * 获取操作系统信息
     *
     * @return {@link Dict}
     */
    public static Dict getOsInfo() {
        return Dict.of(
                "osName", SystemPropsUtil.get("os.name"),
                "osVersion", SystemPropsUtil.get("os.version"),
                "osArchitecture", SystemPropsUtil.get("os.arch"),
                "sysCurrentUser", SystemPropsUtil.get("user.name"),
                "sysTimezone", SystemPropsUtil.get("user.timezone")
        );
    }

    /**
     * 获取Java相关信息
     *
     * @return {@link Dict}
     */
    public static Dict getJavaInfo() {
        return Dict.of(
                "jvmName", SystemPropsUtil.get("java.vm.name"),
                "jvmVersion", SystemPropsUtil.get("java.version"),
                "jvmOccupyMemory", RuntimeUtil.getTotalMemory(),
                "jvmFreeMemory", RuntimeUtil.getFreeMemory(),
                "jvmMaxMemory", RuntimeUtil.getMaxMemory(),
                "javaHome", SystemPropsUtil.get("java.home"),
                "workDirectory", SystemPropsUtil.get("user.dir")
        );
    }

}
