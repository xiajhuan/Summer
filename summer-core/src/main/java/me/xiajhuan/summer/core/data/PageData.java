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

package me.xiajhuan.summer.core.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
@Setter
@Getter
@ToString
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> rows;

    /**
     * 构造私有化
     */
    private PageData() {
    }

    /**
     * 构建PageData
     *
     * @param page {@link Page}
     * @param <T>  数据类型
     * @return PageData
     */
    public static <T> PageData<T> of(Page<T> page) {
        return of(page.getTotal(), page.getRecords());
    }

    /**
     * 构建PageData
     *
     * @param total 总记录数
     * @param rows  数据列表
     * @param <T>   数据类型
     * @return PageData
     */
    public static <T> PageData<T> of(long total, List<T> rows) {
        PageData<T> pageData = new PageData<>();
        pageData.setTotal(total);
        pageData.setRows(rows);
        return pageData;
    }

}