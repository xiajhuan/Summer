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

package me.xiajhuan.summer.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 乐观锁控制 Entity基类<br>
 * 包含【id,create_by,create_time,update_by,update_time,dept_id,version】字段
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class OptimisticLockBaseEntity extends CommonBaseEntity {

    /**
     * 乐观锁控制<br>
     * note：表字段“version”默认值应为0
     */
    @Version
    @TableField(fill = FieldFill.INSERT, value = "version")
    private Integer version;

}
