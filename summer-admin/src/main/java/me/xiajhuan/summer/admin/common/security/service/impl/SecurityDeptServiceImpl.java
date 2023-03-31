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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.admin.common.security.service.SecurityUserService;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.TreeConst;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.custom.BusinessException;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.admin.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityDeptEntity;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.admin.common.security.mapper.SecurityDeptMapper;
import me.xiajhuan.summer.admin.common.security.service.SecurityDeptService;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.core.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityDeptServiceImpl extends ServiceImpl<SecurityDeptMapper, SecurityDeptEntity> implements SecurityDeptService {

    @Resource
    private SecurityUserService securityUserService;

    @Override
    public List<SecurityDeptDto> treeList() {
        Set<Long> idSet = null;
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // 这里不细分数据权限，非超级管理员，一律只能查询本部门及本部门下子部门的数据
        boolean isGeneral = false;
        if (UserTypeEnum.GENERAL.getValue() == loginUser.getUserType()) {
            isGeneral = true;
            idSet = loginUser.getDeptAndChildIdSet();
        }

        // 部门列表
        List<SecurityDeptDto> dtoList = BeanUtil.convert(
                baseMapper.getList(idSet), SecurityDeptDto.class);

        // 构建部门树形结构列表
        return isGeneral ? TreeUtil.buildDto(SecurityDeptDto.class, dtoList, loginUser.getDeptId(), TreeConst.Extra.DEPT)
                : TreeUtil.buildDto(SecurityDeptDto.class, dtoList, TreeConst.ROOT, TreeConst.Extra.DEPT);
    }

    @Override
    public SecurityDeptDto getById(Long id) {
        return BeanUtil.convert(baseMapper.getById(id), SecurityDeptDto.class);
    }

    @Override
    public void add(SecurityDeptDto dto) {
        SecurityDeptEntity entity = BeanUtil.convert(dto, SecurityDeptEntity.class);
        // 所有上级部门ID
        entity.setParentIdAll(getParentIdAll(entity.getParentId()));

        save(entity);
    }

    @Override
    public void update(SecurityDeptDto dto) {
        SecurityDeptEntity entity = BeanUtil.convert(dto, SecurityDeptEntity.class);

        long id = entity.getId().longValue();
        long parentId = entity.getParentId().longValue();
        // 上级部门不能为自身或下级部门
        if (id == parentId || getChildIdSet(id).contains(parentId)) {
            throw BusinessException.of(ErrorCode.SUPERIOR_DEPT_ERROR);
        }

        // 所有上级部门ID
        entity.setParentIdAll(getParentIdAll(parentId));

        updateById(entity);
    }

    @Override
    public void delete(Long id) {
        // 判断是否存在子部门或用户
        if (getChildIdSet(id).size() > 0 || securityUserService.countByDeptId(id) > 0) {
            throw BusinessException.of(ErrorCode.DEPT_SUB_DELETE_ERROR);
        }

        removeById(id);
    }

    @Override
    public Set<Long> getChildIdSet(Long deptId) {
        Set<Long> childIdSet = CollUtil.newHashSet();

        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SecurityDeptEntity::getId);
        queryWrapper.like(SecurityDeptEntity::getParentIdAll, deptId);
        List<SecurityDeptEntity> entityList = list(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            entityList.forEach(entity -> childIdSet.add(entity.getId()));
        }
        return childIdSet;
    }

    /**
     * 获取所有上级部门ID
     *
     * @param parentId 上级部门ID
     * @return 所有上级部门ID
     */
    private String getParentIdAll(Long parentId) {
        // 顶级部门，无上级部门
        if (TreeConst.ROOT.equals(parentId)) {
            return String.valueOf(parentId);
        }

        // 所有部门的id,parentId（到这里至少已经存在顶级部门）
        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SecurityDeptEntity::getId, SecurityDeptEntity::getParentId);
        List<SecurityDeptEntity> entityList = list(queryWrapper);

        // 转换成Map，Key：ID Value：部门Entity
        Map<Long, SecurityDeptEntity> idToDeptMap = MapUtil.newHashMap(entityList.size());
        entityList.forEach(entity -> idToDeptMap.put(entity.getId(), entity));

        // 递归获取所有上级部门ID集合
        Set<Long> parentIdSet = CollUtil.newHashSet();
        getParentIdSetRecursion(parentId, idToDeptMap, parentIdSet);

        return parentIdSet.stream().map(String::valueOf)
                .collect(Collectors.joining(StrPool.COMMA));
    }

    /**
     * 递归获取所有上级部门ID集合
     *
     * @param parentId    上级部门ID
     * @param idToDeptMap 部门ID和部门Entity Map
     * @param parentIdSet 上级部门ID集合
     */
    private void getParentIdSetRecursion(Long parentId, Map<Long, SecurityDeptEntity> idToDeptMap, Set<Long> parentIdSet) {
        if (!TreeConst.ROOT.equals(parentId)) {
            // 非顶级部门，必然存在上级部门
            SecurityDeptEntity parent = idToDeptMap.get(parentId);
            if (parent != null) {
                // 递归
                getParentIdSetRecursion(parent.getParentId(), idToDeptMap, parentIdSet);
            }
            // 递归返回
            parentIdSet.add(parentId);
        }
    }

}
