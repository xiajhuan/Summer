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
     * @param dtoClass    DtoClass
     * @param service     {@link IService}
     * @param entityClass EntityClass
     * @param <D>         Dto类型
     * @param <T>         Entity类型
     * @throws IOException I/O异常
     */
    public static <D, T> void importDb(MultipartFile file, Class<D> dtoClass, IService<T> service, Class<T> entityClass) throws IOException {
        importDb(file, dtoClass, ExcelDbParser.build(service, entityClass));
    }

    /**
     * 导入（保存到Db，自定义前置处理）
     *
     * @param file           {@link MultipartFile}
     * @param dtoClass       DtoClass
     * @param customDbParser 自定义DbParser，需继承 {@link ExcelDbParser}，覆写 {@link AbstractExcelParser#handleParsedDataBefore()}
     * @param <D>            Dto类型
     * @param <T>            Entity类型
     * @throws IOException I/O异常
     */
    public static <D, T> void importDb(MultipartFile file, Class<D> dtoClass, ExcelDbParser<D, T> customDbParser) throws IOException {
        EasyExcel.read(file.getInputStream(), dtoClass, customDbParser).sheet().doRead();
    }

    /**
     * 导入（缓存）
     *
     * @param file        {@link MultipartFile}
     * @param dtoClass    DtoClass
     * @param entityClass EntityClass
     * @param <D>         Dto类型
     * @param <T>         Entity类型
     * @throws IOException I/O异常
     */
    public static <D, T> void importCache(MultipartFile file, Class<D> dtoClass, Class<T> entityClass) throws IOException {
        importCache(file, dtoClass, ExcelCacheParser.build(entityClass));
    }

    /**
     * 导入（缓存，自定义前置处理）
     *
     * @param file              {@link MultipartFile}
     * @param dtoClass          DtoClass
     * @param customCacheParser 自定义CacheParser，需继承 {@link ExcelCacheParser}，覆写 {@link AbstractExcelParser#handleParsedDataBefore()}
     * @param <D>               Dto类型
     * @param <T>               Entity类型
     * @throws IOException I/O异常
     */
    public static <D, T> void importCache(MultipartFile file, Class<D> dtoClass, ExcelCacheParser<D, T> customCacheParser) throws IOException {
        EasyExcel.read(file.getInputStream(), dtoClass, customCacheParser).sheet().doRead();
    }

    /**
     * 导出
     *
     * @param response  {@link HttpServletResponse}
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param dtoList   Dto类型列表
     * @param dtoClass  DtoClass
     * @param <D>       Dto类型
     * @throws IOException I/O异常
     */
    public static <D> void export(HttpServletResponse response, String fileName, String sheetName, List<D> dtoList,
                                  Class<D> dtoClass) throws IOException {
        if (StrUtil.isBlank(fileName)) {
            // 当前日期
            fileName = DateUtil.formatDate(DateUtil.date());
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", StrUtil.format("attachment;filename={}.xlsx", fileName));

        EasyExcel.write(response.getOutputStream(), dtoClass).sheet(sheetName).doWrite(dtoList);
    }

}
