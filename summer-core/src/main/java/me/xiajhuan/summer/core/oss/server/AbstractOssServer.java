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
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.core.enums.BucketTypeEnum;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.utils.AssertUtil;
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
 * @see FileUtil
 * @see HttpUtil
 * @see IoUtil
 */
public abstract class AbstractOssServer {

    protected static final Log LOGGER = LogFactory.get();

    /**
     * 端点（协议://ip:端口或域名）
     */
    protected String endPoint;

    /**
     * 私有空间
     */
    protected String privateBucket;

    /**
     * 公有空间
     */
    protected String publicBucket;

    /**
     * 构造保护化
     */
    protected AbstractOssServer() {
    }

    /**
     * 上传
     *
     * @param multipartFile {@link MultipartFile}
     * @param isPrivate     是否私有，true：是 false：否
     * @return {@link Dict}或{@code null}，{@link Dict}包含的Key有：
     * <ul>
     *   <li>name（文件名称）</li>
     *   <li>url（URL（外链，私有时值为空串））</li>
     *   <li>path（路径（相对路径））</li>
     *   <li>bucketType（空间类型，参考{@link BucketTypeEnum}）</li>
     *   <li>supportType（支持类型，参考{@link OssSupportEnum}）</li>
     * </ul>
     */
    public Dict upload(MultipartFile multipartFile, boolean isPrivate) {
        if (multipartFile != null) {
            // 文件名称
            String name = multipartFile.getOriginalFilename();
            if (multipartFile.isEmpty()) {
                // 空文件
                LOGGER.warn("不允许上传空文件【{}】", name);
                return null;
            }
            // 后缀（不包括”.“）
            String suffix = FileNameUtil.getSuffix(name);
            // 路径（相对路径），格式：日期/后缀/随机串.后缀
            String path = StrUtil.format("{}/{}/{}.{}",
                    DateUtil.format(DateUtil.date(), "yyyyMMdd"),
                    suffix,
                    UUID.fastUUID().toString(true),
                    suffix);
            try {
                // 上传处理
                return Dict.of("name", name, "path", path,
                        "bucketType", isPrivate ? BucketTypeEnum.PRIVATE.getValue() : BucketTypeEnum.PUBLIC.getValue(),
                        "supportType", getSupportType(),
                        "url", uploadInternal(multipartFile.getInputStream(), path, isPrivate));
            } catch (IOException e) {
                throw FileUploadException.of(e);
            }
        }
        return null;
    }

    /**
     * 下载
     *
     * @param path      路径（相对路径）
     * @param fileName  文件名称
     * @param isPrivate 是否私有，true：是 false：否
     * @param response  {@link HttpServletResponse}
     */
    public void download(String path, String fileName, boolean isPrivate, HttpServletResponse response) {
        // byte数组（文件内容）
        final byte[] data;
        // 下载URL，note：本地服务器存储时为绝对路径
        String url = getDownloadUrl(path, isPrivate);
        try {
            // 下载处理
            if (OssSupportEnum.LOCAL.getValue().equals(getSupportType())) {
                // 本地下载
                data = FileUtil.readBytes(url);
            } else {
                // 远程下载
                data = HttpUtil.downloadBytes(url);
            }
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
     * @param path      路径（相对路径）
     * @param isPrivate 是否私有，true：是 false：否
     */
    public abstract void delete(String path, boolean isPrivate);

    /**
     * 获取支持类型，参考{@link OssSupportEnum}
     *
     * @return 支持类型
     */
    protected abstract String getSupportType();

    /**
     * 上传处理
     *
     * @param inputStream {@link InputStream}
     * @param path        路径（相对路径）
     * @param isPrivate   是否私有，true：是 false：否
     * @return URL（外链）
     */
    protected abstract String uploadInternal(InputStream inputStream, String path, boolean isPrivate);

    /**
     * 获取下载URL
     *
     * @param path      路径（相对路径）
     * @param isPrivate 是否私有，true：是 false：否
     * @return 下载URL
     */
    protected abstract String getDownloadUrl(String path, boolean isPrivate);

    /**
     * 校验配置
     *
     * @param needEndPoint 是否需要端点，true：是 false：否
     */
    protected void validateConfig(boolean needEndPoint) {
        if (needEndPoint) {
            AssertUtil.isNotBlank("endPoint", endPoint);
        }
        AssertUtil.isNotBlank("privateBucket", privateBucket);
        AssertUtil.isNotBlank("publicBucket", publicBucket);
    }

}
