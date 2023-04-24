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

package me.xiajhuan.summer.system.dictionary.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.dictionary.dto.DictionaryItemDto;
import me.xiajhuan.summer.system.dictionary.entity.DictionaryItemEntity;

import java.util.List;
import java.util.Set;

/**
 * 字典项 Service
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
public interface DictionaryItemService extends IService<DictionaryItemEntity> {

    Page<DictionaryItemDto> page(DictionaryItemDto dto);

    DictionaryItemDto getById(Long id);

    void add(DictionaryItemDto dto);

    void update(DictionaryItemDto dto);

    void delete(Long[] ids);

    /**
     * 列表
     *
     * @param categoryId 类别ID
     * @return 字典项Dto列表
     */
    List<DictionaryItemDto> list(Long categoryId);

    /**
     * 删除
     *
     * @param categoryIdSet 类别ID集合
     */
    void delete(Set<Long> categoryIdSet);

}
