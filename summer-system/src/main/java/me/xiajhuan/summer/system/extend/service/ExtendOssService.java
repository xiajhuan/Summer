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

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.extend.dto.ExtendOssDto;
import me.xiajhuan.summer.system.extend.entity.ExtendOssEntity;

/**
 * 对象存储 Service
 *
 * @author xiajhuan
 * @date 2023/4/30
 */
public interface ExtendOssService extends IService<ExtendOssEntity> {

    Page<ExtendOssDto> page(ExtendOssDto dto);

    void delete(Long[] ids);

    /**
     * 批量新增
     *
     * @param dictArray {@link Dict} 数组
     */
    void addBatch(Dict[] dictArray);

}
