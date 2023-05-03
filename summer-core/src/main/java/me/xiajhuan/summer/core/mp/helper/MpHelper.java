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

package me.xiajhuan.summer.core.mp.helper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.base.dto.ExcelDto;
import me.xiajhuan.summer.core.utils.PageSortUtil;

/**
 * MybatisPlus Helper，note：
 * <ul>
 *   <li>Mp常用api封装，可根据实际需求选择是否引入</li>
 *   <li>
 *     Dto必须继承{@link PageSortDto}或{@link ExcelDto}，ServiceImpl类写法示例：<br>
 *     {@code public class LogErrorServiceImpl extends ... implements LogErrorService, MpHelper<LogErrorDto, LogErrorEntity>}
 *   </li>
 * </ul>
 *
 * @author xiajhuan
 * @date 2022/11/21
 */
public interface MpHelper<D extends PageSortDto, T> {

    /**
     * 当前EntityClass
     *
     * @return EntityClass
     */
    @SuppressWarnings("unchecked")
    default Class<T> currentEntityClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), ServiceImpl.class, 1);
    }

    //*******************模板方法钩子，执行顺序从上到下依次********************

    // getEmptyWrapper->getQueryWrapper->getSelectWrapper->getSortWrapper
    // 获取空Wrapper->获取查询条件Wrapper->获取查询条件+查询字段Wrapper->获取查询条件+查询字段+排序条件Wrapper

    /**
     * 获取空{@link LambdaQueryWrapper}
     *
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getEmptyWrapper() {
        return Wrappers.lambdaQuery(currentEntityClass());
    }

    // note：以下三个方法覆写时一定要先调用上面一个方法！

    /**
     * 获取{@link LambdaQueryWrapper}（指定查询条件，默认无条件）<br>
     * note：调用后包含【查询条件】
     *
     * @param dto Dto类型对象
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getQueryWrapper(D dto) {
        // 查询条件（无）
        return getEmptyWrapper();
    }

    /**
     * 获取{@link LambdaQueryWrapper}（指定查询条件/查询字段，默认无条件/全部Entity字段）<br>
     * note：调用后包含【查询条件,查询字段】
     *
     * @param dto Dto类型对象
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getSelectWrapper(D dto) {
        // 查询条件（无），查询字段（全部Entity字段）
        return getQueryWrapper(dto).select(currentEntityClass(), i -> true);
    }

    /**
     * 获取{@link LambdaQueryWrapper}（指定查询条件/查询字段/排序条件），note：
     * <ul>
     *   <li>调用后包含【查询条件,查询字段,排序条件】</li>
     *   <li>默认无条件/全部Entity字段</li>
     *   <li>默认排序条件依据{@link PageSortDto}的field和order值指定</li>
     *   <li>{@link PageSortUtil#handleSort(PageSortDto, LambdaQueryWrapper)}有Sql注入风险！推荐根据实际需求覆写</li>
     * </ul>
     *
     * @param dto Dto类型对象
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> getSortWrapper(D dto) {
        return PageSortUtil.handleSort(dto, getSelectWrapper(dto));
    }

    //*******************分页排序统一处理********************

    /**
     * 分页排序处理，默认COUNT总记录数
     *
     * @param dto Dto类型对象
     * @return {@link Page}
     */
    default Page<T> handlePageSort(D dto) {
        return PageSortUtil.handlePageSort(dto);
    }

    //*******************自选钩子********************

    /**
     * 添加查询字段（默认全部Entity字段）<br>
     * note：这个钩子只是为了避免相同的查询字段重复写（如“page”和“getById”要查询的字段相同时），不会参与上面的模板流程！
     *
     * @param queryWrapper {@link LambdaQueryWrapper}
     * @return {@link LambdaQueryWrapper}
     */
    default LambdaQueryWrapper<T> addSelectField(LambdaQueryWrapper<T> queryWrapper) {
        // 查询字段（全部Entity字段）
        return queryWrapper.select(currentEntityClass(), i -> true);
    }

    /**
     * Dto前置处理钩子，可重写该钩子个性化前置处理，如校验/属性运算/属性填充等
     *
     * @param dto Dto类型对象
     */
    default void handleDtoBefore(D dto) {
    }

    /**
     * 自定义分页钩子<br>
     * note：必须覆写该方法才能调用！否则将抛出{@link UnsupportedOperationException}
     *
     * @param dto Dto类型对象
     * @return {@link Page}
     */
    default Page<T> customPage(D dto) {
        throw new UnsupportedOperationException("必须覆写该方法才能调用！");
    }

    /**
     * Entity后置处理钩子，可重写该钩子个性化后置处理，如属性运算/属性填充等
     *
     * @param entity Entity类型对象
     */
    default void handleEntityAfter(T entity) {
    }

}
