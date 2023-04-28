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

package me.xiajhuan.summer.system.extend.dto;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.TreeDto;
import me.xiajhuan.summer.core.validation.group.AddGroup;
import me.xiajhuan.summer.core.validation.group.UpdateGroup;
import me.xiajhuan.summer.system.extend.enums.RegionLevelEnum;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 行政区域 Dto
 *
 * @author xiajhuan
 * @date 2023/4/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtendRegionDto extends TreeDto<Long> {

    /**
     * 区域级别
     *
     * @see RegionLevelEnum
     */
    @NotNull(message = "{extend.region.level.require}", groups = {AddGroup.class, UpdateGroup.class})
    @Range(min = 0, max = 2, message = "{extend.region.level.range}", groups = {AddGroup.class, UpdateGroup.class})
    private Integer level;

    /**
     * 创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createTime;

    /**
     * 子节点
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ExtendRegionDto> children = ListUtil.of();

    /**
     * 上级区域名称
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String parentName;

}
