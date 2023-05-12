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

package me.xiajhuan.summer.core.oss.server.subClass;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
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
        privateBucket = setting.getByGroupWithLog("min-io.private-bucket", "Oss");
        publicBucket = setting.getByGroupWithLog("min-io.public-bucket", "Oss");
        validateConfig(true);

        // 构建客户端
        minioClient = MinioClient.builder().endpoint(endPoint)
                .credentials(setting.getByGroupWithLog("min-io.access-key", "Oss"),
                        setting.getByGroupWithLog("min-io.secret-key", "Oss")).build();
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
    protected String getSupportType() {
        return OssSupportEnum.MIN_IO.getValue();
    }

    @Override
    protected String uploadInternal(InputStream inputStream, String path, boolean isPrivate) {
        // 存储文件
        if (isPrivate) {
            minIoStore(privateBucket, path, inputStream);
            return StrUtil.EMPTY;
        } else {
            minIoStore(publicBucket, path, inputStream);
            // URL（外链）
            return minIoUrl(path);
        }
    }

    @Override
    protected String getDownloadUrl(String path, boolean isPrivate) {
        try {
            return isPrivate ?
                    // URL（带签名）
                    minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .bucket(privateBucket)
                            .object(path)
                            .method(Method.GET).build()) :
                    // URL（外链）
                    minIoUrl(path);
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw FileDownloadException.of(e);
        }
    }

    @Override
    public void deleteInternal(String path, boolean isPrivate) {
        // 删除文件
        if (isPrivate) {
            minIoDelete(privateBucket, path);
        } else {
            minIoDelete(publicBucket, path);
        }
    }

    /**
     * MinIo删除
     *
     * @param bucket 空间
     * @param path   路径（相对路径）
     */
    private void minIoDelete(String bucket, String path) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(path).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
        }
    }

    /**
     * MinIo存储
     *
     * @param bucket      空间
     * @param path        路径（相对路径）
     * @param inputStream {@link InputStream}
     */
    private void minIoStore(String bucket, String path, InputStream inputStream) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(path)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                | InvalidKeyException | InvalidResponseException | IOException
                | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            throw FileUploadException.of(e);
        }
    }

    /**
     * MinIo URL
     *
     * @param path 路径（相对路径）
     * @return MinIo URL
     */
    private String minIoUrl(String path) {
        return StrUtil.format("{}/{}/{}", endPoint, publicBucket, path);
    }

}
