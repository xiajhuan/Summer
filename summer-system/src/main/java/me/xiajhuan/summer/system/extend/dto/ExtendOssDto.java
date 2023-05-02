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

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.core.base.dto.PageSortDto;
import me.xiajhuan.summer.core.enums.OssSupportEnum;

import java.util.Date;

/**
 * 对象存储 Dto
 *
 * @author xiajhuan
 * @date 2023/4/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtendOssDto extends PageSortDto {

    /**
     * 文件名称
     */
    private String name;

    /**
     * URL
     */
    private String url;

    /**
     * 类型
     *
     * @see OssSupportEnum
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

}
