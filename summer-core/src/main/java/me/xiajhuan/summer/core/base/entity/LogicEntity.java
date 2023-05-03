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

package me.xiajhuan.summer.core.base.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 逻辑删除 Entity基类<br>
 * 包含【id,create_by,create_time,update_by,update_time,dept_id,delete_flag】字段
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class LogicEntity extends CommonEntity {

    /**
     * 删除标识 0：未删除 1：已删除<br>
     * note：Entity对应表的delete_flag字段默认值应设为0
     */
    @TableLogic
    private Integer deleteFlag;

}
