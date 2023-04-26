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
import lombok.*;
import me.xiajhuan.summer.core.base.entity.CommonEntity;
import me.xiajhuan.summer.system.extend.enums.RegionLevelEnum;

/**
 * 行政区域 Entity
 *
 * @author xiajhuan
 * @date 2023/4/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("extend_region")
public class ExtendRegionEntity extends CommonEntity {

    /**
     * 上级区域ID
     */
    private Long parentId;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 顺序，越小优先级越高
     */
    private Integer weight;

    /**
     * 区域级别
     *
     * @see RegionLevelEnum
     */
    private Integer level;

    /**
     * 上级区域名称
     */
    @TableField(exist = false)
    private String parentName;

}
