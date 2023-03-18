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

package me.xiajhuan.summer.admin.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.admin.common.security.dto.SecurityUserTokenDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserTokenEntity;

/**
 * 用户Token Service
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public interface SecurityUserTokenService extends IService<SecurityUserTokenEntity> {

    /**
     * 生成用户Token
     *
     * @param userId 用户ID
     * @return 用户Token
     */
    SecurityUserTokenDto generateUserToken(Long userId);

    /**
     * 用户退出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

}
