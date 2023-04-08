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
import me.xiajhuan.summer.system.security.dto.SecurityPostDto;
import me.xiajhuan.summer.system.security.entity.SecurityPostEntity;

import java.util.List;

/**
 * 岗位 Service
 *
 * @author xiajhuan
 * @date 2023/4/8
 */
public interface SecurityPostService extends IService<SecurityPostEntity> {

    Page<SecurityPostDto> page(SecurityPostDto dto);

    List<SecurityPostDto> list(int status);

    SecurityPostDto getById(Long id);

    void add(SecurityPostDto dto);

    void update(SecurityPostDto dto);

    void delete(Long[] ids);

}
