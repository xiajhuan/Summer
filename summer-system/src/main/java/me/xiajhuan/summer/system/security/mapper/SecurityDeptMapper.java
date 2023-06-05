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

package me.xiajhuan.summer.system.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.system.security.entity.SecurityDeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 部门 Mapper
 *
 * @author xiajhuan
 * @date 2023/3/7
 */
public interface SecurityDeptMapper extends BaseMapper<SecurityDeptEntity> {

    /**
     * 获取列表
     *
     * @param idSet ID集合
     * @return 部门Entity列表
     */
    List<SecurityDeptEntity> getList(@Param("idSet") Set<Long> idSet);

    /**
     * 根据ID获取
     *
     * @param id ID
     * @return 部门Entity
     */
    SecurityDeptEntity getById(@Param("id") long id);

}
