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

package me.xiajhuan.summer.system.locale.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.ExcelDto;
import me.xiajhuan.summer.core.enums.LocaleSupportEnum;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 名称 Dto
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LocaleNameDto extends ExcelDto {

    /**
     * 表名
     */
    @Null(message = "{locale.name.tableName.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{locale.name.tableName.require}", groups = AddGroup.class)
    @ExcelProperty(value = "表名", index = 0)
    private String tableName;

    /**
     * 行ID
     */
    @Null(message = "{locale.name.lineId.null}", groups = UpdateGroup.class)
    @NotNull(message = "{locale.name.lineId.require}", groups = AddGroup.class)
    @ExcelProperty(value = "行ID", index = 1)
    private Long lineId;

    /**
     * 字段名
     */
    @Null(message = "{locale.name.fieldName.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{locale.name.fieldName.require}", groups = AddGroup.class)
    @ExcelProperty(value = "字段名", index = 2)
    private String fieldName;

    /**
     * 字段值
     */
    @NotBlank(message = "{locale.name.fieldValue.require}", groups = {AddGroup.class, UpdateGroup.class})
    @ExcelProperty(value = "字段值", index = 3)
    private String fieldValue;

    /**
     * 地区语言
     *
     * @see LocaleSupportEnum
     */
    @Null(message = "{locale.name.locale.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{locale.name.locale.require}", groups = AddGroup.class)
    @ExcelProperty(value = "地区语言", index = 4)
    private String locale;

    /**
     * 创建时间
     */
    private Date createTime;

}
