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

package me.xiajhuan.summer.core.mp.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.xiajhuan.summer.core.constant.SortConst;
import me.xiajhuan.summer.core.data.PageAndSort;

/**
 * 查询参数处理
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
public class QueryParamHandler {

    /**
     * 处理分页排序参数<br>
     * note:只支持单字段排序
     *
     * @param pageAndSort       分页排序参数
     * @param page              {@link Page}
     * @param defaultSort       默认排序字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否驼峰转下划线，true：是 false：否
     * @param <T>               Entity类型
     * @return {@link Page}
     */
    public static <T> Page<T> handlePageAndSort(PageAndSort pageAndSort, Page<T> page, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        // 排序字段
        String sortField = pageAndSort.getField();
        // 排序规则
        String sortOrder = pageAndSort.getOrder();

        // 是否驼峰转下划线
        if (camelToUnderscore) {
            sortField = StrUtil.toUnderlineCase(sortField);
            defaultSort = StrUtil.toUnderlineCase(defaultSort);
        }

        if (StrUtil.isAllNotBlank(sortField, sortOrder)
                && !StrUtil.equalsAnyIgnoreCase(sortField, SortConst.NULL_AND_UNDEFINED)
                && !StrUtil.equalsAnyIgnoreCase(sortOrder, SortConst.NULL_AND_UNDEFINED)) {
            // 按照请求传递参数排序
            if (StrUtil.equalsAnyIgnoreCase(sortOrder, SortConst.ORDER_DESC)) {
                page.addOrder(OrderItem.desc(sortField));
            } else {
                page.addOrder(OrderItem.asc(sortField));
            }
        } else {
            // 按照默认字段和规则排序
            if (StrUtil.isNotBlank(defaultSort)) {
                if (StrUtil.equalsAnyIgnoreCase(defaultOrder, SortConst.ORDER_DESC)) {
                    page.addOrder(OrderItem.desc(defaultSort));
                } else {
                    page.addOrder(OrderItem.asc(defaultSort));
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
    public static int getPageOffset(int pageNum, int pageSize) {
        return (pageNum - 1) * pageSize;
    }

}
