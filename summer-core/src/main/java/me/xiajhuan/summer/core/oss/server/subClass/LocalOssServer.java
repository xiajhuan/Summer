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
     * 私有存储位置
     */
    private final String privateLocation;

    /**
     * 公有存储位置
     */
    private final String publicLocation;

    //*******************单例处理开始********************

    private LocalOssServer() {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);

        privateLocation = setting.getByGroupWithLog("local.private-location", "Oss");
        AssertUtil.isNotBlank("privateLocation", privateLocation);
        publicLocation = setting.getByGroupWithLog("local.public-location", "Oss");
        AssertUtil.isNotBlank("publicLocation", publicLocation);

        endPoint = setting.getByGroupWithLog("local.public-end-point", "Oss");
        privateBucket = setting.getByGroupWithLog("local.private-bucket", "Oss");
        publicBucket = setting.getByGroupWithLog("local.public-bucket", "Oss");
        validateConfig(false);
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
    protected String getSupportType() {
        return OssSupportEnum.LOCAL.getValue();
    }

    @Override
    protected String uploadInternal(InputStream inputStream, String path, boolean isPrivate) {
        try {
            // 存储文件
            if (isPrivate) {
                localStore(inputStream, getFile(privateLocation, privateBucket, path));
            } else {
                localStore(inputStream, getFile(publicLocation, publicBucket, path));
                if (StrUtil.isNotBlank(endPoint)) {
                    // URL（外链）
                    return StrUtil.format("{}/{}/{}", endPoint, publicBucket, path);
                }
            }
            return StrUtil.EMPTY;
        } catch (IORuntimeException e) {
            throw FileUploadException.of(e);
        }
    }

    @Override
    protected String getDownloadUrl(String path, boolean isPrivate) {
        // 绝对路径
        return isPrivate ?
                StrUtil.format("{}/{}/{}", privateLocation, privateBucket, path) :
                StrUtil.format("{}/{}/{}", publicLocation, publicBucket, path);
    }

    @Override
    public void deleteInternal(String path, boolean isPrivate) {
        try {
            // 删除文件
            if (!(isPrivate ? FileUtil.del(getFile(privateLocation, privateBucket, path))
                    : FileUtil.del(getFile(publicLocation, publicBucket, path)))) {
                throw SystemException.of(ErrorCode.FILE_DELETE_FAILURE, StrUtil.EMPTY);
            }
        } catch (IORuntimeException e) {
            throw SystemException.of(e, ErrorCode.FILE_DELETE_FAILURE, e.getMessage());
        }
    }

    /**
     * 获取{@link File}
     *
     * @param location 存储位置
     * @param bucket   空间
     * @param path     路径（相对路径）
     * @return {@link File}
     */
    private File getFile(String location, String bucket, String path) {
        return new File(location
                + File.separator + bucket
                + File.separator + path);
    }

    /**
     * 本地存储
     *
     * @param inputStream {@link InputStream}
     * @param file        {@link File}
     */
    private void localStore(InputStream inputStream, File file) {
        try {
            // 将流的内容写入文件（自动关闭输入流）
            FileUtil.writeFromStream(inputStream, file);
        } catch (IORuntimeException e) {
            throw FileUploadException.of(e);
        }
    }

}
