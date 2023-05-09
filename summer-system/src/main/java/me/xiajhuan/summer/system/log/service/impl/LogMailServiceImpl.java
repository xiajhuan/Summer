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
import me.xiajhuan.summer.system.log.dto.LogMailDto;
import me.xiajhuan.summer.system.log.entity.LogMailEntity;
import me.xiajhuan.summer.system.log.mapper.LogMailMapper;
import me.xiajhuan.summer.system.log.service.LogMailService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 邮件日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/5/9
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class LogMailServiceImpl extends ServiceImpl<LogMailMapper, LogMailEntity> implements LogMailService, MpHelper<LogMailDto, LogMailEntity> {

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
        clearDaysLimit = setting.getInt("mail.clear-days-limit", "Log", -90);
    }

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LogMailEntity> getQueryWrapper(LogMailDto dto) {
        LambdaQueryWrapper<LogMailEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 邮件名称
        String mailName = dto.getMailName();
        queryWrapper.eq(StrUtil.isNotBlank(mailName), LogMailEntity::getMailName, mailName);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogMailEntity::getStatus, status);
        // 创建时间范围（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        queryWrapper.ge(createTimeStart != null, LogMailEntity::getCreateTime, createTimeStart);
        Date createTimeEnd = dto.getCreateTimeEnd();
        queryWrapper.le(createTimeEnd != null, LogMailEntity::getCreateTime, createTimeEnd);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogMailEntity> getSelectWrapper(LogMailDto dto) {
        LambdaQueryWrapper<LogMailEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(LogMailEntity::getId, LogMailEntity::getMailName, LogMailEntity::getSender,
                LogMailEntity::getReceiversTo, LogMailEntity::getReceiversCc, LogMailEntity::getReceiversBcc,
                LogMailEntity::getSubject, LogMailEntity::getContent, LogMailEntity::getStatus,
                LogMailEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LogMailDto> page(LogMailDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LogMailDto.class);
    }

    @Override
    public List<LogMailDto> list(LogMailDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LogMailDto.class);
    }

    @Override
    public long count(LogMailDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public void saveAsync(LogMailEntity entity) {
        save(entity);
    }

    @Override
    public void clear() {
        // 删除邮件日志
        LambdaQueryWrapper<LogMailEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.lt(LogMailEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), clearDaysLimit));
        remove(queryWrapper);
    }

}
