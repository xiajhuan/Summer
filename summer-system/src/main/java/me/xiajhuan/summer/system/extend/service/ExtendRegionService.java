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

package me.xiajhuan.summer.system.extend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.extend.dto.ExtendRegionDto;
import me.xiajhuan.summer.system.extend.entity.ExtendRegionEntity;

import java.util.List;

/**
 * 行政区域 Service
 *
 * @author xiajhuan
 * @date 2023/4/27
 */
public interface ExtendRegionService extends IService<ExtendRegionEntity> {

    /**
     * 树形结构列表
     *
     * @param needFilter 是否需要数据权限过滤，true：是 false：否
     * @return 行政区域列表（树形结构）或{@code null}
     */
    List<ExtendRegionDto> treeList(boolean needFilter);

    ExtendRegionDto getById(Long id);

    void add(ExtendRegionDto dto);

    void update(ExtendRegionDto dto);

    void delete(Long id);

    List<ExtendRegionDto> listByParentId(Long parentId);

}
