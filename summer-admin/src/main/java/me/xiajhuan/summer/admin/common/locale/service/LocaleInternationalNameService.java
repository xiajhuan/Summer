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

package me.xiajhuan.summer.admin.common.locale.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.admin.common.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.admin.common.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.core.data.PageAndSort;

import java.util.List;

/**
 * 国际化名称 Service
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
public interface LocaleInternationalNameService extends IService<LocaleInternationalNameEntity> {

    IPage<LocaleInternationalNameDto> page(PageAndSort pageAndSort, LocaleInternationalNameDto dto);

    List<LocaleInternationalNameDto> list(LocaleInternationalNameDto dto);

    LocaleInternationalNameDto getById(Long id);

    void add(LocaleInternationalNameDto dto);

    void update(LocaleInternationalNameDto dto);

    void delete(Long[] ids);

    default List<LocaleInternationalNameDto> excelTemplate() {
        return null;
    }

    /**
     * 判断是否存在
     *
     * @param entity 国际化名称Entity
     * @return 返回 {@code null} 则不存在
     */
    Integer exist(LocaleInternationalNameEntity entity);

}
