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

package me.xiajhuan.summer.admin.common.locale.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.dto.BaseDto;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 国际化名称 Dto
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LocaleInternationalNameDto extends BaseDto {

    /**
     * 表名
     */
    @NotBlank(message = "{locale.internationalName.tableName.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "表名", index = 0)
    private String tableName;

    /**
     * 字段名
     */
    @NotBlank(message = "{locale.internationalName.fieldName.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "字段名", index = 1)
    private String fieldName;

    /**
     * 字段值
     */
    @NotBlank(message = "{locale.internationalName.fieldValue.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "字段值", index = 2)
    private String fieldValue;

    /**
     * 地区语言
     *
     * @see LocaleSupportEnum
     */
    @NotBlank(message = "{locale.internationalName.locale.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "地区语言", index = 3)
    private String locale;

    /**
     * 创建时间
     */
    private Date createTime;

}
