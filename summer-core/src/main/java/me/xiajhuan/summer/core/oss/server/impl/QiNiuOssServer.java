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

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.oss.server.OssServer;

import java.io.InputStream;

/**
 * 七牛云对象存储
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see Auth
 * @see UploadManager
 */
public class QiNiuOssServer implements OssServer {

    /**
     * 绑定域名
     */
    private final String qiNiuDomain;

    /**
     * 路径前缀
     */
    private final String qiuNiuPrefix;

    /**
     * Token
     */
    private final String token;

    /**
     * 文件上传管理器
     */
    private final UploadManager uploadManager;

    //*******************单例处理开始********************

    private QiNiuOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        token = Auth.create(setting.getByGroupWithLog("qi-niu.access-key", "Oss"),
                setting.getByGroupWithLog("qi-niu.secret-key", "Oss"))
                .uploadToken(setting.getByGroupWithLog("qi-niu.bucket-mame", "Oss"));

        qiNiuDomain = setting.getByGroupWithLog("qi-niu.domain", "Oss");
        qiuNiuPrefix = setting.getByGroupWithLog("qi-niu.prefix", "Oss");

        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
    }

    private static volatile QiNiuOssServer instance = null;

    public static QiNiuOssServer getInstance() {
        if (instance == null) {
            synchronized (QiNiuOssServer.class) {
                if (instance == null) {
                    instance = new QiNiuOssServer();
                }
            }
        }
        return instance;
    }

    //*******************单例处理结束********************

    @Override
    public String upload(InputStream inputStream, String suffix) {
        return uploadInternal(inputStream, getPath(qiuNiuPrefix, suffix));
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
            // 存储文件
            Response response = uploadManager.put(inputStream, path, token, null, null);
            if (!response.isOK()) {
                throw FileUploadException.of(StrUtil.format("文件上传失败【{}】", response.error));
            }
        } catch (QiniuException e) {
            throw FileUploadException.of(e);
        }

        // 存储的URL
        return StrUtil.format("{}/{}", qiNiuDomain, path);
    }

}
