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
import me.xiajhuan.summer.admin.common.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityMenuEntity;

import java.util.List;

/**
 * 菜单 Service
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
public interface SecurityMenuService extends IService<SecurityMenuEntity> {

    /**
     * 树形结构列表
     *
     * @return 菜单列表（树形结构）
     */
    List<SecurityMenuDto> treeList();

    SecurityMenuDto getById(Long id);

    void add(SecurityMenuDto dto);

    void update(SecurityMenuDto dto);

    void delete(Long id);

}
