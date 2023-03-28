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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.admin.common.security.dto.SecurityRoleDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityRoleEntity;
import me.xiajhuan.summer.admin.common.security.mapper.SecurityRoleMapper;
import me.xiajhuan.summer.admin.common.security.service.SecurityRoleService;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import org.springframework.stereotype.Service;

/**
 * 角色 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityRoleServiceImpl extends ServiceImpl<SecurityRoleMapper, SecurityRoleEntity> implements SecurityRoleService, MpHelper<SecurityRoleDto, SecurityRoleEntity> {

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> getSelectWrapper(Class<SecurityRoleEntity> entityClass) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(SecurityRoleEntity::getId, SecurityRoleEntity::getName, SecurityRoleEntity::getDesc,
                SecurityRoleEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> getQueryWrapper(SecurityRoleDto dto) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = getSelectWrapper(currentModelClass());
        // 动态Sql查询条件
        // 角色名称
        String name = dto.getName();
        queryWrapper.eq(StrUtil.isNotBlank(name), SecurityRoleEntity::getName, name);

        return queryWrapper;
    }

    //*******************MpHelper覆写结束********************

    @Override
    public IPage<SecurityRoleDto> page(SecurityRoleDto dto) {
        return ConvertUtil.convert(page(handlePageSort(dto), getQueryWrapper(dto)), SecurityRoleDto.class);
    }

    @Override
    public void add(SecurityRoleDto dto) {

    }

    @Override
    public void update(SecurityRoleDto dto) {

    }

    @Override
    public void delete(Long[] ids) {

    }

}
