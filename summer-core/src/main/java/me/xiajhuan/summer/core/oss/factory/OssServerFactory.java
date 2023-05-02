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

package me.xiajhuan.summer.core.oss.factory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.oss.server.impl.LocalOssServer;
import me.xiajhuan.summer.core.oss.server.impl.MinIoOssServer;
import me.xiajhuan.summer.core.oss.server.impl.QiNiuOssServer;
import me.xiajhuan.summer.core.properties.ApplicationProperties;

/**
 * 对象存储服务工厂
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
public class OssServerFactory {

    /**
     * 构造OssServerFactory（不允许实例化）
     */
    private OssServerFactory() {
    }

    /**
     * 对象存储类型
     */
    private static String OSS_TYPE;

    /**
     * 初始化 {@link OSS_TYPE}
     */
    static {
        OSS_TYPE = SpringUtil.getBean("applicationProperties", ApplicationProperties.class)
                .getOss().getType();
        if (StrUtil.isBlank(OSS_TYPE)) {
            // 没有配置则默认为：LOCAL
            OSS_TYPE = OssSupportEnum.LOCAL.toString();
        }
    }

    /**
     * 获取对象存储服务
     *
     * @return 对象存储服务
     */
    public static AbstractOssServer getOssServer() {
        if (OSS_TYPE.equalsIgnoreCase(OssSupportEnum.LOCAL.toString())) {
            return LocalOssServer.getInstance();
        } else if (OSS_TYPE.equalsIgnoreCase(OssSupportEnum.MIN_IO.toString())) {
            return MinIoOssServer.getInstance();
        } else if (OSS_TYPE.equalsIgnoreCase(OssSupportEnum.QI_NIU.toString())) {
            return QiNiuOssServer.getInstance();
        } else {
            throw new IllegalArgumentException(StrUtil.format("不支持的对象存储类型【{}】", OSS_TYPE));
        }
    }

    /**
     * 获取本地存储服务，note：
     * <pre>
     *   1.当对象存储类型为“MIN_IO”或“QI_NIU”时，若有分级存储需求，可通过该方法获取本地存储服务
     *   2.当对象存储类型为“LOCAL”时，推荐使用 {@link OssServerFactory#getOssServer()}，
     *     这将便于在对象存储类型切换时保证代码通用
     * </pre>
     *
     * @return 本地存储服务
     */
    public static LocalOssServer getLocalOssServer() {
        return LocalOssServer.getInstance();
    }

    /**
     * 获取MinIo对象存储服务，note：
     * <pre>
     *   1.当对象存储类型为“MIN_IO”时，若有分级存储需求，可以通过该方法获取MinIo对象存储服务
     *   2.当没有分级存储需求时，推荐使用 {@link OssServerFactory#getOssServer()}，
     *     这将便于在对象存储类型切换时保证代码通用
     *   3.当对象存储类型不为“MIN_IO”时将抛出 {@link UnsupportedOperationException}
     * </pre>
     *
     * @return MinIo对象存储服务
     */
    public static MinIoOssServer getMinIoOssServer() {
        if (OSS_TYPE.equalsIgnoreCase(OssSupportEnum.MIN_IO.toString())) {
            return MinIoOssServer.getInstance();
        }
        throw new UnsupportedOperationException("对象存储类型必须为MIN_IO才能调用!");
    }

    /**
     * 获取七牛云对象存储服务，note：
     * <pre>
     *   1.当对象存储类型为“QI_NIU”时，若有分级存储需求，可以通过该方法获取七牛云对象存储服务
     *   2.当没有分级存储需求时，推荐使用 {@link OssServerFactory#getOssServer()}，
     *     这将便于在对象存储类型切换时保证代码通用
     *   3.当对象存储类型不为“QI_NIU”时将抛出 {@link UnsupportedOperationException}
     * </pre>
     *
     * @return 七牛云对象存储服务
     */
    public static QiNiuOssServer getQiNiuOssServer() {
        if (OSS_TYPE.equalsIgnoreCase(OssSupportEnum.QI_NIU.toString())) {
            return QiNiuOssServer.getInstance();
        }
        throw new UnsupportedOperationException("对象存储类型必须为QI_NIU才能调用!");
    }

}
