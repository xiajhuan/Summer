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

package me.xiajhuan.summer.common.dto;

import cn.hutool.core.lang.Dict;
import lombok.Data;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * Dto基类
 *
 * @author xiajhuan
 * @date 2022/3/6
 */
@Data
public abstract class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    /**
     * 参数
     *
     * @see Dict
     */
    private Dict params = Dict.create();

}
