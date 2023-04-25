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
import me.xiajhuan.summer.system.dictionary.dto.DictionaryCategoryDto;
import me.xiajhuan.summer.system.dictionary.entity.DictionaryCategoryEntity;

import java.util.List;

/**
 * 字典类别 Service
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
public interface DictionaryCategoryService extends IService<DictionaryCategoryEntity> {

    Page<DictionaryCategoryDto> page(DictionaryCategoryDto dto);

    DictionaryCategoryDto getById(Long id);

    void add(DictionaryCategoryDto dto);

    void update(DictionaryCategoryDto dto);

    void delete(Long[] ids);

    List<DictionaryCategoryDto> all();

}
