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
import me.xiajhuan.summer.system.dictionary.entity.DictionaryItemEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典项 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/24
 */
public interface DictionaryItemMapper extends BaseMapper<DictionaryItemEntity> {

    /**
     * 判断是否存在
     *
     * @param categoryId 类别ID
     * @param value      值
     * @return 返回 {@code null} 则不存在
     */
    @Select("SELECT 1 FROM dictionary_item WHERE category_id = #{categoryId} AND value = #{value} LIMIT 1")
    Integer exist(@Param("categoryId") long categoryId, @Param("value") String value);

    /**
     * 查询列表（忽略数据权限）
     *
     * @param queryWrapper {@link Wrapper}
     * @return 字典项Entity列表
     */
    @InterceptorIgnore
    List<DictionaryItemEntity> selectList(@Param(Constants.WRAPPER) Wrapper<DictionaryItemEntity> queryWrapper);

}
