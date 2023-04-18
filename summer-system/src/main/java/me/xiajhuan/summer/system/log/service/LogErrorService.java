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
import me.xiajhuan.summer.system.log.dto.LogErrorDto;
import me.xiajhuan.summer.system.log.entity.LogErrorEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 错误日志 Service
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
public interface LogErrorService extends IService<LogErrorEntity> {

    Page<LogErrorDto> page(LogErrorDto dto);

    List<LogErrorDto> list(LogErrorDto dto);

    LogErrorDto getById(Long id);

    /**
     * 统计记录数
     *
     * @param dto 错误日志Dto
     * @return 记录数
     */
    long count(LogErrorDto dto);

    /**
     * 异步保存
     *
     * @param e       {@link Exception}
     * @param request {@link HttpServletRequest}
     */
    @Async(ThreadPoolConst.ASYNC_COMMON)
    void saveAsync(Exception e, HttpServletRequest request);

    /**
     * 清理
     */
    void clear();

}
