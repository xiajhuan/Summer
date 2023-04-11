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

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.system.security.entity.SecurityDeptEntity;

import java.util.List;
import java.util.Set;

/**
 * 部门 Service
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
public interface SecurityDeptService extends IService<SecurityDeptEntity> {

    /**
     * 树形结构列表
     *
     * @param needAll 是否需要全部，true：是 false：不是
     * @return 部门列表（树形结构）
     */
    List<SecurityDeptDto> treeList(boolean needAll);

    SecurityDeptDto getById(Long id);

    void add(SecurityDeptDto dto);

    void update(SecurityDeptDto dto);

    void delete(Long id);

    /**
     * 获取子部门ID集合
     *
     * @param deptId 部门ID
     * @return 子部门ID集合
     */
    Set<Long> getChildIdSet(Long deptId);

    /**
     * 缓存所有部门
     */
    void cacheAll();

}
