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

package me.xiajhuan.summer.system.locale.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import me.xiajhuan.summer.core.mp.mapper.CustomBaseMapper;
import me.xiajhuan.summer.system.locale.entity.LocaleInternationalNameEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 国际化名称 Mapper
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@InterceptorIgnore
public interface LocaleInternationalNameMapper extends CustomBaseMapper<LocaleInternationalNameEntity> {

    @Select("SELECT 1 FROM locale_international_name WHERE" +
            " table_name = #{param.tableName} AND field_name = #{param.fieldName} AND" +
            " field_value = #{param.fieldValue} AND locale = #{param.locale} LIMIT 1")
    Integer exist(@Param("param") LocaleInternationalNameEntity entity);

}
