/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * IP工具
 *
 * @author xiajhuan
 * @date 2022/11/26
 */
public class IpUtil {

    private static final Log LOGGER = LogFactory.get();

    /**
     * unknown IP
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取请求IP
     * <p>
     * 使用Nginx等反向代理软件，则不能通过 {@link ServletRequest#getRemoteAddr()} 获取IP地址，
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非unknown的有效IP字符串，为真实IP地址
     * </p>
     *
     * @param request {@link HttpServletRequest}
     * @return 请求IP
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StrUtil.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            LOGGER.error(e, "IPUtil ERROR【{}】", e.getMessage());
        }

        return ip;
    }

    /**
     * 获取当前服务节点IP
     *
     * @return 当前服务节点IP
     */
    public static String getIpSelf() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error(e, "获取当前服务节点IP失败【{}】", e.getMessage());
        }
        return null;
    }

}
