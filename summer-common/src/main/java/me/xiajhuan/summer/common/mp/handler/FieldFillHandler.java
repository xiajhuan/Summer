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

package me.xiajhuan.summer.common.mp.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.xiajhuan.summer.common.utils.SecurityUtil;
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
     * 当前用户名和时间
     *
     * @return 当前用户名和时间
     */
    private Object[] currentUsernameAndDateTime() {
        Object[] arr = new Object[2];

        // 当前用户名，未登录则默认为：systemUser
        arr[0] = SecurityUtil.getCurrentUsername();
        // 当前时间
        arr[1] = DateUtil.date();

        return arr;
    }

    /**
     * 新增时自动填充
     *
     * @param metaObject {@link MetaObject}
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object[] arr = currentUsernameAndDateTime();

        // 创建者和修改者
        setFieldValByName("createBy", arr[0], metaObject);
        setFieldValByName("updateBy", arr[0], metaObject);

        // 创建时间和修改时间
        setFieldValByName("createTime", arr[1], metaObject);
        setFieldValByName("updateTime", arr[1], metaObject);

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
        Object[] arr = currentUsernameAndDateTime();

        // 修改者和修改时间
        setFieldValByName("updateBy", arr[0], metaObject);
        setFieldValByName("updateTime", arr[1], metaObject);
    }

}
