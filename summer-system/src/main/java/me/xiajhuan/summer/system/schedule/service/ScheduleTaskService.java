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

package me.xiajhuan.summer.system.schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.system.schedule.dto.ScheduleTaskDto;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;

/**
 * 定时任务 Service
 *
 * @author xiajhuan
 * @date 2023/4/17
 */
public interface ScheduleTaskService extends IService<ScheduleTaskEntity> {

    Page<ScheduleTaskDto> page(ScheduleTaskDto dto);

    ScheduleTaskDto getById(Long id);

    void add(ScheduleTaskDto dto);

    void update(ScheduleTaskDto dto);

    void delete(Long[] ids);

    void execute(Long[] ids);

    void pause(Long[] ids);

    void resume(Long[] ids);

    void manualStart();

    /**
     * 初始化定时任务
     *
     * @return 是否初始化成功，true：是 false：否
     */
    boolean initSchedule();

}
