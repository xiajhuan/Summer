package me.xiajhuan.summer.common.mp.standard;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.xiajhuan.summer.common.constant.SortConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;
import me.xiajhuan.summer.common.mp.handler.QueryParamHandler;

/**
 * MybatisPlus通用操作<br>
 * note：非侵入式封装，不影响原生API使用
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
public interface MpCommonOperation<D, T> {

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

        return QueryParamHandler.handlePageAndSort(pageAndSort, page, SortConst.DEFAULT_SORT, SortConst.DEFAULT_ORDER, true);
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
        return getQueryWrapperUnconditional(dto, isCount);
    }

    /**
     * 获取 {@link LambdaQueryWrapper}（无条件）
     *
     * @param dto     Dto类型对象
     * @param isCount 是否是count查询，是则不加入查询字段，true：是 false：否
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getQueryWrapperUnconditional(D dto, boolean isCount) {
        if (isCount) {
            return Wrappers.lambdaQuery();
        } else {
            return getSelectWrapper(currentEntityClass());
        }
    }

    /**
     * 查询结果属性填充钩子
     *
     * @param entity Entity类型对象
     */
    default void fieldFillHook(T entity) {
    }

    /**
     * 自定义分页
     * <p>
     * note：推荐关闭MP分页时内置的count查询使用自定义count，
     * 因为它只是在查询语句外包裹了一层COUNT(*)，这将带来不必要的性能开销
     * </p>
     *
     * @param page {@link Page}
     * @param dto  Dto类型对象
     * @return {@link IPage}
     */
    IPage<T> customPage(Page<T> page, D dto);

}
