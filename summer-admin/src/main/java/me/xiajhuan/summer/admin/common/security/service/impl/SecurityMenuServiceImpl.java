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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.admin.common.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityMenuEntity;
import me.xiajhuan.summer.admin.common.security.mapper.SecurityMenuMapper;
import me.xiajhuan.summer.admin.common.security.service.SecurityMenuService;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityMenuServiceImpl extends ServiceImpl<SecurityMenuMapper, SecurityMenuEntity> implements SecurityMenuService {

    @Override
    public List<SecurityMenuDto> treeList() {
        return null;
    }

    @Override
    public SecurityMenuDto getById(Long id) {
        return null;
    }


    @Override
    public void add(SecurityMenuDto dto) {

    }

    @Override
    public void update(SecurityMenuDto dto) {

    }

    @Override
    public void delete(Long id) {

    }

}
