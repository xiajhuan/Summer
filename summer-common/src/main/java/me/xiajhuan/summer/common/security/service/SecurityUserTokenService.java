package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.security.dto.SecurityUserTokenDto;
import me.xiajhuan.summer.common.security.entity.SecurityUserTokenEntity;

/**
 * 用户Token Service
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public interface SecurityUserTokenService extends IService<SecurityUserTokenEntity> {

    /**
     * 生成Token
     *
     * @param userId 用户ID
     * @return 用户TokenDto
     */
    SecurityUserTokenDto generateToken(Long userId);

    /**
     * 用户退出
     *
     * @param userId 用户ID
     */
    void logout(Long userId);

}
