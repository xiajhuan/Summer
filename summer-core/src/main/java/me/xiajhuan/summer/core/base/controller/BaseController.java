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

import cn.hutool.core.io.file.FileNameUtil;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.oss.factory.OssServerFactory;
import me.xiajhuan.summer.core.properties.BatchLimitProperties;
import me.xiajhuan.summer.core.utils.AssertUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

/**
 * 基本 Controller基类
 *
 * @author xiajhuan
 * @date 2023/4/8
 * @see FileNameUtil
 */
public abstract class BaseController {

    @Resource
    private BatchLimitProperties batchLimitProperties;

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
     * 多文件上传
     *
     * @param files {@link MultipartFile} 数组
     * @return 存储的URL数组
     */
    protected String[] multiFileUpload(MultipartFile[] files) {
        return Arrays.stream(files).map(this::fileUpload).toArray(String[]::new);
    }

    /**
     * 文件上传
     *
     * @param file {@link MultipartFile}
     * @return 存储的URL
     */
    protected String fileUpload(MultipartFile file) {
        try {
            return OssServerFactory.getOssServer().upload(file.getBytes(),
                    FileNameUtil.getSuffix(file.getOriginalFilename()));
        } catch (IOException e) {
            throw FileUploadException.of(e);
        }
    }

}
