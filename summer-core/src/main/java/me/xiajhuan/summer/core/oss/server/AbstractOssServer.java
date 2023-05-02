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

package me.xiajhuan.summer.core.oss.server;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 对象存储服务
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see MultipartFile
 */
public abstract class AbstractOssServer {

    /**
     * 端点（协议://ip:端口 或 域名）
     */
    protected String endPoint;

    /**
     * 默认逻辑空间名
     */
    protected String defaultBucketName;

    /**
     * 构造AbstractOssServer
     */
    protected AbstractOssServer() {
    }

    /**
     * 上传，使用默认逻辑空间名
     *
     * @param multipartFile {@link MultipartFile}
     * @return {@link Dict}或 {@code null}，{@link Dict} 包含的Key有：<br>
     * 【type（类型）,name（文件名称）,path（路径（相对路径））,url（URL）】
     */
    public Dict upload(MultipartFile multipartFile) {
        return upload(multipartFile, null);
    }

    /**
     * 上传，指定逻辑空间名
     *
     * @param multipartFile {@link MultipartFile}
     * @param bucketName    逻辑空间名
     * @return {@link Dict}或 {@code null}，{@link Dict} 包含的Key有：<br>
     * 【type（类型）,name（文件名称）,path（路径（相对路径））,url（URL）】
     */
    public Dict upload(MultipartFile multipartFile, String bucketName) {
        if (multipartFile != null) {
            // 文件名称
            String name = multipartFile.getOriginalFilename();
            // 路径（相对路径），格式：日期/随机串.后缀
            String path = StrUtil.format("{}/{}.{}",
                    DateUtil.format(DateUtil.date(), "yyyyMMdd"),
                    UUID.fastUUID().toString(true),
                    FileNameUtil.getSuffix(name));
            try {
                // 上传处理
                return Dict.of("type", getType(), "name", name, "path", path, "url",
                        uploadInternal(multipartFile.getInputStream(), bucketName, path));
            } catch (IOException e) {
                throw FileUploadException.of(e);
            }
        }
        return null;
    }

    /**
     * 获取对象存储类型 {@link OssSupportEnum}
     *
     * @return 对象存储类型
     */
    public abstract int getType();

    /**
     * 删除，使用默认逻辑空间名
     *
     * @param path 路径（相对路径）
     */
    public void delete(String path) {
        delete(path, null);
    }

    /**
     * 删除，指定逻辑空间名
     *
     * @param path       路径（相对路径）
     * @param bucketName 逻辑空间名
     */
    public abstract void delete(String path, String bucketName);

    /**
     * 上传处理
     *
     * @param inputStream {@link InputStream}
     * @param bucketName  逻辑空间名
     * @param path        路径（相对路径）
     * @return URL
     */
    protected abstract String uploadInternal(InputStream inputStream, String bucketName, String path);

    /**
     * 获取实际的逻辑空间名
     *
     * @param bucketName 逻辑空间名
     * @return 实际的逻辑空间名
     */
    protected String getRealBucketName(String bucketName) {
        if (bucketName == null) {
            bucketName = defaultBucketName;
        }
        return bucketName;
    }

    /**
     * 获取URL，格式：端点/逻辑空间名/路径（相对路径）
     *
     * @param bucketName 逻辑空间名
     * @param path       路径（相对路径）
     * @return URL
     */
    protected String getUrl(String bucketName, String path) {
        return StrUtil.format("{}/{}/{}", endPoint, bucketName, path);
    }

}
