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
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.utils.AssertUtil;

import java.io.InputStream;

/**
 * 七牛云对象存储
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see Auth
 * @see UploadManager
 * @see BucketManager
 */
public class QiNiuOssServer extends AbstractOssServer {

    /**
     * 绑定域名（私有空间）
     */
    private final String privateDomain;

    /**
     * 绑定域名（公有空间）
     */
    private final String publicDomain;

    /**
     * 凭证
     */
    private final Auth auth;

    /**
     * 上传管理器
     */
    private final UploadManager uploadManager;

    /**
     * 空间管理器
     */
    private final BucketManager bucketManager;

    //*******************单例处理开始********************

    private QiNiuOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);

        privateDomain = setting.getByGroupWithLog("qi-niu.private-domain", "Oss");
        AssertUtil.isNotBlank("privateDomain", privateDomain);
        publicDomain = setting.getByGroupWithLog("qi-niu.public-domain", "Oss");
        AssertUtil.isNotBlank("publicDomain", publicDomain);

        privateBucket = setting.getByGroupWithLog("qi-niu.private-bucket", "Oss");
        publicBucket = setting.getByGroupWithLog("qi-niu.public-bucket", "Oss");
        validateConfig(false);

        // 凭证
        auth = Auth.create(setting.getByGroupWithLog("qi-niu.access-key", "Oss"),
                setting.getByGroupWithLog("qi-niu.secret-key", "Oss"));

        // 配置
        Configuration configuration = new Configuration(Region.autoRegion());
        uploadManager = new UploadManager(configuration);
        bucketManager = new BucketManager(auth, configuration);
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
    protected String getSupportType() {
        return OssSupportEnum.QI_NIU.getValue();
    }

    @Override
    protected String uploadInternal(InputStream inputStream, String path, boolean isPrivate) {
        // 存储文件
        if (isPrivate) {
            qiNiuStore(privateBucket, path, inputStream);
            return StrUtil.EMPTY;
        } else {
            qiNiuStore(publicBucket, path, inputStream);
            // URL（外链）
            return qiNiuUrl(publicDomain, path);
        }
    }

    @Override
    protected String getDownloadUrl(String path, boolean isPrivate) {
        return isPrivate ?
                // URL（带签名）
                auth.privateDownloadUrl(qiNiuUrl(privateDomain, path)) :
                // URL（外链）
                qiNiuUrl(publicDomain, path);
    }

    @Override
    public void deleteInternal(String path, boolean isPrivate) {
        // 删除文件
        if (isPrivate) {
            qiNiuDelete(privateBucket, path);
        } else {
            qiNiuDelete(publicBucket, path);
        }
    }

    /**
     * 七牛云删除
     *
     * @param bucket 空间
     * @param path   路径（相对路径）
     */
    private void qiNiuDelete(String bucket, String path) {
        try {
            Response response = bucketManager.delete(bucket, path);
            if (!response.isOK()) {
                throw SystemException.of(ErrorCode.FILE_DELETE_FAILURE, response.error);
            }
        } catch (QiniuException e) {
            throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
        }
    }

    /**
     * 七牛云存储
     *
     * @param bucket      空间
     * @param path        路径（相对路径）
     * @param inputStream {@link InputStream}
     */
    private void qiNiuStore(String bucket, String path, InputStream inputStream) {
        try {
            Response response = uploadManager.put(inputStream, path,
                    auth.uploadToken(bucket), null, null);
            if (!response.isOK()) {
                throw FileUploadException.of(StrUtil.format("文件上传失败【{}】", response.error));
            }
        } catch (QiniuException e) {
            throw FileUploadException.of(e);
        }
    }

    /**
     * 七牛云 URL
     *
     * @param domain 绑定域名
     * @param path   路径（相对路径）
     * @return 七牛云 URL
     */
    private String qiNiuUrl(String domain, String path) {
        return StrUtil.format("{}/{}", domain, path);
    }

}
