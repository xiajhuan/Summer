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

package me.xiajhuan.summer.system.monitor.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.cache.server.CacheServerFactory;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.system.monitor.dto.MonitorOnlineDto;
import me.xiajhuan.summer.system.monitor.entity.MonitorOnlineEntity;
import me.xiajhuan.summer.system.monitor.mapper.MonitorOnlineMapper;
import me.xiajhuan.summer.system.monitor.service.MonitorOnlineService;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.*;

import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.service.SecurityService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 在线用户 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/12
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class MonitorOnlineServiceImpl extends ServiceImpl<MonitorOnlineMapper, MonitorOnlineEntity> implements MonitorOnlineService, MpHelper<MonitorOnlineDto, MonitorOnlineEntity> {

    @Lazy
    @Resource
    private SecurityService securityService;

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<MonitorOnlineEntity> getQueryWrapper(MonitorOnlineDto dto) {
        LambdaQueryWrapper<MonitorOnlineEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 排除Token过期的
        queryWrapper.gt(MonitorOnlineEntity::getExpireTime, DateUtil.date());
        // 创建者（用户名）
        String createBy = dto.getCreateBy();
        queryWrapper.eq(StrUtil.isNotBlank(createBy), MonitorOnlineEntity::getCreateBy, createBy);
        // 本部门名称
        String deptName = dto.getDeptName();
        queryWrapper.eq(StrUtil.isNotBlank(deptName), MonitorOnlineEntity::getDeptName, deptName);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<MonitorOnlineDto> page(MonitorOnlineDto dto) {
        return BeanUtil.convert(page(handlePageSort(dto), getSelectWrapper(dto)), MonitorOnlineDto.class);
    }

    @Override
    public void saveOrUpdateAsync(SecurityUserEntity userEntity, Integer expireTime) {
        Date now = DateUtil.date();
        Date expire = DateUtil.offsetSecond(now, expireTime);
        String realName = userEntity.getRealName();
        String deptName = String.valueOf(CacheServerFactory.getCacheServer()
                .getHash(dept(userEntity.getDeptId()), "name"));

        Long userId = userEntity.getId();
        // 查询原来的在线用户
        MonitorOnlineEntity entity = getOne(getUserIdCondition(userId));

        if (entity == null) {
            // 构建在线用户
            entity = MonitorOnlineEntity.builder()
                    .createBy(userEntity.getUsername())
                    .createTime(now)
                    .userId(userId)
                    .realName(realName)
                    .loginTime(now)
                    .expireTime(expire)
                    .deptName(deptName).build();
        } else {
            entity.setRealName(realName);
            entity.setDeptName(deptName);
            entity.setLoginTime(now);
            entity.setExpireTime(expire);
        }

        saveOrUpdate(entity);
    }

    @Override
    public void delete(Long userId) {
        remove(getUserIdCondition(userId));
    }

    @Override
    public void deleteBatch(Set<Long> userIdSet) {
        LambdaQueryWrapper<MonitorOnlineEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.in(MonitorOnlineEntity::getUserId, userIdSet);
        remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void kick(Long[] userIds) {
        if (UserTypeEnum.SUPER_ADMIN.getValue() != SecurityUtil.getLoginUser().getUserType()) {
            // 只有超级管理员可以踢出用户
            throw ValidationException.of(ErrorCode.KICK_OUT_ERROR);
        }

        // 踢出用户
        Set<Long> userIdSet = Arrays.stream(userIds).collect(Collectors.toSet());

        deleteBatch(userIdSet);

        userIdSet.forEach(userId -> securityService.logout(userId, false));
    }

    /**
     * 获取{@link LambdaQueryWrapper}（用户ID条件）
     *
     * @param userId 用户ID
     * @return {@link LambdaQueryWrapper}
     */
    private LambdaQueryWrapper<MonitorOnlineEntity> getUserIdCondition(Long userId) {
        LambdaQueryWrapper<MonitorOnlineEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(MonitorOnlineEntity::getUserId, userId);
        return queryWrapper;
    }

}
