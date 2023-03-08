package me.xiajhuan.summer.common.security.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.common.constant.DataSourceConst;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.security.dto.UserTokenDto;
import me.xiajhuan.summer.common.security.entity.UserTokenEntity;
import me.xiajhuan.summer.common.security.mapper.UserTokenMapper;
import me.xiajhuan.summer.common.security.service.UserTokenService;
import me.xiajhuan.summer.common.utils.SecurityUtil;
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
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserTokenEntity> implements UserTokenService {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    @Override
    public UserTokenDto generateToken(Long userId) {
        LambdaQueryWrapper<UserTokenEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserTokenEntity::getUserId, userId);
        UserTokenEntity tokenEntity = getOne(queryWrapper);

        // 计算Token过期时间
        int expireHour = setting.getInt("token.expire", "Security");
        Date expireTime = DateUtil.offsetHour(DateUtil.date(), expireHour);

        String accessToken;
        // 判断是否生成过Token
        if (tokenEntity == null) {
            accessToken = SecurityUtil.generateToken();
            // 构建用户Token
            tokenEntity = UserTokenEntity.builder()
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
            UserTokenEntity tokenNew = UserTokenEntity.builder()
                    .token(accessToken).expireTime(expireTime).build();

            updateById(tokenNew);
        }

        UserTokenDto tokenDto = new UserTokenDto();
        tokenDto.setToken(accessToken);
        tokenDto.setExpireTime(expireHour * 3600);

        return tokenDto;
    }

    @Override
    public void logout(Long userId) {
        // 将Token过期时间设值为10s前（使Token失效）
        LambdaUpdateWrapper<UserTokenEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserTokenEntity::getExpireTime, DateUtil.offsetSecond(DateUtil.date(), -10));
        updateWrapper.eq(UserTokenEntity::getUserId, updateWrapper);

        update(updateWrapper);
    }

}
