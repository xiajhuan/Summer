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

package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.security.dto.SecurityUserDto;
import me.xiajhuan.summer.common.security.entity.SecurityUserEntity;

/**
 * 用户 Service
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public interface SecurityUserService extends IService<SecurityUserEntity> {

    /**
     * 根据用户名获取
     *
     * @param username 用户名
     * @return 用户Dto
     */
    SecurityUserDto getByUsername(String username);

}
