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

package me.xiajhuan.summer.core.oss.server.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.oss.server.OssServer;

import java.io.File;
import java.io.InputStream;

/**
 * 本地服务器存储
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see FileUtil
 */
public class LocalOssServer implements OssServer {

    /**
     * endPoint（协议://IP:端口）
     */
    private final String localEndPoint;

    /**
     * 路径前缀
     */
    private final String localPrefix;

    /**
     * 存储目录
     */
    private final String localPath;

    //*******************单例处理开始********************

    private LocalOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        localEndPoint = setting.getByGroupWithLog("local.end-point", "Oss");
        localPrefix = setting.getByGroupWithLog("local.prefix", "Oss");
        localPath = setting.getByGroupWithLog("local.path", "Oss");
    }

    private static volatile LocalOssServer instance = null;

    public static LocalOssServer getInstance() {
        if (instance == null) {
            synchronized (LocalOssServer.class) {
                if (instance == null) {
                    instance = new LocalOssServer();
                }
            }
        }
        return instance;
    }

    //*******************单例处理结束********************

    @Override
    public String upload(InputStream inputStream, String suffix) {
        return uploadInternal(inputStream, getPath(localPrefix, suffix));
    }

    /**
     * 上传处理
     *
     * @param inputStream {@link InputStream}
     * @param path        上传路径
     * @return 存储的URL
     */
    private String uploadInternal(InputStream inputStream, String path) {
        try {
            // 将流的内容写入到文件（自动关闭输入流）
            FileUtil.writeFromStream(inputStream,
                    new File(localPath + File.separator + path));
        } catch (IORuntimeException e) {
            throw FileUploadException.of(e);
        }

        // 存储的URL
        return StrUtil.format("{}/{}", localEndPoint, path);
    }

}
