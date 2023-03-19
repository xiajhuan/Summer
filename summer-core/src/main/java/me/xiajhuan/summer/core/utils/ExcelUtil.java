/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
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
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.excel.AbstractExcelParser;
import me.xiajhuan.summer.core.excel.subClass.ExcelCacheParser;
import me.xiajhuan.summer.core.excel.subClass.ExcelDbParser;
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
 * @see EasyExcel
 */
public class ExcelUtil {

    /**
     * 导入（保存到Db）
     *
     * @param file        {@link MultipartFile}
     * @param excelClass  ExcelClass
     * @param service     {@link IService}
     * @param entityClass EntityClass
     * @param <E>         Excel类型
     * @param <T>         Entity类型
     * @throws IOException I/O异常
     */
    public static <E, T> void importToDb(MultipartFile file, Class<E> excelClass, IService<T> service, Class<T> entityClass) throws IOException {
        importToDb(file, excelClass, ExcelDbParser.build(service, entityClass));
    }

    /**
     * 导入（保存到Db，自定义前置处理）
     *
     * @param file           {@link MultipartFile}
     * @param excelClass     ExcelClass
     * @param customDbParser 自定义DbParser，需继承 {@link ExcelDbParser}，覆写 {@link AbstractExcelParser#handleParsedDataBefore()}
     * @param <E>            Excel类型
     * @param <T>            Entity类型
     * @throws IOException I/O异常
     */
    public static <E, T> void importToDb(MultipartFile file, Class<E> excelClass, ExcelDbParser<T, E> customDbParser) throws IOException {
        EasyExcel.read(file.getInputStream(), excelClass, customDbParser).sheet().doRead();
    }

    /**
     * 导入（缓存）
     *
     * @param file        {@link MultipartFile}
     * @param excelClass  ExcelClass
     * @param entityClass EntityClass
     * @param <E>         Excel类型
     * @param <T>         Entity类型
     * @throws IOException I/O异常
     */
    public static <E, T> void importToCache(MultipartFile file, Class<E> excelClass, Class<T> entityClass) throws IOException {
        importToCache(file, excelClass, ExcelCacheParser.build(entityClass));
    }

    /**
     * 导入（缓存，自定义前置处理）
     *
     * @param file              {@link MultipartFile}
     * @param excelClass        ExcelClass
     * @param customCacheParser 自定义CacheParser，需继承 {@link ExcelCacheParser}，覆写 {@link AbstractExcelParser#handleParsedDataBefore()}
     * @param <E>               Excel类型
     * @param <T>               Entity类型
     * @throws IOException I/O异常
     */
    public static <E, T> void importToCache(MultipartFile file, Class<E> excelClass, ExcelCacheParser<T, E> customCacheParser) throws IOException {
        EasyExcel.read(file.getInputStream(), excelClass, customCacheParser).sheet().doRead();
    }

    /**
     * 导出（List<ExcelClass>）
     *
     * @param response   {@link HttpServletResponse}
     * @param fileName   文件名
     * @param sheetName  sheet名
     * @param dtoList    Dto类型列表
     * @param excelClass ExcelClass
     * @param <D>        Dto类型
     * @param <E>        Excel类型
     * @throws IOException I/O异常
     */
    public static <D, E> void exportExcel(HttpServletResponse response, String fileName, String sheetName, List<D> dtoList,
                                          Class<E> excelClass) throws IOException {
        exportDto(response, fileName, sheetName, ConvertUtil.convert(dtoList, excelClass), excelClass);
    }

    /**
     * 导出（List<DtoClass>）
     *
     * @param response   {@link HttpServletResponse}
     * @param fileName   文件名
     * @param sheetName  sheet名
     * @param dtoList    Dto类型列表
     * @param excelClass ExcelClass
     * @param <D>        Dto类型
     * @param <E>        Excel类型
     * @throws IOException I/O异常
     */
    public static <D, E> void exportDto(HttpServletResponse response, String fileName, String sheetName, List<D> dtoList,
                                        Class<E> excelClass) throws IOException {
        if (StrUtil.isBlank(fileName)) {
            // 当前日期
            fileName = DateUtil.formatDate(DateUtil.date());
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", StrUtil.format("attachment;filename={}.xlsx", fileName));

        EasyExcel.write(response.getOutputStream(), excelClass).sheet(sheetName).doWrite(dtoList);
    }

}
