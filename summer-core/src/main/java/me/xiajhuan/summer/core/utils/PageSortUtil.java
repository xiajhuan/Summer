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

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
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
     * 初始化 {@link defaultFieldArray} {@link defaultOrderArray} {@link camelToUnderscore}
     */
    static {
        Setting setting = SpringContextUtil.getBean(SettingBeanConst.CORE, Setting.class);

        String defaultField = setting.getByGroup("sort.default-field", "Mp");
        if (StrUtil.isNotBlank(defaultField)) {
            defaultFieldArray = defaultField.split(StrPool.COMMA);
        }
        String defaultOrder = setting.getByGroup("sort.default-order", "Mp");
        if (StrUtil.isNotBlank(defaultOrder)) {
            defaultOrderArray = defaultOrder.split(StrPool.COMMA);
        }

        camelToUnderscore = setting.getBool("sort.field-camelToUnderscore", "Mp", true);
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
        Page<T> page = Page.of(pageSortDto.getPageNum(), pageSortDto.getPageSize());

        //*******************排序处理********************

        String field = pageSortDto.getField();
        String order = pageSortDto.getOrder();

        if (StrUtil.isAllNotBlank(field, order)
                && !StrUtil.equalsAnyIgnoreCase(field, NULL_AND_UNDEFINED)
                && !StrUtil.equalsAnyIgnoreCase(order, NULL_AND_UNDEFINED)) {
            // 按照请求参数排序
            handleSortInternal(page, field.split(StrPool.COMMA), order.split(StrPool.COMMA));
        } else {
            // 按照默认字段和规则排序
            if (ArrayUtil.isNotEmpty(defaultFieldArray) && ArrayUtil.isNotEmpty(defaultOrderArray)) {
                handleSortInternal(page, defaultFieldArray, defaultOrderArray);
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
    public static int getPageOffset(int pageNum, int pageSize) {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 处理排序参数
     *
     * @param page       {@link Page}
     * @param fieldArray 字段数组
     * @param orderArray 规则数组
     * @param <T>        Entity类型
     */
    private static <T> void handleSortInternal(Page<T> page, String[] fieldArray, String[] orderArray) {
        int num = checkFieldAndOrder(fieldArray, orderArray);

        if (camelToUnderscore) {
            // 驼峰转下划线
            fieldArray = Arrays.stream(fieldArray)
                    .map(StrUtil::toUnderlineCase).toArray(String[]::new);
        }

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
