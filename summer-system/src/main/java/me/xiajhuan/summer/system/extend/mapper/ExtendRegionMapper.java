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

package me.xiajhuan.summer.system.extend.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.xiajhuan.summer.system.extend.entity.ExtendRegionEntity;
import me.xiajhuan.summer.system.extend.enums.RegionLevelEnum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 行政区域 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/26
 */
public interface ExtendRegionMapper extends BaseMapper<ExtendRegionEntity> {

    /**
     * 获取列表
     *
     * @return 行政区域Entity列表
     */
    List<ExtendRegionEntity> getList();

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 行政区域Entity
     */
    ExtendRegionEntity getById(@Param("id") long id);

    /**
     * 查询列表（忽略数据权限）
     *
     * @param queryWrapper {@link Wrapper}
     * @return 行政区域Entity列表
     */
    @InterceptorIgnore
    List<ExtendRegionEntity> selectList(@Param(Constants.WRAPPER) Wrapper<ExtendRegionEntity> queryWrapper);

    /**
     * 根据ID获取区域级别，参考{@link RegionLevelEnum}（忽略数据权限）
     *
     * @param id ID
     * @return 区域级别
     */
    @Select("SELECT level FROM extend_region WHERE id = #{id}")
    @InterceptorIgnore
    Integer getLevelById(@Param("id") long id);

}
