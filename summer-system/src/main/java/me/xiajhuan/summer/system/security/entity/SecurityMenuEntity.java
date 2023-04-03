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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.entity.SimpleEntity;
import me.xiajhuan.summer.system.security.enums.ComponentTypeEnum;
import me.xiajhuan.summer.system.security.enums.OpenModeEnum;

import java.util.Date;

/**
 * 菜单 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_menu")
public class SecurityMenuEntity extends SimpleEntity {

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private Date updateTime;

    /**
     * 上级菜单ID
     */
    private Long parentId;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权
     * <p>
     * 多个授权以“,”分隔
     * 格式：模块名:业务名:操作名
     * 例如：
     * <pre>
     *     1.security:user:page,security:user:add
     *     2.log:error:page,log:error:excelExport
     * </pre>
     * </p>
     */
    private String permissions;

    /**
     * 类型
     *
     * @see ComponentTypeEnum
     */
    private Integer type;

    /**
     * 打开方式
     *
     * @see OpenModeEnum
     */
    private Integer openMode;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 顺序，越小优先级越高
     */
    private Integer weight;

    /**
     * 菜单名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 上级菜单名称
     */
    @TableField(exist = false)
    private String parentName;

}
