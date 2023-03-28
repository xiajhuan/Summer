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

package me.xiajhuan.summer.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页排序 Dto基类
 *
 * @author xiajhuan
 * @date 2022/3/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class PageSortDto extends BaseDto {

    //*******************分页********************

    /**
     * 当前页码，默认1
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer pageNum = 1;

    /**
     * 每页记录数，默认10
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer pageSize = 10;

    //*******************排序********************

    /**
     * 排序字段<br>
     * note：多字段排序时以“,”分隔，为空则不排序
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String field;

    /**
     * 排序规则<br>
     * note：多字段排序时以“,”分隔（必须与 {@link field} 匹配），为空则不排序
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String order;

}
