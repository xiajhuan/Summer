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

package me.xiajhuan.summer.admin.common.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户Token Dto
 *
 * @author xiajhuan
 * @date 2023/3/4
 */
@Data
public class SecurityUserTokenDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * accessToken
     */
    private String token;

    /**
     * 过期时间（s）
     */
    private Integer expireTime;

}
