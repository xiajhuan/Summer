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

package me.xiajhuan.summer.system.schedule.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import me.xiajhuan.summer.system.schedule.mapper.ScheduleTaskMapper;
import me.xiajhuan.summer.system.schedule.service.ScheduleTaskService;
import org.springframework.stereotype.Service;

/**
 * 任务 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/17
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class ScheduleServiceImpl extends ServiceImpl<ScheduleTaskMapper, ScheduleTaskEntity> implements ScheduleTaskService {
}
