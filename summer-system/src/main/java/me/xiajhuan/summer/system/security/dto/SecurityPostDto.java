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

package me.xiajhuan.summer.system.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.core.enums.StatusEnum;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 岗位 Dto
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityPostDto extends PageSortDto {

    /**
     * 岗位编码
     */
    @Null(message = "{security.post.code.null}", groups = UpdateGroup.class)
    @NotBlank(message = "{security.post.code.require}", groups = AddGroup.class)
    private String code;

    /**
     * 岗位名称
     */
    @NotBlank(message = "{security.post.name.require}", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 状态
     *
     * @see StatusEnum
     */
    @Range(min = 0, max = 1, message = "{status.range}", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "{status.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;

    //*******************查询参数********************

    /**
     * 岗位编码或名称
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String codeOrName;

}
