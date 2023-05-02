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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.xiajhuan.summer.system.extend.entity.ExtendOssEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 对象存储 Mapper
 *
 * @author xiajhuan
 * @date 2023/4/29
 */
public interface ExtendOssMapper extends BaseMapper<ExtendOssEntity> {

    /**
     * 获取路径（相对路径）集合
     *
     * @param idSet ID集合
     * @return 路径（相对路径）集合
     */
    @Select("<script>" +
            "SELECT path FROM extend_oss " +
            "<where> " +
            "id IN " +
            "<foreach collection=\"idSet\" item=\"id\" separator=\",\" open=\"(\" close=\")\">" +
            " #{id} " +
            "</foreach>" +
            "</where>" +
            "</script>")
    Set<String> getPathSet(@Param("idSet") Set<Long> idSet);

}
