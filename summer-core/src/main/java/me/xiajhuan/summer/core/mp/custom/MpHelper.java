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

package me.xiajhuan.summer.core.mp.custom;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.xiajhuan.summer.core.constant.SortConst;
import me.xiajhuan.summer.core.data.PageAndSort;
import me.xiajhuan.summer.core.entity.SimpleBaseEntity;
import me.xiajhuan.summer.core.mp.handler.QueryParamHandler;

/**
 * MybatisPlus Helper
 * <p>
 * 非侵入式封装了MybatisPlus的一些通用操作，
 * 可根据实际需求自行选择是否使用
 * </p>
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
public interface MpHelper<D, T> {

    /**
     * 当前EntityClass
     *
     * @return EntityClass
     */
    default Class<T> currentEntityClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), SimpleBaseEntity.class, 0);
    }

    /**
     * 分页排序参数处理
     *
     * @param pageAndSort 分页排序参数
     * @return {@link Page}
     */
    default Page<T> handlePageAndSort(PageAndSort pageAndSort) {
        Page<T> page = new Page<>(pageAndSort.getPageNum(), pageAndSort.getPageSize());

        return QueryParamHandler.handlePageAndSort(pageAndSort, page, SortConst.Field.DEFAULT, SortConst.Order.DEFAULT, true);
    }

    /**
     * 获取 {@link LambdaQueryWrapper}（指定查询字段，默认所有Entity字段）
     *
     * @param entityClass EntityClass
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getSelectWrapper(Class<T> entityClass) {
        LambdaQueryWrapper<T> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段（所有Entity字段）
        return queryWrapper.select(entityClass, i -> true);
    }

    /**
     * 获取 {@link LambdaQueryWrapper}（指定查询条件，默认无条件）
     *
     * @param dto     Dto类型对象
     * @param isCount 是否是count查询，是则不加入查询字段，true：是 false：否
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getQueryWrapper(D dto, boolean isCount) {
        return getQueryWrapperUnconditional(isCount);
    }

    /**
     * 获取 {@link LambdaQueryWrapper}（无条件）
     *
     * @param isCount 是否是count查询，是则不加入查询字段，true：是 false：否
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getQueryWrapperUnconditional(boolean isCount) {
        if (isCount) {
            return Wrappers.lambdaQuery();
        } else {
            return getSelectWrapper(currentEntityClass());
        }
    }

    /**
     * 自定义分页钩子
     * <p>
     * note：必须覆写该方法才能调用！否则将抛出 {@link UnsupportedOperationException}，
     * 推荐关闭MP分页时内置的count查询使用自定义count，
     * 因为它只是在查询语句外包裹了一层COUNT(*)，这将带来不必要的性能开销
     * </p>
     *
     * @param page {@link Page}
     * @param dto  Dto类型对象
     * @return {@link IPage}
     */
    default IPage<T> customPage(Page<T> page, D dto) {
        throw new UnsupportedOperationException("必须覆写该方法才能调用！");
    }

    /**
     * 查询结果属性填充钩子
     *
     * @param entity Entity类型对象
     */
    default void fieldFill(T entity) {
    }

}
