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
     * 凭证
     */
    private final Auth auth;

    /**
     * 上传管理器
     */
    private final UploadManager uploadManager;

    /**
     * 逻辑空间管理器
     */
    private final BucketManager bucketManager;

    //*******************单例处理开始********************

    private QiNiuOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        endPoint = setting.getByGroupWithLog("qi-niu.end-point", "Oss");
        AssertUtil.isNotBlank("endPoint", endPoint);

        // 凭证
        auth = Auth.create(setting.getByGroupWithLog("qi-niu.access-key", "Oss"),
                setting.getByGroupWithLog("qi-niu.secret-key", "Oss"));

        // 配置
        Configuration configuration = new Configuration(Region.autoRegion());
        uploadManager = new UploadManager(configuration);
        bucketManager = new BucketManager(auth, configuration);

        defaultBucketName = setting.getByGroupWithLog("qi-niu.default-bucket-mame", "Oss");
        if (StrUtil.isBlank(defaultBucketName)) {
            // 没有配置则默认为：files
            defaultBucketName = "files";
        }
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
    public int getType() {
        return OssSupportEnum.QI_NIU.getValue();
    }

    @Override
    protected String uploadInternal(InputStream inputStream, String bucketName, String path) {
        try {
            // 存储文件
            Response response = uploadManager.put(inputStream, path,
                    auth.uploadToken(bucketName), null, null);
            if (!response.isOK()) {
                throw FileUploadException.of(StrUtil.format("文件上传失败【{}】", response.error));
            }
        } catch (QiniuException e) {
            throw FileUploadException.of(e);
        }

        // URL（外链）
        return StrUtil.format("{}/{}", endPoint, path);
    }

    @Override
    public void deleteInternal(String path, String bucketName) {
        try {
            // 删除文件
            Response response = bucketManager.delete(bucketName, path);
            if (!response.isOK()) {
                throw SystemException.of(ErrorCode.FILE_DELETE_FAILURE, response.error);
            }
        } catch (QiniuException e) {
            throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
        }
    }

}
