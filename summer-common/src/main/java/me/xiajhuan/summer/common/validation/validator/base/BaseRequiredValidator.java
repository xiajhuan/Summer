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

package me.xiajhuan.summer.common.validation.validator.base;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 基础必填校验器（required）
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@NoArgsConstructor
@Setter
public class BaseRequiredValidator {

    /**
     * 是否必填，true：必填 false：非必填
     */
    protected boolean required;

}
