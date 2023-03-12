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
import me.xiajhuan.summer.admin.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityDeptEntity;

import java.util.List;

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
     * @return 部门列表（树形结构）
     */
    List<SecurityDeptDto> treeList();

    void add(SecurityDeptDto dto);

    void update(SecurityDeptDto dto);

    void delete(String[] ids);

}
