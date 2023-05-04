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
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.utils.AssertUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 本地服务器存储
 *
 * @author xiajhuan
 * @date 2023/4/29
 * @see FileUtil
 */
public class LocalOssServer extends AbstractOssServer {

    /**
     * 存储目录
     */
    private final String storageDirectory;

    //*******************单例处理开始********************

    private LocalOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);
        endPoint = setting.getByGroupWithLog("local.end-point", "Oss");
        AssertUtil.isNotBlank("endPoint", endPoint);

        storageDirectory = setting.getByGroupWithLog("local.storage-directory", "Oss");
        AssertUtil.isNotBlank("storageDirectory", storageDirectory);

        defaultBucketName = setting.getByGroupWithLog("local.default-bucket-name", "Oss");
        if (StrUtil.isBlank(defaultBucketName)) {
            // 没有配置则默认为：summer-files
            defaultBucketName = "summer-files";
        }
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
    public int getType() {
        return OssSupportEnum.LOCAL.getValue();
    }

    @Override
    protected String uploadInternal(InputStream inputStream, String bucketName, String path) {
        try {
            // 将流的内容写入文件（自动关闭输入流）
            FileUtil.writeFromStream(inputStream, getFile(bucketName, path));
        } catch (IORuntimeException e) {
            throw FileUploadException.of(e);
        }

        // URL（外链）
        return StrUtil.format("{}/{}/{}", endPoint, bucketName, path);
    }

    @Override
    protected void deleteInternal(String bucketName, String path) {
        try {
            // 删除文件
            if (!FileUtil.del(getFile(bucketName, path))) {
                throw SystemException.of(ErrorCode.FILE_DELETE_FAILURE, StrUtil.EMPTY);
            }
        } catch (IORuntimeException e) {
            throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
        }
    }

    /**
     * 获取{@link File}
     *
     * @param bucketName 逻辑空间名
     * @param path       路径（相对路径）
     * @return {@link File}
     */
    private File getFile(String bucketName, String path) {
        return new File(storageDirectory + File.separator
                + bucketName + File.separator
                + path);
    }

}
