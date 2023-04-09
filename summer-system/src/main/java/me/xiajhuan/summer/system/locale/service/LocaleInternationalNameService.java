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

package me.xiajhuan.summer.system.locale.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.system.locale.entity.LocaleInternationalNameEntity;

import java.util.List;

/**
 * 国际化名称 Service
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
public interface LocaleInternationalNameService extends IService<LocaleInternationalNameEntity> {

    Page<LocaleInternationalNameDto> page(LocaleInternationalNameDto dto);

    List<LocaleInternationalNameDto> list(LocaleInternationalNameDto dto);

    LocaleInternationalNameDto getById(Long id);

    void add(LocaleInternationalNameDto dto);

    void update(LocaleInternationalNameDto dto);

    void delete(Long[] ids);

    /**
     * Excel模板数据
     *
     * @return 国际化名称列表 或 {@code null}
     */
    default List<LocaleInternationalNameDto> excelTemplate() {
        return null;
    }

    /**
     * 统计记录数
     *
     * @param dto 国际化名称Dto
     * @return 记录数
     */
    long count(LocaleInternationalNameDto dto);

    /**
     * 判断是否存在
     *
     * @param lineId 行ID
     * @param locale 地区语言
     * @return 返回 {@code null} 则不存在
     */
    Integer exist(long lineId, String locale);

}
