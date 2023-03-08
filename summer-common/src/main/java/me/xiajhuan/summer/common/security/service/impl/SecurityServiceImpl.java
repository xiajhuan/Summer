package me.xiajhuan.summer.common.security.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.xiajhuan.summer.common.cache.factory.CacheServerFactory;
import me.xiajhuan.summer.common.cache.server.CacheServer;
import me.xiajhuan.summer.common.constant.CacheConst;
import me.xiajhuan.summer.common.constant.DataSourceConst;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.constant.TimeUnitConst;
import me.xiajhuan.summer.common.enums.UserTypeEnum;
import me.xiajhuan.summer.common.security.entity.DeptEntity;
import me.xiajhuan.summer.common.security.entity.UserEntity;
import me.xiajhuan.summer.common.security.entity.UserTokenEntity;
import me.xiajhuan.summer.common.security.mapper.*;
import me.xiajhuan.summer.common.security.service.SecurityService;
import me.xiajhuan.summer.common.security.login.LoginUser;
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
    private MenuMapper menuMapper;

    @Resource
    private UserTokenMapper userTokenMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleDeptMapper roleDeptMapper;

    @Resource
    private DeptMapper deptMapper;

    //*******************认证授权********************

    @Override
    public Set<String> getPermissionsOfUser(LoginUser loginUser) {
        // 用户拥有的所有权限
        final List<String> permissionsOfUser;
        if (loginUser.getUserType() == UserTypeEnum.SUPER_ADMIN.getValue()) {
            // 超级管理员
            permissionsOfUser = menuMapper.getPermissionsAll();
        } else {
            // 普通用户
            permissionsOfUser = menuMapper.getPermissionsOfUser(loginUser.getId());
        }

        if (CollUtil.isNotEmpty(permissionsOfUser)) {
            return permissionsOfUser.stream().filter(p -> StrUtil.isNotBlank(p))
                    .collect(Collectors.toSet());
        } else {
            return CollUtil.newHashSet();
        }
    }

    @Override
    public UserTokenEntity getByToken(String token) {
        LambdaQueryWrapper<UserTokenEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(UserTokenEntity::getUserId, UserTokenEntity::getExpireTime);
        queryWrapper.eq(UserTokenEntity::getToken, token);

        return userTokenMapper.selectOne(queryWrapper);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<Long> getDeptIdListOfUser(Long userId) {
        return roleDeptMapper.getDeptListByUserId(userId);
    }

    @Override
    public List<Long> getDeptAndChildIdList(Long deptId) {
        List<Long> deptIdList = CollUtil.newArrayList(deptId);

        // 获取所有子部门ID
        LambdaQueryWrapper<DeptEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(DeptEntity::getId);
        queryWrapper.like(DeptEntity::getPidAll, deptId);
        List<DeptEntity> entityList = deptMapper.selectList(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            entityList.forEach(d -> deptIdList.add(d.getId()));
        }
        return deptIdList;
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
                .setStringTtl(StrUtil.format(CacheConst.CAPTCHA_CODE, uuid), captcha.getCode(),
                        setting.getInt("cache.ttl", "Captcha") * TimeUnitConst.MINUTE);
    }

    @Override
    public boolean validateCaptcha(String uuid, String captcha) {
        CacheServer cacheServer = CacheServerFactory.getInstance().getCacheServer();

        // 获取缓存的验证码
        String cacheKey = StrUtil.format(CacheConst.CAPTCHA_CODE, uuid);
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
     */
    private AbstractCaptcha buildGraphicCaptcha() {
        // 验证码类型
        String type = setting.getByGroup("graphic.type", "Captcha");
        // 验证码长宽
        int width = setting.getInt("graphic.width", "Captcha");
        int height = setting.getInt("graphic.height", "Captcha");
        // 验证码字符个数
        int codeNum = setting.getInt("graphic.char-num", "Captcha");
        switch (type) {
            case "Line":
                return CaptchaUtil.createLineCaptcha(width, height, codeNum,
                        setting.getInt("graphic.line-num", "Captcha"));
            case "Circle":
                return CaptchaUtil.createCircleCaptcha(width, height, codeNum,
                        setting.getInt("graphic.circle-num", "Captcha"));
            case "Shear":
                return CaptchaUtil.createShearCaptcha(width, height, codeNum,
                        setting.getInt("graphic.shear-width", "Captcha"));
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的验证码类型【{}】", type));
        }
    }

}
