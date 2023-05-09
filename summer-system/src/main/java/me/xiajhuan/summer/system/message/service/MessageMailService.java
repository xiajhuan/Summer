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

package me.xiajhuan.summer.system.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.message.dto.MessageMailDto;
import me.xiajhuan.summer.system.message.entity.MessageMailEntity;

/**
 * 邮件 Service
 *
 * @author xiajhuan
 * @date 2023/5/9
 */
public interface MessageMailService extends IService<MessageMailEntity> {

    Page<MessageMailDto> page(MessageMailDto dto);

    MessageMailDto getById(Long id);

    void add(MessageMailDto dto);

    void update(MessageMailDto dto);

    void delete(Long[] ids);

}
