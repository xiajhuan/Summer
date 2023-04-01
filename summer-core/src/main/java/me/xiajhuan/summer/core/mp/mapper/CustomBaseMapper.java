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

package me.xiajhuan.summer.core.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.xiajhuan.summer.core.mp.injector.CustomSqlInjector;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义 BaseMapper
 *
 * @author xiajhuan
 * @date 2023/3/29
 * @see BaseMapper
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入（JDBC批量提交）
     *
     * @param entityList Entity类型列表
     * @return 插入数量
     * @see CustomSqlInjector#realSaveBatch()
     */
    int realSaveBatch(List<T> entityList);

    /**
     * 根据ID更新固定的几个字段（不忽略值为 {@code null} 的字段）
     *
     * @param entity Entity类型对象
     * @return 更新数量
     * @see CustomSqlInjector#alwaysUpdateById()
     */
    int alwaysUpdateById(@Param(Constants.ENTITY) T entity);

    /**
     * 逻辑批量删除（更新时填充字段以第一条记录为准）
     *
     * @param entityList Entity类型列表
     * @return 逻辑删除数量
     * @see CustomSqlInjector#logicDeleteBatchByIds()
     */
    int logicDeleteBatchByIds(@Param(Constants.COLL) List<T> entityList);

}
