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
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.oss.server.OssServer;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MinIo对象存储
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see MinioClient
 */
public class MinIoOssServer implements OssServer {

    /**
     * endPoint（协议://IP:端口）
     */
    private final String minIoEndPoint;

    /**
     * bucketName（逻辑空间名）
     */
    private final String minIoBucketName;

    /**
     * 路径前缀
     */
    private final String minIoPrefix;

    /**
     * 客户端
     */
    private final MinioClient minioClient;

    //*******************单例处理开始********************

    private MinIoOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        minIoEndPoint = setting.getByGroupWithLog("min-io.end-point", "Oss");
        // 构建客户端
        minioClient = MinioClient.builder().endpoint(minIoEndPoint)
                .credentials(setting.getByGroupWithLog("min-io.access-key", "Oss"),
                        setting.getByGroupWithLog("min-io.secret-key", "Oss")).build();

        minIoBucketName = setting.getByGroupWithLog("min-io.bucket-mame", "Oss");
        minIoPrefix = setting.getByGroupWithLog("min-io.prefix", "Oss");
    }

    private static volatile MinIoOssServer instance = null;

    public static MinIoOssServer getInstance() {
        if (instance == null) {
            synchronized (MinIoOssServer.class) {
                if (instance == null) {
                    instance = new MinIoOssServer();
                }
            }
        }
        return instance;
    }

    //*******************单例处理结束********************

    @Override
    public String upload(InputStream inputStream, String suffix) {
        return uploadInternal(inputStream, getPath(minIoPrefix, suffix));
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
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minIoBucketName).build())) {
                // 逻辑空间不存在则创建
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minIoBucketName).build());
            }

            // 存储文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minIoBucketName)
                    .object(path)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType("application/octet-stream")
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw FileUploadException.of(e);
        }

        // 存储的URL
        return StrUtil.format("{}/{}/{}", minIoEndPoint, minIoBucketName, path);
    }

}
