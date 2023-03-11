package me.xiajhuan.summer.common.security.service;

import me.xiajhuan.summer.common.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.common.security.entity.SecurityUserTokenEntity;
import me.xiajhuan.summer.common.security.login.LoginUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 权限相关 Service
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
public interface SecurityService {

    //*******************认证授权********************

    /**
     * 获取用户菜单权限
     *
     * @param loginUser 登录用户信息
     * @return 用户菜单权限
     */
    Set<String> getPermissionsOfUser(LoginUser loginUser);

    /**
     * 获取用户Token
     *
     * @param token accessToken
     * @return 用户Token
     */
    SecurityUserTokenEntity getByToken(String token);

    /**
     * 根据用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    SecurityUserEntity getUserById(Long userId);

    /**
     * 获取部门ID集合（这里指用户所有角色关联的所有部门ID）
     *
     * @param userId 用户ID
     * @return 用户所有角色关联的所有部门ID
     */
    Set<Long> getDeptIdSetOfUser(Long userId);

    /**
     * 获取本部门及本部门下子部门ID
     *
     * @param deptId 所属部门ID
     * @return 本部门及本部门下子部门ID
     */
    Set<Long> getDeptAndChildIdSet(Long deptId);

    //*******************验证码********************

    /**
     * 构建图形验证码并缓存
     *
     * @param response {@link HttpServletResponse}
     * @param uuid     uuid
     * @throws IOException I/O异常
     */
    void buildCaptchaAndCache(HttpServletResponse response, String uuid) throws IOException;

    /**
     * 校验验证码
     *
     * @param uuid    uuid
     * @param captcha 验证码
     * @return 校验结果 true：成功 false：失败
     */
    boolean validateCaptcha(String uuid, String captcha);

}
