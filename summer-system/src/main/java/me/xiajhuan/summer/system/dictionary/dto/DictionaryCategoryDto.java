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

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**
 * 字典类别 Dto
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryCategoryDto extends PageSortDto {

    /**
     * 类别编码
     */
    @Null(message = "{dictionary.category.code.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{dictionary.category.code.require}", groups = AddGroup.class)
    private String code;

    /**
     * 类别名称
     */
    @NotBlank(message = "{dictionary.category.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;

    /**
     * 项列表
     */
    private List<DictionaryItemDto> itemList = ListUtil.of();

    //*******************查询参数********************

    /**
     * 类别编码或名称
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String codeOrName;

}
