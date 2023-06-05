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

package me.xiajhuan.summer.system.monitor.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.core.constant.ThreadPoolConst;
import me.xiajhuan.summer.system.monitor.dto.MonitorOnlineDto;
import me.xiajhuan.summer.system.monitor.entity.MonitorOnlineEntity;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.Set;

/**
 * 在线用户 Service
 *
 * @author xiajhuan
 * @date 2023/4/12
 */
public interface MonitorOnlineService extends IService<MonitorOnlineEntity> {

    Page<MonitorOnlineDto> page(MonitorOnlineDto dto);

    /**
     * 异步保存/更新
     *
     * @param userEntity 用户Entity
     * @param expireTime 过期时间（s）
     */
    @Async(ThreadPoolConst.ASYNC_COMMON)
    void saveOrUpdateAsync(SecurityUserEntity userEntity, Integer expireTime);

    /**
     * 删除
     *
     * @param userId 用户ID
     */
    void delete(Long userId);

    /**
     * 批量删除
     *
     * @param userIdSet 用户ID集合
     */
    void deleteBatch(Set<Long> userIdSet);

    void kick(Long[] userIds);

}
