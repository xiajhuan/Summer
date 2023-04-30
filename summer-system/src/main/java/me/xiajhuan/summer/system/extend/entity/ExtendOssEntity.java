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

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.entity.SimpleEntity;
import me.xiajhuan.summer.core.enums.OssSupportEnum;

/**
 * 对象存储 Entity<br>
 * note：该表仅用于持久化对象存储信息
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("extend_oss")
public class ExtendOssEntity extends SimpleEntity {

    /**
     * URL
     */
    private String url;

    /**
     * 类型
     *
     * @see OssSupportEnum
     */
    private Integer type;

}
