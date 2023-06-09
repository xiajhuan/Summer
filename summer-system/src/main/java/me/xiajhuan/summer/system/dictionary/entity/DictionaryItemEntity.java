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

package me.xiajhuan.summer.system.dictionary.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.entity.CommonEntity;

/**
 * 字典项 Entity
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dictionary_item")
public class DictionaryItemEntity extends CommonEntity {

    /**
     * 项标签
     */
    private String label;

    /**
     * 项值
     */
    private String value;

    /**
     * 顺序，越小优先级越高
     */
    private Integer weight;

    /**
     * 描述
     */
    private String description;

    /**
     * 类别ID
     */
    private Long categoryId;

}
