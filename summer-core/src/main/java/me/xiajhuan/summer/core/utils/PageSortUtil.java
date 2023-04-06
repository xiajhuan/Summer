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

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.dto.PageSortDto;

import java.util.Arrays;

/**
 * 分页排序工具
 *
 * @author xiajhuan
 * @date 2023/3/28
 */
public class PageSortUtil {

    /**
     * 构造PageSortUtil
     */
    private PageSortUtil() {
    }

    /**
     * 空值和未定义
     */
    private static final String[] NULL_AND_UNDEFINED = {"null", "undefined"};

    /**
     * 升序
     */
    private static final String[] ASC = {"asc", "ascend", "ascending"};

    /**
     * 降序
     */
    private static final String[] DESC = {"desc", "descend", "descending"};

    /**
     * 单页分页条数限制
     */
    private static long maxLimit;

    /**
     * 默认排序字段数组
     */
    private static String[] defaultFieldArray;

    /**
     * 默认排序规则数组
     */
    private static String[] defaultOrderArray;

    /**
     * 排序字段是否驼峰转下划线
     */
    private static boolean camelToUnderscore;

    /**
     * 初始化 {@link maxLimit} {@link defaultFieldArray}<br>
     * {@link defaultOrderArray} {@link camelToUnderscore}
     */
    static {
        Setting setting = SpringUtil.getBean(SettingConst.CORE, Setting.class);

        maxLimit = setting.getLong("page.max-limit", "Mp", 2000L);

        String defaultField = setting.getByGroup("sort.default-field", "Mp");
        if (StrUtil.isNotBlank(defaultField)) {
            defaultFieldArray = defaultField.split(StrPool.COMMA);
        }
        String defaultOrder = setting.getByGroup("sort.default-order", "Mp");
        if (StrUtil.isNotBlank(defaultOrder)) {
            defaultOrderArray = defaultOrder.split(StrPool.COMMA);
        }

        camelToUnderscore = setting.getBool("sort.field-camel-to-underscore", "Mp", true);
    }

    /**
     * 处理排序参数<br>
     * note：拼接排序条件至最后，有Sql注入风险，谨慎使用！
     *
     * @param pageSortDto  分页排序Dto
     * @param queryWrapper {@link LambdaQueryWrapper}
     * @param <D>          Dto类型
     * @param <T>          Entity类型
     * @return {@link LambdaQueryWrapper}
     */
    public static <D extends PageSortDto, T> LambdaQueryWrapper<T> handleSort(D pageSortDto, LambdaQueryWrapper<T> queryWrapper) {
        Dict sortDesc = handleSortInternal(pageSortDto.getField(), pageSortDto.getOrder());

        if (sortDesc != null) {
            // 添加排序条件
            int num = sortDesc.getInt("num");
            String[] fieldArray = (String[]) sortDesc.get("field");
            String[] orderArray = (String[]) sortDesc.get("order");

            // Sql片段示例：ORDER BY create_time DESC,create_by ASC
            StringBuilder sqlSegment = StrUtil.builder(" ORDER BY ");
            for (int i = 0; i < num; i++) {
                if (StrUtil.equalsAnyIgnoreCase(orderArray[i], ASC)) {
                    // 升序
                    sqlSegment.append(fieldArray[i]).append(StrUtil.SPACE)
                            .append("ASC").append(StrPool.COMMA);
                } else if (StrUtil.equalsAnyIgnoreCase(orderArray[i], DESC)) {
                    // 降序
                    sqlSegment.append(fieldArray[i]).append(StrUtil.SPACE)
                            .append("DESC").append(StrPool.COMMA);
                } else {
                    throw new IllegalArgumentException(StrUtil.format("不支持的排序规则【{}】", orderArray[i]));
                }
            }
            // 拼接排序条件至最后
            queryWrapper.last(sqlSegment.substring(0, sqlSegment.length() - 1));
        }

        return queryWrapper;
    }

    /**
     * 处理分页排序参数
     *
     * @param pageSortDto 分页排序Dto
     * @param <D>         Dto类型
     * @param <T>         Entity类型
     * @return {@link Page}
     */
    public static <D extends PageSortDto, T> Page<T> handlePageSort(D pageSortDto) {
        return handlePageSort(pageSortDto, 0L);
    }

    /**
     * 处理分页排序参数
     *
     * @param pageSortDto 分页排序Dto
     * @param customTotal 自定义分页总记录数（值不等于0时不 count）
     * @param <D>         Dto类型
     * @param <T>         Entity类型
     * @return {@link Page}
     */
    public static <D extends PageSortDto, T> Page<T> handlePageSort(D pageSortDto, long customTotal) {
        final Page<T> page;

        //*******************分页处理********************

        long pageNum = pageSortDto.getPageNum();
        long pageSize = pageSortDto.getPageSize();

        if (customTotal == 0L) {
            // count查询分页总记录数
            page = Page.of(pageNum, pageSize);
        } else {
            // 自定义分页总记录数
            page = Page.of(pageNum, pageSize, customTotal, false);
        }
        page.setMaxLimit(maxLimit);

        //*******************排序处理********************

        Dict sortDesc = handleSortInternal(pageSortDto.getField(), pageSortDto.getOrder());

        if (sortDesc != null) {
            // 添加排序条件
            int num = sortDesc.getInt("num");
            String[] fieldArray = (String[]) sortDesc.get("field");
            String[] orderArray = (String[]) sortDesc.get("order");

            for (int i = 0; i < num; i++) {
                if (StrUtil.equalsAnyIgnoreCase(orderArray[i], ASC)) {
                    // 升序
                    page.addOrder(OrderItem.asc(fieldArray[i]));
                } else if (StrUtil.equalsAnyIgnoreCase(orderArray[i], DESC)) {
                    // 降序
                    page.addOrder(OrderItem.desc(fieldArray[i]));
                } else {
                    throw new IllegalArgumentException(StrUtil.format("不支持的排序规则【{}】", orderArray[i]));
                }
            }
        }

        return page;
    }

    /**
     * 获取分页起始位置
     *
     * @param pageNum  当前页码
     * @param pageSize 每页记录数
     * @return 分页起始位置
     */
    public static long getPageOffset(long pageNum, long pageSize) {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 处理排序参数
     *
     * @param field 字段
     * @param order 规则
     * @return {@link Dict} 或 {@code null}
     */
    private static Dict handleSortInternal(String field, String order) {
        // 默认字段和规则
        String[] fieldArray = defaultFieldArray;
        String[] orderArray = defaultOrderArray;

        if (StrUtil.isAllNotBlank(field, order)
                && !StrUtil.equalsAnyIgnoreCase(field, NULL_AND_UNDEFINED)
                && !StrUtil.equalsAnyIgnoreCase(order, NULL_AND_UNDEFINED)) {
            // 按照请求参数排序
            fieldArray = field.split(StrPool.COMMA);
            orderArray = order.split(StrPool.COMMA);
        }

        if (ArrayUtil.isNotEmpty(fieldArray) && ArrayUtil.isNotEmpty(orderArray)) {
            int num = checkFieldAndOrder(fieldArray, orderArray);
            if (camelToUnderscore) {
                // 驼峰转下划线
                fieldArray = Arrays.stream(fieldArray)
                        .map(StrUtil::toUnderlineCase).toArray(String[]::new);
            }
            return Dict.of("num", num, "field", fieldArray, "order", orderArray);
        }
        return null;
    }

    /**
     * 检查排序字段和规则是否匹配
     *
     * @param fieldArray 字段数组
     * @param orderArray 规则数组
     * @return 字段数
     */
    private static int checkFieldAndOrder(String[] fieldArray, String[] orderArray) {
        if (fieldArray.length != orderArray.length) {
            throw new IllegalArgumentException("排序字段和规则不匹配");
        }
        return fieldArray.length;
    }

}
