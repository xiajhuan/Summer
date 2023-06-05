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

package me.xiajhuan.summer.system.extend.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.TreeConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.TreeUtil;
import me.xiajhuan.summer.system.extend.dto.ExtendRegionDto;
import me.xiajhuan.summer.system.extend.entity.ExtendRegionEntity;
import me.xiajhuan.summer.system.extend.mapper.ExtendRegionMapper;
import me.xiajhuan.summer.system.extend.service.ExtendRegionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区域 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/4/27
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class ExtendRegionServiceImpl extends ServiceImpl<ExtendRegionMapper, ExtendRegionEntity> implements ExtendRegionService {

    @Override
    public List<ExtendRegionDto> treeList(boolean needFilter) {
        final List<ExtendRegionEntity> entityList;
        if (needFilter) {
            // 过滤数据权限
            entityList = baseMapper.getList();
        } else {
            // 忽略数据权限
            entityList = baseMapper.selectList(addSelectFieldForComponent());
        }

        if (entityList.size() > 0) {
            // 构建行政区域树形结构列表
            return TreeUtil.buildDto(ExtendRegionDto.class, BeanUtil.convert(entityList, ExtendRegionDto.class),
                    TreeConst.ROOT, TreeConst.Extra.REGION);
        }
        return null;
    }

    @Override
    public ExtendRegionDto getById(Long id) {
        return BeanUtil.convert(baseMapper.getById(id), ExtendRegionDto.class);
    }

    @Override
    public void add(ExtendRegionDto dto) {
        checkLevel(dto);

        save(BeanUtil.convert(dto, ExtendRegionEntity.class));
    }

    @Override
    public void update(ExtendRegionDto dto) {
        checkLevel(dto);

        updateById(BeanUtil.convert(dto, ExtendRegionEntity.class));
    }

    @Override
    public void delete(Long id) {
        // 判断是否存在下级区域
        LambdaQueryWrapper<ExtendRegionEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ExtendRegionEntity::getParentId, id);
        if (count(queryWrapper) > 0) {
            throw ValidationException.of(ErrorCode.REGION_SUB_DELETE_ERROR);
        }

        removeById(id);
    }

    @Override
    public List<ExtendRegionDto> listByParentId(Long parentId) {
        LambdaQueryWrapper<ExtendRegionEntity> queryWrapper = addSelectFieldForComponent();
        queryWrapper.eq(ExtendRegionEntity::getParentId, parentId);

        return BeanUtil.convert(baseMapper.selectList(queryWrapper), ExtendRegionDto.class);
    }

    /**
     * 添加查询字段（前端组件需要的字段）
     *
     * @return {@link LambdaQueryWrapper}
     */
    private LambdaQueryWrapper<ExtendRegionEntity> addSelectFieldForComponent() {
        LambdaQueryWrapper<ExtendRegionEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ExtendRegionEntity::getId, ExtendRegionEntity::getParentId, ExtendRegionEntity::getName);

        return queryWrapper;
    }

    /**
     * 校验区域级别
     *
     * @param dto 行政区域Dto
     */
    private void checkLevel(ExtendRegionDto dto) {
        // 上级区域级别，没有时为-1
        int parentLevel = -1;
        long parentId = dto.getParentId();
        if (parentId > 0L) {
            parentLevel = baseMapper.getLevelById(parentId);
        }

        // 上级区域级别必须比自身大一级
        if (dto.getLevel() - parentLevel != 1) {
            throw ValidationException.of(ErrorCode.SUPERIOR_REGION_LEVEL_ERROR);
        }
    }

}
