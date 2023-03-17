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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 基础字段自动填充
 *
 * @author xiajhuan
 * @date 2022/11/29
 * @see MetaObjectHandler
 */
@Component
public class FieldFillHandler implements MetaObjectHandler {

    /**
     * 新增时自动填充
     *
     * @param metaObject {@link MetaObject}
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object[] array = currentUsernameAndDateTime();

        // 创建者和修改者
        setFieldValByName("createBy", array[0], metaObject);
        setFieldValByName("updateBy", array[0], metaObject);

        // 创建时间和修改时间
        setFieldValByName("createTime", array[1], metaObject);
        setFieldValByName("updateTime", array[1], metaObject);

        // 部门ID
        setFieldValByName("deptId", SecurityUtil.getCurrentDeptId(), metaObject);

        // 乐观锁控制
        setFieldValByName("version", 0, metaObject);
    }

    /**
     * 修改时自动填充
     *
     * @param metaObject {@link MetaObject}
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object[] array = currentUsernameAndDateTime();

        // 修改者和修改时间
        setFieldValByName("updateBy", array[0], metaObject);
        setFieldValByName("updateTime", array[1], metaObject);
    }

    /**
     * 当前用户名和时间
     *
     * @return 当前用户名和时间
     */
    private Object[] currentUsernameAndDateTime() {
        Object[] array = ArrayUtil.newArray(2);

        // 当前用户名，未登录则默认为：systemUser
        array[0] = SecurityUtil.getCurrentUsername();
        // 当前时间
        array[1] = DateUtil.date();

        return array;
    }

}
