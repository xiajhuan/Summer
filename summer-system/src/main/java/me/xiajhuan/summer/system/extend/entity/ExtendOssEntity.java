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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.core.base.entity.SimpleEntity;
import me.xiajhuan.summer.core.enums.OssSupportEnum;

/**
 * 对象存储 Entity
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("extend_oss")
public class ExtendOssEntity extends SimpleEntity {

    /**
     * 部门ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long deptId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * URL（外链）
     */
    private String url;

    /**
     * 逻辑空间名
     */
    private String bucketName;

    /**
     * 路径（相对路径）
     */
    private String path;

    /**
     * 类型
     *
     * @see OssSupportEnum
     */
    private Integer type;

}
