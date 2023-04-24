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

package me.xiajhuan.summer.system.dictionary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 字典类型 Dto
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryTypeDto extends PageSortDto {

    /**
     * 字典编码
     */
    @Null(message = "{dictionary.type.code.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{dictionary.type.code.require}", groups = AddGroup.class)
    private String code;

    /**
     * 字典名称
     */
    @NotBlank(message = "{dictionary.type.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 顺序，越小优先级越高
     */
    @Min(value = 0, message = "{weight.number}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{weight.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer weight;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;

}
