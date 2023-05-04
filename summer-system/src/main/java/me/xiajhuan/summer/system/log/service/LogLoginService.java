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
import me.xiajhuan.summer.core.enums.LoginOperationEnum;
import me.xiajhuan.summer.core.enums.LoginStatusEnum;
import me.xiajhuan.summer.system.log.dto.LogLoginDto;
import me.xiajhuan.summer.system.log.entity.LogLoginEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录日志 Service
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
public interface LogLoginService extends IService<LogLoginEntity> {

    Page<LogLoginDto> page(LogLoginDto dto);

    List<LogLoginDto> list(LogLoginDto dto);

    /**
     * 统计记录数
     *
     * @param dto 登录日志Dto
     * @return 记录数
     */
    long count(LogLoginDto dto);

    /**
     * 异步保存
     *
     * @param loginUser      登录用户名
     * @param loginOperation 登录操作，参考{@link LoginOperationEnum}
     * @param loginStatus    登录状态，参考{@link LoginStatusEnum}
     * @param request        {@link HttpServletRequest}
     */
    @Async(ThreadPoolConst.ASYNC_COMMON)
    void saveAsync(String loginUser, int loginOperation, int loginStatus, HttpServletRequest request);

    /**
     * 清理
     */
    void clear();

}
