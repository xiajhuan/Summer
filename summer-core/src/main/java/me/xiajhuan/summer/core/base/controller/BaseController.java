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
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.oss.factory.OssServerFactory;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 基本 Controller基类
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
public abstract class BaseController {

    @Value("${batch.limit.excel-max-export}")
    private long excelMaxExport;

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
        AssertUtil.checkBetween(count, 0L, excelMaxExport,
                () -> ValidationException.of(ErrorCode.EXCEL_EXPORT_MAXIMUM_LIMIT, String.valueOf(excelMaxExport)));
    }

    /**
     * 多文件上传
     *
     * @param files     {@link MultipartFile}数组
     * @param isPrivate 是否私有，true：是 false：否
     * @return {@link Dict}数组
     */
    protected Dict[] multiFileUpload(MultipartFile[] files, boolean isPrivate) {
        return Arrays.stream(files)
                .map(file -> fileUpload(file, isPrivate))
                .toArray(Dict[]::new);
    }

    /**
     * 文件上传
     *
     * @param file      {@link MultipartFile}
     * @param isPrivate 是否私有，true：是 false：否
     * @return {@link Dict}
     */
    protected Dict fileUpload(MultipartFile file, boolean isPrivate) {
        return OssServerFactory.getOssServer().upload(file, isPrivate);
    }

    /**
     * 文件下载，使用默认文件名称
     *
     * @param path      路径（相对路径）
     * @param isPrivate 是否私有，true：是 false：否
     * @param response  {@link HttpServletResponse}
     */
    protected void fileDownload(String path, boolean isPrivate, HttpServletResponse response) {
        fileDownload(path, StrUtil.subAfter(path, StrPool.SLASH, true), isPrivate, response);
    }

    /**
     * 文件下载，指定文件名称
     *
     * @param path      路径（相对路径）
     * @param fileName  文件名称
     * @param isPrivate 是否私有，true：是 false：否
     * @param response  {@link HttpServletResponse}
     */
    protected void fileDownload(String path, String fileName, boolean isPrivate, HttpServletResponse response) {
        OssServerFactory.getOssServer().download(path, fileName, isPrivate, response);
    }

}
