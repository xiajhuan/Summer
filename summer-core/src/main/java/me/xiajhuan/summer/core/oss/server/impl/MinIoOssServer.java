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
import io.minio.*;
import io.minio.errors.*;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.utils.AssertUtil;
import org.springframework.http.MediaType;

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
public class MinIoOssServer extends AbstractOssServer {

    /**
     * 客户端
     */
    private final MinioClient minioClient;

    //*******************单例处理开始********************

    private MinIoOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        endPoint = setting.getByGroupWithLog("min-io.end-point", "Oss");
        AssertUtil.isNotBlank("endPoint", endPoint);

        // 构建客户端
        minioClient = MinioClient.builder().endpoint(endPoint)
                .credentials(setting.getByGroupWithLog("min-io.access-key", "Oss"),
                        setting.getByGroupWithLog("min-io.secret-key", "Oss")).build();

        defaultBucketName = setting.getByGroupWithLog("min-io.default-bucket-mame", "Oss");
        if (StrUtil.isBlank(defaultBucketName)) {
            // 没有配置则默认为：summer-files
            defaultBucketName = "summer-files";
        }
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
    protected String getType() {
        return OssSupportEnum.MIN_IO.getValue();
    }

    @Override
    protected String uploadInternal(InputStream inputStream, String bucketName, String path) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                // 逻辑空间不存在则创建
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            // 存储文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw FileUploadException.of(e);
        }

        // URL（外链）
        return StrUtil.format("{}/{}/{}", endPoint, bucketName, path);
    }

    @Override
    protected void deleteInternal(String bucketName, String path) {
        try {
            // 删除文件
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
        }
    }

    @Override
    protected String handleUrl(String url) {
        return url;
    }

}
