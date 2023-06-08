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
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.ServletUtil;
import me.xiajhuan.summer.system.log.dto.LogLoginDto;
import me.xiajhuan.summer.system.log.entity.LogLoginEntity;
import me.xiajhuan.summer.system.log.mapper.LogLoginMapper;
import me.xiajhuan.summer.system.log.service.LogLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 登录日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLoginEntity> implements LogLoginService, MpHelper<LogLoginDto, LogLoginEntity> {

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
        clearDaysLimit = setting.getInt("login.clear-days-limit", "Log", -30);
    }

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<LogLoginEntity> getQueryWrapper(LogLoginDto dto) {
        LambdaQueryWrapper<LogLoginEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 用户操作
        Integer operation = dto.getOperation();
        queryWrapper.eq(operation != null, LogLoginEntity::getOperation, operation);
        // 登录状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogLoginEntity::getStatus, status);
        // 创建时间范围（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        queryWrapper.ge(createTimeStart != null, LogLoginEntity::getCreateTime, createTimeStart);
        Date createTimeEnd = dto.getCreateTimeEnd();
        queryWrapper.le(createTimeEnd != null, LogLoginEntity::getCreateTime, createTimeEnd);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogLoginEntity> getSelectWrapper(LogLoginDto dto) {
        LambdaQueryWrapper<LogLoginEntity> queryWrapper = getQueryWrapper(dto);
        // 查询字段
        queryWrapper.select(LogLoginEntity::getId, LogLoginEntity::getLoginUser, LogLoginEntity::getOperation,
                LogLoginEntity::getStatus, LogLoginEntity::getUserAgent, LogLoginEntity::getIp,
                LogLoginEntity::getCreateTime);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<LogLoginDto> page(LogLoginDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), LogLoginDto.class);
    }

    @Override
    public List<LogLoginDto> list(LogLoginDto dto) {
        return BeanUtil.convert(list(getSortWrapper(dto)), LogLoginDto.class);
    }

    @Override
    public long count(LogLoginDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public void saveAsync(String loginUser, Integer loginOperation, Integer loginStatus, HttpServletRequest request) {
        // 构建登录日志
        LogLoginEntity entity = LogLoginEntity.builder()
                .loginUser(loginUser)
                .operation(loginOperation)
                .status(loginStatus)
                .userAgent(ServletUtil.getUserAgent(request))
                .ip(ServletUtil.getClientIP(request))
                .createTime(DateUtil.date()).build();

        save(entity);
    }

    @Override
    public void clear() {
        // 删除登录日志
        LambdaQueryWrapper<LogLoginEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.lt(LogLoginEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), clearDaysLimit));
        remove(queryWrapper);
    }

}
