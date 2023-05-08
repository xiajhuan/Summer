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

package me.xiajhuan.summer.system.log.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.system.log.dto.LogTaskDto;
import me.xiajhuan.summer.system.log.entity.LogTaskEntity;
import me.xiajhuan.summer.system.log.mapper.LogTaskMapper;
import me.xiajhuan.summer.system.log.service.LogTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时任务日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/19
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class LogTaskServiceImpl extends ServiceImpl<LogTaskMapper, LogTaskEntity> implements LogTaskService, MpHelper<LogTaskDto, LogTaskEntity> {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    /**
     * 日志清理天数限制
     */
    private int clearDaysLimit;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        clearDaysLimit = setting.getInt("task.clear-days-limit", "Log", -90);
    }

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LogTaskEntity> getQueryWrapper(LogTaskDto dto) {
        LambdaQueryWrapper<LogTaskEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // Bean名称
        String beanName = dto.getBeanName();
        queryWrapper.eq(StrUtil.isNotBlank(beanName), LogTaskEntity::getBeanName, beanName);
        // 类型
        Integer type = dto.getType();
        queryWrapper.eq(type != null, LogTaskEntity::getType, type);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogTaskEntity::getStatus, status);
        // 创建时间范围（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        queryWrapper.ge(createTimeStart != null, LogTaskEntity::getCreateTime, createTimeStart);
        Date createTimeEnd = dto.getCreateTimeEnd();
        queryWrapper.le(createTimeEnd != null, LogTaskEntity::getCreateTime, createTimeEnd);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogTaskEntity> getSelectWrapper(LogTaskDto dto) {
        LambdaQueryWrapper<LogTaskEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(LogTaskEntity::getId, LogTaskEntity::getTaskId, LogTaskEntity::getBeanName,
                LogTaskEntity::getJson, LogTaskEntity::getTaskTime, LogTaskEntity::getType,
                LogTaskEntity::getStatus, LogTaskEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LogTaskDto> page(LogTaskDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LogTaskDto.class);
    }

    @Override
    public List<LogTaskDto> list(LogTaskDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LogTaskDto.class);
    }

    @Override
    public LogTaskDto getById(Long id) {
        LambdaQueryWrapper<LogTaskEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(LogTaskEntity::getId, id);
        queryWrapper.select(LogTaskEntity::getId, LogTaskEntity::getErrorInfo);

        return BeanUtil.convert(getOne(queryWrapper), LogTaskDto.class);
    }

    @Override
    public long count(LogTaskDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public void saveAsync(LogTaskEntity entity) {
        save(entity);
    }

    @Override
    public void clear() {
        // 删除定时任务日志
        LambdaQueryWrapper<LogTaskEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.lt(LogTaskEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), clearDaysLimit));
        remove(queryWrapper);
    }

}
