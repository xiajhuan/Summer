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

import cn.hutool.core.date.DateUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.admin.common.security.dto.SecurityUserTokenDto;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserTokenEntity;
import me.xiajhuan.summer.admin.common.security.mapper.SecurityUserTokenMapper;
import me.xiajhuan.summer.admin.common.security.service.SecurityUserTokenService;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户Token ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityUserTokenServiceImpl extends ServiceImpl<SecurityUserTokenMapper, SecurityUserTokenEntity> implements SecurityUserTokenService {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    @Override
    public SecurityUserTokenDto generateToken(Long userId) {
        LambdaQueryWrapper<SecurityUserTokenEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SecurityUserTokenEntity::getUserId, userId);
        SecurityUserTokenEntity tokenEntity = getOne(queryWrapper);

        // 计算Token过期时间
        int expireHour = setting.getInt("token.expire", "Security", 12);
        Date expireTime = DateUtil.offsetHour(DateUtil.date(), expireHour);

        String accessToken;
        // 判断是否生成过Token
        if (tokenEntity == null) {
            accessToken = SecurityUtil.generateToken();
            // 构建用户Token
            tokenEntity = SecurityUserTokenEntity.builder()
                    .userId(userId).token(accessToken)
                    .expireTime(expireTime).build();

            save(tokenEntity);
        } else {
            accessToken = tokenEntity.getToken();
            // 判断Token是否过期
            if (tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                accessToken = SecurityUtil.generateToken();
            }

            // 更新用户Token
            SecurityUserTokenEntity tokenNew = SecurityUserTokenEntity.builder()
                    .token(accessToken).expireTime(expireTime).build();

            updateById(tokenNew);
        }

        SecurityUserTokenDto tokenDto = new SecurityUserTokenDto();
        tokenDto.setToken(accessToken);
        tokenDto.setExpireTime(expireHour * 3600);

        return tokenDto;
    }

    @Override
    public void logout(Long userId) {
        // 将Token过期时间设值为10s前（使Token失效）
        LambdaUpdateWrapper<SecurityUserTokenEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(SecurityUserTokenEntity::getExpireTime, DateUtil.offsetSecond(DateUtil.date(), -10));
        updateWrapper.eq(SecurityUserTokenEntity::getUserId, updateWrapper);

        update(updateWrapper);
    }

}
