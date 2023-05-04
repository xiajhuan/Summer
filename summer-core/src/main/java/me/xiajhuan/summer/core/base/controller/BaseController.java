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

package me.xiajhuan.summer.core.base.controller;

import cn.hutool.core.lang.Dict;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.oss.server.AbstractOssServer;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.oss.factory.OssServerFactory;
import me.xiajhuan.summer.core.properties.BatchLimitProperties;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 基本 Controller基类
 *
 * @author xiajhuan
 * @date 2023/4/8
 * @see AbstractOssServer#upload(MultipartFile, String)
 */
public abstract class BaseController {

    @Resource
    private BatchLimitProperties batchLimitProperties;

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    protected LoginUser getLoginUser() {
        return SecurityUtil.getLoginUser();
    }

    /**
     * 校验最大导出数
     *
     * @param count 导出数
     */
    protected void validateMaxExport(long count) {
        long excelMaxExport = batchLimitProperties.getExcelMaxExport();
        AssertUtil.checkBetween(count, 0L, excelMaxExport,
                () -> ValidationException.of(ErrorCode.EXCEL_EXPORT_MAXIMUM_LIMIT, String.valueOf(excelMaxExport)));
    }

    /**
     * 多文件上传，使用默认逻辑空间名
     *
     * @param files {@link MultipartFile}数组
     * @return {@link Dict}数组
     */
    protected Dict[] multiFileUpload(MultipartFile[] files) {
        return multiFileUpload(files, null);
    }

    /**
     * 文件上传，使用默认逻辑空间名
     *
     * @param file {@link MultipartFile}
     * @return {@link Dict}
     */
    protected Dict fileUpload(MultipartFile file) {
        return fileUpload(file, null);
    }

    /**
     * 多文件上传，指定逻辑空间名
     *
     * @param files      {@link MultipartFile}数组
     * @param bucketName 逻辑空间名
     * @return {@link Dict}数组
     */
    protected Dict[] multiFileUpload(MultipartFile[] files, String bucketName) {
        return Arrays.stream(files)
                .map(file -> fileUpload(file, bucketName))
                .toArray(Dict[]::new);
    }

    /**
     * 文件上传，指定逻辑空间名
     *
     * @param file       {@link MultipartFile}
     * @param bucketName 逻辑空间名
     * @return {@link Dict}
     */
    protected Dict fileUpload(MultipartFile file, String bucketName) {
        return OssServerFactory.getOssServer().upload(file, bucketName);
    }

}
