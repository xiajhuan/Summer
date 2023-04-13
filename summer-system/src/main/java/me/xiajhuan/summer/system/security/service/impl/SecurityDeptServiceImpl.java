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

package me.xiajhuan.summer.system.security.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.CacheConst;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.TimeUnitConst;
import me.xiajhuan.summer.core.constant.TreeConst;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.core.utils.TreeUtil;
import me.xiajhuan.summer.system.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.entity.SecurityDeptEntity;
import me.xiajhuan.summer.system.security.entity.SecurityRoleDeptEntity;
import me.xiajhuan.summer.system.security.mapper.SecurityDeptMapper;
import me.xiajhuan.summer.system.security.mapper.SecurityRoleDeptMapper;
import me.xiajhuan.summer.system.security.service.SecurityDeptService;
import me.xiajhuan.summer.system.security.service.SecurityUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.dept;

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
@DS(DataSourceConst.SYSTEM)
public class SecurityDeptServiceImpl extends ServiceImpl<SecurityDeptMapper, SecurityDeptEntity> implements SecurityDeptService {

    @Resource
    private SecurityUserService securityUserService;

    @Resource
    private SecurityRoleDeptMapper securityRoleDeptMapper;

    @Override
    public List<SecurityDeptDto> treeList(boolean needAll) {
        Set<Long> idSet = null;
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // 非超级管理员，一律只能查询本部门及本部门下子部门
        boolean isGeneral = false;
        if (!needAll && UserTypeEnum.GENERAL.getValue() == loginUser.getUserType()) {
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

        if (save(entity)) {
            // 缓存
            cache(entity, CacheServerFactory.getCacheServer());
        }
    }

    @Override
    public void update(SecurityDeptDto dto) {
        long id = dto.getId().longValue();
        long parentId = dto.getParentId().longValue();
        // 上级部门不能为自身或下级部门
        if (id == parentId || getChildIdSet(id).contains(parentId)) {
            throw ValidationException.of(ErrorCode.SUPERIOR_DEPT_ERROR);
        }

        SecurityDeptEntity entity = BeanUtil.convert(dto, SecurityDeptEntity.class);
        // 所有上级部门ID
        entity.setParentIdAll(getParentIdAll(parentId));

        if (updateById(entity)) {
            // 更新缓存
            cache(entity, CacheServerFactory.getCacheServer());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 判断是否存在子部门
        if (getChildIdSet(id).size() > 0) {
            throw ValidationException.of(ErrorCode.DEPT_SUB_DELETE_ERROR);
        }

        // 判断部门下是否存在用户
        if (securityUserService.count(SecurityUserDto.builder().deptId(id).build()) > 0) {
            throw ValidationException.of(ErrorCode.DEPT_USER_DELETE_ERROR);
        }

        if (removeById(id)) {
            // 删除缓存
            CacheServerFactory.getCacheServer().delete(dept(id), CacheConst.Value.HASH);

            // 删除角色部门关联
            LambdaQueryWrapper<SecurityRoleDeptEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SecurityRoleDeptEntity::getDeptId, id);
            securityRoleDeptMapper.delete(queryWrapper);
        }
    }

    @Override
    public Set<Long> getChildIdSet(Long deptId) {
        Set<Long> childIdSet = CollUtil.newHashSet();

        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(SecurityDeptEntity::getParentIdAll, deptId);
        queryWrapper.select(SecurityDeptEntity::getId);
        List<SecurityDeptEntity> entityList = list(queryWrapper);

        if (entityList.size() > 0) {
            entityList.forEach(entity -> childIdSet.add(entity.getId()));
        }
        return childIdSet;
    }

    @Override
    public void cacheAll() {
        // 所有部门
        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SecurityDeptEntity::getId, SecurityDeptEntity::getName, SecurityDeptEntity::getWeight,
                SecurityDeptEntity::getParentId, SecurityDeptEntity::getParentIdAll);
        List<SecurityDeptEntity> entityList = list(queryWrapper);
        if (entityList.size() > 0) {
            CacheServer cacheServer = CacheServerFactory.getCacheServer();
            entityList.forEach(entity -> cache(entity, cacheServer));
        }
    }

    /**
     * 缓存部门
     *
     * @param entity      部门Entity
     * @param cacheServer 缓存服务
     */
    private void cache(SecurityDeptEntity entity, CacheServer cacheServer) {
        Map<String, Object> hash = MapUtil.newHashMap(4, true);
        hash.put("name", entity.getName());
        hash.put("weight", entity.getWeight());
        hash.put("parentId", entity.getParentId());
        hash.put("parentIdAll", entity.getParentIdAll());

        cacheServer.setHash(dept(entity.getId()), hash, TimeUnitConst.WEEK);
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
