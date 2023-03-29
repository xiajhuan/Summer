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

package me.xiajhuan.summer.core.mp.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * 自定义Sql注入器<br>
 * note：必须调用 {@link DefaultSqlInjector#getMethodList(Class, TableInfo)} 保留MybatisPlus自带的方法！
 *
 * @author xiajhuan
 * @date 2023/3/29
 * @see DefaultSqlInjector
 * @see InsertBatchSomeColumn
 */
public class MySqlInjector extends DefaultSqlInjector {

    /**
     * 构造MySqlInjector
     */
    private MySqlInjector() {
    }

    /**
     * 构建MySqlInjector
     *
     * @return MySqlInjector
     */
    public static MySqlInjector of() {
        return new MySqlInjector();
    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        // 保留MybatisPlus自带的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);

        // 注入批量插入Sql
        methodList.add(realSaveBatch());

        return methodList;
    }

    /**
     * 批量插入Sql（JDBC批量提交）
     * <p>
     * note1：MybatisPlus自带的saveBatch是循环插入，大数据量时可能效率低下，故提供“JDBC批量提交”模式的支持
     * note2：不同的数据库支持度不一样，Mysql以外的数据库谨慎使用！
     * </p>
     *
     * @return {@link AbstractMethod}
     */
    private AbstractMethod realSaveBatch() {
        return new InsertBatchSomeColumn("realSaveBatch",
                field -> !field.isLogicDelete() && !"version".equals(field.getProperty()));
    }

}
