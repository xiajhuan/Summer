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

package me.xiajhuan.summer.system.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.security.dto.PasswordDto;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;

import java.util.List;

/**
 * 用户 Service
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public interface SecurityUserService extends IService<SecurityUserEntity> {

    Page<SecurityUserDto> page(SecurityUserDto dto);

    List<SecurityUserDto> list(SecurityUserDto dto);

    SecurityUserDto getById(Long id);

    void add(SecurityUserDto dto);

    void update(SecurityUserDto dto);

    void delete(Long[] ids);

    /**
     * 统计记录数
     *
     * @param dto 用户Dto
     * @return 记录数
     */
    long count(SecurityUserDto dto);

    /**
     * 根据用户名获取
     *
     * @param username 用户名
     * @return 用户Entity
     */
    SecurityUserEntity getByUsername(String username);

    /**
     * 修改密码并退出
     *
     * @param dto 密码Dto
     */
    void updatePasswordAndLogout(PasswordDto dto);

    /**
     * 重置密码
     *
     * @param ids ID数组
     * @return 重置的密码或 {@code null}
     */
    String reset(Long[] ids);

}
