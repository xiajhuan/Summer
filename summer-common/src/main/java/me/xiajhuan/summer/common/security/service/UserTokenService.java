package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.security.dto.UserTokenDto;
import me.xiajhuan.summer.common.security.entity.UserTokenEntity;

/**
 * 用户Token Service
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public interface UserTokenService extends IService<UserTokenEntity> {

    /**
     * 生成Token
     *
     * @param userId 用户ID
     * @return 用户TokenDto
     */
    UserTokenDto generateToken(Long userId);

    /**
     * 用户退出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

}
