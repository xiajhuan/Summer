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

package me.xiajhuan.summer.system.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import me.xiajhuan.summer.system.log.dto.LogOperationDto;
import me.xiajhuan.summer.system.log.entity.LogOperationEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 操作日志 Service
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
public interface LogOperationService extends IService<LogOperationEntity> {

    Page<LogOperationDto> page(LogOperationDto dto);

    List<LogOperationDto> list(LogOperationDto dto);

    /**
     * 统计记录数
     *
     * @param dto 操作日志Dto
     * @return 记录数
     */
    long count(LogOperationDto dto);

    /**
     * 异步保存
     *
     * @param entity 操作日志Entity
     */
    @Async(ThreadPoolConst.Async.COMMON)
    void saveAsync(LogOperationEntity entity);

    /**
     * 清理
     */
    void clear();

}
