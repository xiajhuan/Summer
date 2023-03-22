/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.locale.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.entity.CommonBaseEntity;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;

/**
 * 国际化名称 Entity
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("locale_international_name")
public class LocaleInternationalNameEntity extends CommonBaseEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 地区语言
     *
     * @see LocaleSupportEnum
     */
    private String locale;

}