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

package me.xiajhuan.summer.core.mp.injector;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteBatchByIds;
import me.xiajhuan.summer.core.base.entity.LogicEntity;

import java.util.List;

/**
 * 自定义Sql注入器<br>
 * note：必须调用{@link DefaultSqlInjector#getMethodList(Class, TableInfo)}保留MybatisPlus自带的方法！
 *
 * @author xiajhuan
 * @date 2023/3/29
 * @see DefaultSqlInjector
 */
public class CustomSqlInjector extends DefaultSqlInjector {

    /**
     * 构造私有化
     */
    private CustomSqlInjector() {
    }

    /**
     * 构建CustomSqlInjector
     *
     * @return CustomSqlInjector
     */
    public static CustomSqlInjector of() {
        return new CustomSqlInjector();
    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        // 保留MybatisPlus自带的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);

        // 注入“批量插入Sql”
        methodList.add(realSaveBatch());

        // 注入“根据ID更新固定几个字段Sql”
        methodList.add(alwaysUpdateById());

        // 注入“逻辑批量删除Sql”
        methodList.add(logicDeleteBatchByIds());

        return methodList;
    }

    /**
     * 批量插入（JDBC批量提交）<br>
     * Sql示例：INSERT INTO table(...) VALUES (...),(...),(...);note：
     * <ul>
     *   <li>MybatisPlus自带的saveBatch是循环插入，数据量大时效率低下</li>
     *   <li>如果个别字段在Entity里为{@code null}但表中有配置默认值，插入后该字段将为“NULL”而不是默认值</li>
     *   <li>逻辑删除和乐观锁控制字段不包含在内</li>
     *   <li>不同数据库支持度不一样，Mysql以外数据库谨慎使用！</li>
     * </ul>
     *
     * @return {@link AbstractMethod}
     * @see InsertBatchSomeColumn
     */
    private AbstractMethod realSaveBatch() {
        return new InsertBatchSomeColumn("realSaveBatch",
                field -> !field.isLogicDelete() && !"version".equals(field.getProperty()));
    }

    /**
     * 根据ID更新固定几个字段（不忽略值为{@code null}的字段）<br>
     * Sql示例：UPDATE table SET 非空字段 = xxx,空字段 = NULL WHERE id = 主键;note：
     * <ul>
     *   <li>
     *     MybatisPlus自带的update/updateById会自动忽略{@code null}字段不更新，<br>
     *     而这个Sql会把Entity存在的字段都更新，如果某个字段值为{@code null}就更新为“NULL”
     *   </li>
     *   <li>插入时自动填充、乐观锁控制和逻辑删除字段不包含在内</li>
     * </ul>
     *
     * @return {@link AbstractMethod}
     * @see AlwaysUpdateSomeColumnById
     */
    private AbstractMethod alwaysUpdateById() {
        return new AlwaysUpdateSomeColumnById("alwaysUpdateById",
                field -> field.getFieldFill() != FieldFill.INSERT && !"version".equals(field.getProperty()));
    }

    /**
     * 逻辑批量删除（更新时填充字段以第一条记录为准）<br>
     * Sql示例：UPDATE table SET 填充字段 = xxx,逻辑删除字段 = 1 WHERE id IN (主键1,主键2,主键3);note：
     * <ul>
     *   <li>只有Entity支持逻辑删除时才生效，推荐继承{@link LogicEntity}</li>
     *   <li>若无法接受所有记录填充字段以第一条记录为准，如要求字段update_time的时间精确，请使用循环删除</li>
     * </ul>
     *
     * @return {@link AbstractMethod}
     * @see LogicDeleteBatchByIds
     */
    private AbstractMethod logicDeleteBatchByIds() {
        return new LogicDeleteBatchByIds("logicDeleteBatchByIds");
    }

}
