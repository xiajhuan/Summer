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

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.xiajhuan.summer.admin.common.base.constant.CacheKeyConst;
import me.xiajhuan.summer.core.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.core.cache.server.CacheServer;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.constant.TimeUnitConst;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.admin.common.security.entity.SecurityDeptEntity;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.admin.common.security.entity.SecurityUserTokenEntity;
import me.xiajhuan.summer.admin.common.security.mapper.*;
import me.xiajhuan.summer.admin.common.security.service.SecurityService;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.enums.CaptchaTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限相关 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityServiceImpl implements SecurityService {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    @Resource
    private SecurityMenuMapper securityMenuMapper;

    @Resource
    private SecurityUserTokenMapper securityUserTokenMapper;

    @Resource
    private SecurityUserMapper securityUserMapper;

    @Resource
    private SecurityRoleDeptMapper securityRoleDeptMapper;

    @Resource
    private SecurityDeptMapper securityDeptMapper;

    //*******************认证授权********************

    @Override
    public Set<String> getPermissionsOfUser(LoginUser loginUser) {
        // 用户拥有的所有权限
        final Set<String> permissionsOfUser;
        if (loginUser.getUserType() == UserTypeEnum.SUPER_ADMIN.getValue()) {
            // 超级管理员
            permissionsOfUser = securityMenuMapper.getPermissionsAll();
        } else {
            // 普通用户
            permissionsOfUser = securityMenuMapper.getPermissionsOfUser(loginUser.getId());
        }

        if (CollUtil.isNotEmpty(permissionsOfUser)) {
            return permissionsOfUser.stream().filter(p -> !StrUtil.isBlankOrUndefined(p))
                    .collect(Collectors.toSet());
        } else {
            return CollUtil.newHashSet();
        }
    }

    @Override
    public SecurityUserTokenEntity getByToken(String token) {
        LambdaQueryWrapper<SecurityUserTokenEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SecurityUserTokenEntity::getUserId, SecurityUserTokenEntity::getExpireTime);
        queryWrapper.eq(SecurityUserTokenEntity::getToken, token);

        return securityUserTokenMapper.selectOne(queryWrapper);
    }

    @Override
    public SecurityUserEntity getUserById(Long userId) {
        return securityUserMapper.selectById(userId);
    }

    @Override
    public Set<Long> getDeptIdSetOfUser(Long userId) {
        return securityRoleDeptMapper.getDeptSetByUserId(userId);
    }

    @Override
    public Set<Long> getDeptAndChildIdSet(Long deptId) {
        Set<Long> deptIdSet = CollUtil.newHashSet(deptId);

        // 获取所有子部门ID
        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SecurityDeptEntity::getId);
        queryWrapper.like(SecurityDeptEntity::getParentIdAll, deptId);
        List<SecurityDeptEntity> entityList = securityDeptMapper.selectList(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            entityList.forEach(d -> deptIdSet.add(d.getId()));
        }
        return deptIdSet;
    }

    //*******************验证码********************

    @Override
    public void buildCaptchaAndCache(HttpServletResponse response, String uuid) throws IOException {
        // 构建验证码
        AbstractCaptcha captcha = buildGraphicCaptcha();

        // 响应验证码
        response.setContentType("image/png");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        captcha.write(response.getOutputStream());

        // 缓存验证码
        CacheServerFactory.getInstance().getCacheServer()
                .setStringTtl(StrUtil.format(CacheKeyConst.CAPTCHA_CODE, uuid), captcha.getCode(),
                        setting.getInt("captcha.cache.ttl", "Security", 5) * TimeUnitConst.MINUTE);
    }

    @Override
    public boolean validateCaptcha(String uuid, String captcha) {
        CacheServer cacheServer = CacheServerFactory.getInstance().getCacheServer();

        // 获取缓存的验证码
        String cacheKey = StrUtil.format(CacheKeyConst.CAPTCHA_CODE, uuid);
        String cacheCode = cacheServer.getString(cacheKey);
        if (cacheCode != null) {
            cacheServer.delete(cacheKey);
        }
        return captcha.equalsIgnoreCase(cacheCode);
    }

    /**
     * 构建图形验证码
     *
     * @return {@link AbstractCaptcha}
     * @see CaptchaTypeEnum
     */
    private AbstractCaptcha buildGraphicCaptcha() {
        // 验证码类型
        String type = setting.getByGroupWithLog("captcha.type", "Security");
        if (StrUtil.isBlank(type)) {
            // 没有配置则默认为：Line
            type = CaptchaTypeEnum.Line.getValue();
        }
        // 验证码长宽
        int width = setting.getInt("captcha.width", "Security", 150);
        int height = setting.getInt("captcha.height", "Security", 40);
        // 验证码字符个数
        int codeNum = setting.getInt("captcha.char-num", "Security", 4);
        switch (type) {
            case "Line":
                return CaptchaUtil.createLineCaptcha(width, height, codeNum,
                        setting.getInt("captcha.line-num", "Security", 10));
            case "Circle":
                return CaptchaUtil.createCircleCaptcha(width, height, codeNum,
                        setting.getInt("captcha.circle-num", "Security", 10));
            case "Shear":
                return CaptchaUtil.createShearCaptcha(width, height, codeNum,
                        setting.getInt("captcha.shear-width", "Security", 4));
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的验证码类型【{}】", type));
        }
    }

}
