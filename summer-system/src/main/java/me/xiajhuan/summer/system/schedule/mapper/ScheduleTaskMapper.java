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

package me.xiajhuan.summer.system.schedule.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.system.schedule.entity.ScheduleTaskEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 定时任务 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/17
 */
@InterceptorIgnore
public interface ScheduleTaskMapper extends BaseMapper<ScheduleTaskEntity> {

    /**
     * 判断是否存在
     *
     * @param beanName Bean名称
     * @return 返回 {@code null} 则不存在
     */
    @Select("SELECT 1 FROM schedule_task WHERE bean_name = #{beanName} LIMIT 1")
    Integer exist(@Param("beanName") String beanName);

}
