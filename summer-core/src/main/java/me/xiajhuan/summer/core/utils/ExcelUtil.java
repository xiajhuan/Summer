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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.excel.parser.AbstractExcelParser;
import me.xiajhuan.summer.core.excel.parser.subClass.ExcelCacheParser;
import me.xiajhuan.summer.core.excel.parser.subClass.ExcelDbParser;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.FileDownloadException;
import me.xiajhuan.summer.core.exception.custom.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel工具
 *
 * @author xiajhuan
 * @date 2022/12/1
 * @see EasyExcelFactory
 */
public class ExcelUtil extends EasyExcelFactory {

    /**
     * 构造ExcelUtil
     */
    private ExcelUtil() {
    }

    /**
     * 导入（保存到Db）
     *
     * @param file        {@link MultipartFile}
     * @param dtoClass    DtoClass
     * @param service     {@link IService}
     * @param entityClass EntityClass
     * @param <D>         Dto类型
     * @param <T>         Entity类型
     */
    public static <D, T> void importDb(MultipartFile file, Class<D> dtoClass, IService<T> service, Class<T> entityClass) {
        importDb(file, dtoClass, ExcelDbParser.build(service, entityClass));
    }

    /**
     * 导入（保存到Db，自定义前/后置处理）
     *
     * @param file           {@link MultipartFile}
     * @param dtoClass       DtoClass
     * @param customDbParser 自定义DbParser，需继承 {@link ExcelDbParser}，可覆写：
     *                       {@link AbstractExcelParser#handleDtoBefore(Object, AnalysisContext)}，
     *                       {@link AbstractExcelParser#handleEntityListBefore()}，
     *                       {@link AbstractExcelParser#handleEntityListAfter()}
     * @param <D>            Dto类型
     * @param <T>            Entity类型
     */
    public static <D, T> void importDb(MultipartFile file, Class<D> dtoClass, ExcelDbParser<D, T> customDbParser) {
        try {
            read(file.getInputStream(), dtoClass, customDbParser).sheet().doRead();
        } catch (IOException e) {
            throw FileUploadException.of(e, ErrorCode.FILE_UPLOAD_FAILURE);
        }
    }

    /**
     * 导入（缓存），谨慎使用！
     *
     * @param file        {@link MultipartFile}
     * @param dtoClass    DtoClass
     * @param entityClass EntityClass
     * @param <D>         Dto类型
     * @param <T>         Entity类型
     */
    public static <D, T> void importCache(MultipartFile file, Class<D> dtoClass, Class<T> entityClass) {
        importCache(file, dtoClass, ExcelCacheParser.build(entityClass));
    }

    /**
     * 导入（缓存，自定义前/后置处理），谨慎使用！
     *
     * @param file              {@link MultipartFile}
     * @param dtoClass          DtoClass
     * @param customCacheParser 自定义CacheParser，需继承 {@link ExcelCacheParser}，可覆写：
     *                          {@link AbstractExcelParser#handleDtoBefore(Object, AnalysisContext)}，
     *                          {@link AbstractExcelParser#handleEntityListBefore()}，
     *                          {@link AbstractExcelParser#handleEntityListAfter()}
     * @param <D>               Dto类型
     * @param <T>               Entity类型
     */
    public static <D, T> void importCache(MultipartFile file, Class<D> dtoClass, ExcelCacheParser<D, T> customCacheParser) {
        try {
            read(file.getInputStream(), dtoClass, customCacheParser).sheet().doRead();
        } catch (IOException e) {
            throw FileUploadException.of(e, ErrorCode.FILE_UPLOAD_FAILURE);
        }
    }

    /**
     * 导出
     *
     * @param response  {@link HttpServletResponse}
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param dtoList   Dto类型列表
     * @param dtoClass  DtoClass
     * @param code      错误编码 {@link ErrorCode#EXCEL_TEMPLATE_DOWNLOAD_FAILURE} {@link ErrorCode#EXCEL_EXPORT_FAILURE}
     * @param <D>       Dto类型
     */
    public static <D> void export(HttpServletResponse response, String fileName, String sheetName,
                                  List<D> dtoList, Class<D> dtoClass, int code) {
        if (StrUtil.isBlank(fileName)) {
            // 当前日期
            fileName = DateUtil.formatDate(DateUtil.date());
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", StrUtil.format("attachment;filename={}.xlsx", fileName));

            write(response.getOutputStream(), dtoClass).sheet(sheetName).doWrite(dtoList);
        } catch (IOException e) {
            throw FileDownloadException.of(e, code);
        }
    }

}
