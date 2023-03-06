package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.security.dto.UserDto;
import me.xiajhuan.summer.common.security.entity.UserEntity;

/**
 * 用户 Service
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 根据用户名获取
     *
     * @param username 用户名
     * @return 用户Dto
     */
    UserDto getByUsername(String username);

}
