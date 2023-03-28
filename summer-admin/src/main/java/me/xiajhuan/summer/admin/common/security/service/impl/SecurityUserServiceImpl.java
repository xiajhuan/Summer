/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.admin.common.security.dto.SecurityUserDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.admin.common.security.mapper.SecurityUserMapper;
import me.xiajhuan.summer.admin.common.security.service.SecurityUserService;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import org.springframework.stereotype.Service;

/**
 * 用户 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityUserServiceImpl extends ServiceImpl<SecurityUserMapper, SecurityUserEntity> implements SecurityUserService, MpHelper<SecurityUserDto, SecurityUserEntity> {

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getSelectWrapper(Class<SecurityUserEntity> entityClass) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getRealName,
                SecurityUserEntity::getHeadUrl, SecurityUserEntity::getGender, SecurityUserEntity::getEmail,
                SecurityUserEntity::getMobile, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getQueryWrapper(SecurityUserDto dto) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getSelectWrapper(currentModelClass());
        // 动态Sql查询条件
        // 用户名
        String username = dto.getUsername();
        queryWrapper.eq(StrUtil.isNotBlank(username), SecurityUserEntity::getUsername, username);
        // 性别
        Integer gender = dto.getGender();
        queryWrapper.eq(gender != null, SecurityUserEntity::getGender, gender);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public SecurityUserDto getByUsername(String username) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getDeptId, SecurityUserEntity::getUsername,
                SecurityUserEntity::getPassword, SecurityUserEntity::getRealName, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getDataScope);
        queryWrapper.eq(SecurityUserEntity::getUsername, username);

        return ConvertUtil.convert(getOne(queryWrapper), SecurityUserDto.class);
    }

    @Override
    public long countByDeptId(Long deptId) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityUserEntity::getDeptId, deptId);
        return count(queryWrapper);
    }

}
