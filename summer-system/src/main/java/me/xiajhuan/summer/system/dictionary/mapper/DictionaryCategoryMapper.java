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

package me.xiajhuan.summer.system.dictionary.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.xiajhuan.summer.system.dictionary.entity.DictionaryCategoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典类别 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
public interface DictionaryCategoryMapper extends BaseMapper<DictionaryCategoryEntity> {

    /**
     * 判断是否存在
     *
     * @param code 类别编码
     * @return 返回 {@code null} 则不存在
     */
    @Select("SELECT 1 FROM dictionary_category WHERE code = #{code} LIMIT 1")
    Integer exist(@Param("code") String code);

    /**
     * 查询列表（忽略数据权限）
     *
     * @param queryWrapper {@link Wrapper}
     * @return 字典类别Entity列表
     */
    @InterceptorIgnore
    List<DictionaryCategoryEntity> selectList(@Param(Constants.WRAPPER) Wrapper<DictionaryCategoryEntity> queryWrapper);

}
