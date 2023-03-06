package me.xiajhuan.summer.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.excel.AbstractExcelParser;
import me.xiajhuan.summer.common.excel.subClass.ExcelCacheParser;
import me.xiajhuan.summer.common.excel.subClass.ExcelDbParser;
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
     * 导出（List<DtoClass>）
     *
     * @param response   {@link HttpServletResponse}
     * @param fileName   文件名称
     * @param sheetName  sheet名称
     * @param dtoList    Dto类型列表
     * @param excelClass ExcelClass
     * @param <D>        Dto类型
     * @param <E>        Excel类型
     * @throws IOException I/O异常
     */
    public static <D, E> void exportWithD(HttpServletResponse response, String fileName, String sheetName, List<D> dtoList,
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

    /**
     * 导出（List<ExcelClass>）
     *
     * @param response   {@link HttpServletResponse}
     * @param fileName   文件名称
     * @param sheetName  sheet名称
     * @param dtoList    Dto类型列表
     * @param excelClass ExcelClass
     * @param <D>        Dto类型
     * @param <E>        Excel类型
     * @throws IOException I/O异常
     */
    public static <D, E> void exportWithE(HttpServletResponse response, String fileName, String sheetName, List<D> dtoList,
                                          Class<E> excelClass) throws IOException {
        exportWithD(response, fileName, sheetName, ConvertUtil.convert(dtoList, excelClass), excelClass);
    }

}
