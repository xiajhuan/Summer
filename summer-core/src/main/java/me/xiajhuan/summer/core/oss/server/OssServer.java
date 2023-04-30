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
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 对象存储服务
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
public interface OssServer {

    /**
     * 获取上传路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 上传路径
     */
    default String getPath(String prefix, String suffix) {
        String date = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        String uuid = UUID.fastUUID().toString();

        return StrUtil.isBlank(prefix) ?
                StrUtil.format("{}/{}.{}", date, uuid, suffix) :
                StrUtil.format("{}/{}/{}.{}", prefix, date, uuid, suffix);
    }

    /**
     * 上传（字节数组）
     *
     * @param data   字节数组
     * @param suffix 后缀
     * @return 存储的URL
     */
    default String upload(byte[] data, String suffix) {
        return upload(new ByteArrayInputStream(data), suffix);
    }

    /**
     * 上传（输入流）
     *
     * @param inputStream {@link InputStream}
     * @param suffix      后缀
     * @return 存储的URL
     */
    String upload(InputStream inputStream, String suffix);

}
