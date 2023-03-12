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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.TreeExtraConst;
import me.xiajhuan.summer.core.entity.SimpleBaseEntity;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.admin.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityDeptEntity;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.admin.common.security.mapper.SecurityDeptMapper;
import me.xiajhuan.summer.admin.common.security.service.SecurityDeptService;
import me.xiajhuan.summer.core.utils.ConvertUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.core.utils.TreeUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityDeptServiceImpl extends ServiceImpl<SecurityDeptMapper, SecurityDeptEntity> implements SecurityDeptService, MpCommonOperation<SecurityDeptDto, SecurityDeptEntity> {

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityDeptEntity> getSelectWrapper(Class<SecurityDeptEntity> entityClass) {
        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(SecurityDeptEntity::getId, SecurityDeptEntity::getParentId, SecurityDeptEntity::getName,
                SecurityDeptEntity::getWeight, SimpleBaseEntity::getCreateTime, SecurityDeptEntity::getParentName);

        return queryWrapper;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public List<SecurityDeptDto> treeList() {
        Set<Long> param = null;
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // 这里不细分数据权限，非超级管理员，一律只能查询本部门及本部门下子部门的数据
        boolean isGeneral = false;
        if (UserTypeEnum.GENERAL.getValue() == loginUser.getUserType()) {
            isGeneral = true;
            param = loginUser.getDeptAndChildIdSet();
        }

        // 部门列表
        List<SecurityDeptDto> dtoList = ConvertUtil.convert(
                baseMapper.getList(param), SecurityDeptDto.class);

        // 构建部门树形结构列表
        return isGeneral ? TreeUtil.buildWithD(SecurityDeptDto.class, dtoList, loginUser.getDeptId(), TreeExtraConst.TREE_DEPT)
                : TreeUtil.buildWithD(SecurityDeptDto.class, dtoList, 0L, TreeExtraConst.TREE_DEPT);
    }

    @Override
    public void add(SecurityDeptDto dto) {

    }

    @Override
    public void update(SecurityDeptDto dto) {

    }

    @Override
    public void delete(String[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

}
