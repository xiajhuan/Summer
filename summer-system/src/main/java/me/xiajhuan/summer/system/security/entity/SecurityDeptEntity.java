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

package me.xiajhuan.summer.system.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.entity.CommonEntity;

/**
 * 部门 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_dept")
public class SecurityDeptEntity extends CommonEntity {

    /**
     * 部门ID
     */
    @TableField(exist = false)
    private Long deptId;

    /**
     * 上级部门ID
     */
    private Long parentId;

    /**
     * 所有上级部门ID，用“,”分隔
     */
    private String parentIdAll;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 顺序，越小优先级越高
     */
    private Integer weight;

    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;

}
