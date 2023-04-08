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

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.entity.SimpleEntity;

import java.util.Date;

/**
 * 角色部门关联 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_role_dept")
public class SecurityRoleDeptEntity extends SimpleEntity {

    /**
     * 创建者<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private String createBy;

    /**
     * 创建时间<br>
     * note：不使用字段自动填充，否则“deptId”会被填充覆盖！
     */
    private Date createTime;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;

}
