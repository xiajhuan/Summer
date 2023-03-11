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

package me.xiajhuan.summer.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 类型转换工具
 *
 * @author xiajhuan
 * @date 2022/11/21
 * @see BeanUtil
 */
public class ConvertUtil {

    /**
     * 单一对象转换
     *
     * @param source 源
     * @param target 目标Class
     * @param <T>    目标类型
     * @return 目标对象
     */
    public static <T> T convert(Object source, Class<T> target) {
        return BeanUtil.copyProperties(source, target);
    }

    /**
     * 对象列表转换
     *
     * @param sourceList 源列表
     * @param target     目标Class
     * @param <T>        目标类型
     * @return 目标列表
     */
    public static <T> List<T> convert(List<?> sourceList, Class<T> target) {
        return BeanUtil.copyToList(sourceList, target);
    }

    /**
     * 分页对象转换
     *
     * @param page   {@link IPage}
     * @param target 目标Class
     * @param <T>    目标类型
     * @return {@link IPage}
     */
    public static <T> IPage<T> convert(IPage<?> page, Class<T> target) {
        IPage<T> pageResult = new Page<>();
        pageResult.setRecords(convert(page.getRecords(), target));
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

}