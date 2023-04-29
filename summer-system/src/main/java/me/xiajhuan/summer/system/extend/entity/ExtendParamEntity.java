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

package me.xiajhuan.summer.system.extend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.entity.CommonEntity;

/**
 * 配置参数 Entity<br>
 * note：该表仅用于持久化一些系统配置参数
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("extend_param")
public class ExtendParamEntity extends CommonEntity {

    /**
     * 部门ID
     */
    @TableField(exist = false)
    private Long deptId;

    /**
     * 参数键
     */
    private String key;

    /**
     * 参数值（Json格式）
     */
    private String value;

}
