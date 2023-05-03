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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * Bean工具
 *
 * @author xiajhuan
 * @date 2022/11/21
 * @see cn.hutool.core.bean.BeanUtil
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * 不允许实例化
     */
    private BeanUtil() {
    }

    /**
     * 转换（单一对象）
     *
     * @param source 源对象
     * @param target 目标Class
     * @param <T>    目标类型
     * @return 目标对象
     */
    public static <T> T convert(Object source, Class<T> target) {
        return copyProperties(source, target);
    }

    /**
     * 转换（对象列表）
     *
     * @param sourceList 源列表
     * @param target     目标Class
     * @param <T>        目标类型
     * @return 目标列表
     */
    public static <T> List<T> convert(List<?> sourceList, Class<T> target) {
        return copyToList(sourceList, target);
    }

    /**
     * 转换（分页对象）
     *
     * @param page   {@link Page}
     * @param target 目标Class
     * @param <T>    目标类型
     * @return {@link Page}
     */
    public static <T> Page<T> convert(Page<?> page, Class<T> target) {
        Page<T> pageResult = new Page<>();
        pageResult.setRecords(convert(page.getRecords(), target));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

}