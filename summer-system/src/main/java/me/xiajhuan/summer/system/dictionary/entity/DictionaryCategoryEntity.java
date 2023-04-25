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
 * 字典类别 Entity
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dictionary_category")
public class DictionaryCategoryEntity extends CommonEntity {

    /**
     * 类别代码
     */
    private String code;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

}
