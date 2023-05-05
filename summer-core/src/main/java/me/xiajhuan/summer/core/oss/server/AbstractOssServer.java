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
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * 对象存储服务
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see MultipartFile
 */
public abstract class AbstractOssServer {

    /**
     * 端点（协议://ip:端口或域名）
     */
    protected String endPoint;

    /**
     * 默认逻辑空间名
     */
    protected String defaultBucketName;

    /**
     * 构造保护化
     */
    protected AbstractOssServer() {
    }

    /**
     * 上传
     *
     * @param multipartFile {@link MultipartFile}
     * @param bucketName    逻辑空间名
     * @return {@link Dict}或{@code null}，{@link Dict}包含的Key有：<br>
     * 【type（类型）,name（文件名称）,bucketName（逻辑空间名）,path（路径（相对路径））,url（URL（外链））】
     */
    public Dict upload(MultipartFile multipartFile, String bucketName) {
        if (multipartFile != null) {
            // 文件名称
            String name = multipartFile.getOriginalFilename();
            // 逻辑空间名
            bucketName = getRealBucketName(bucketName);
            // 路径（相对路径），格式：日期/随机串.后缀
            String path = StrUtil.format("{}/{}.{}",
                    DateUtil.format(DateUtil.date(), "yyyyMMdd"),
                    UUID.fastUUID().toString(true),
                    FileNameUtil.getSuffix(name));
            try {
                // 上传处理
                return Dict.of("type", getType(), "name", name,
                        "bucketName", bucketName, "path", path,
                        "url", uploadInternal(multipartFile.getInputStream(), bucketName, path));
            } catch (IOException e) {
                throw FileUploadException.of(e);
            }
        }
        return null;
    }

    /**
     * 下载
     *
     * @param bucketName 逻辑空间名
     * @param path       路径（相对路径）
     * @param fileName   文件名称
     * @param response   {@link HttpServletResponse}
     */
    public void download(String bucketName, String path, String fileName, HttpServletResponse response) {
        try {
            // 请求下载URL
            byte[] data = HttpUtil.downloadBytes(
                    getDownloadUrl(getRealBucketName(bucketName), path));
            // 设置响应头
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    StrUtil.format("attachment;filename={}",
                            URLEncoder.encode(fileName, "UTF-8")));

            // 将byte数组写入流（自动关闭输出流）
            IoUtil.write(response.getOutputStream(), true, data);
        } catch (Exception e) {
            throw FileDownloadException.of(e);
        }
    }

    /**
     * 删除
     *
     * @param bucketName 逻辑空间名
     * @param path       路径（相对路径）
     */
    public void delete(String bucketName, String path) {
        // 删除处理
        deleteInternal(getRealBucketName(bucketName), path);
    }

    /**
     * 获取对象存储类型，参考{@link OssSupportEnum}
     *
     * @return 对象存储类型
     */
    protected abstract String getType();

    /**
     * 上传处理
     *
     * @param inputStream {@link InputStream}
     * @param bucketName  逻辑空间名
     * @param path        路径（相对路径）
     * @return URL（外链）
     */
    protected abstract String uploadInternal(InputStream inputStream, String bucketName, String path);

    /**
     * 获取下载URL
     *
     * @param bucketName 逻辑空间名
     * @param path       路径（相对路径）
     * @return 下载URL
     */
    protected abstract String getDownloadUrl(String bucketName, String path);

    /**
     * 删除处理
     *
     * @param bucketName 逻辑空间名
     * @param path       路径（相对路径）
     */
    protected abstract void deleteInternal(String bucketName, String path);

    /**
     * 获取实际逻辑空间名
     *
     * @param bucketName 逻辑空间名
     * @return 实际逻辑空间名
     */
    protected String getRealBucketName(String bucketName) {
        if (bucketName == null) {
            bucketName = defaultBucketName;
        }
        return bucketName;
    }

}
