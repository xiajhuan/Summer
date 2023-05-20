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

package me.xiajhuan.summer.system.security.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.core.cache.server.CacheServerFactory;
import me.xiajhuan.summer.core.constant.DataSourceConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import me.xiajhuan.summer.core.mp.helper.MpHelper;
import me.xiajhuan.summer.core.oss.server.OssServerFactory;
import me.xiajhuan.summer.core.utils.BeanUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.system.monitor.service.MonitorOnlineService;
import me.xiajhuan.summer.system.security.dto.PasswordDto;
import me.xiajhuan.summer.system.security.dto.SecurityUserDto;
import me.xiajhuan.summer.system.security.dto.UserInfoDto;
import me.xiajhuan.summer.system.security.entity.SecurityRoleUserEntity;
import me.xiajhuan.summer.system.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.system.security.entity.SecurityUserPostEntity;
import me.xiajhuan.summer.system.security.mapper.*;
import me.xiajhuan.summer.system.security.service.SecurityService;
import me.xiajhuan.summer.system.security.service.SecurityUserService;

import static me.xiajhuan.summer.system.security.cache.SecurityCacheKey.dept;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Service
@DS(DataSourceConst.SYSTEM)
public class SecurityUserServiceImpl extends ServiceImpl<SecurityUserMapper, SecurityUserEntity> implements SecurityUserService, MpHelper<SecurityUserDto, SecurityUserEntity> {

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    @Lazy
    @Resource
    private SecurityService securityService;

    @Resource
    private MonitorOnlineService monitorOnlineService;

    @Resource
    private SecurityRoleMapper securityRoleMapper;

    @Resource
    private SecurityRoleUserMapper securityRoleUserMapper;

    @Resource
    private SecurityUserPostMapper securityUserPostMapper;

    @Resource
    private SecurityPostMapper securityPostMapper;

    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        defaultPassword = setting.getByGroupWithLog("default-password", "Security");
        if (StrUtil.isBlank(defaultPassword)) {
            // 没有配置则默认为：123456
            defaultPassword = "123456";
        }
    }

    //*******************MpHelper覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getQueryWrapper(SecurityUserDto dto) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        // 查询条件
        // 排除超级管理员
        queryWrapper.eq(SecurityUserEntity::getUserType, 1);
        // 用户名
        String username = dto.getUsername();
        queryWrapper.eq(StrUtil.isNotBlank(username), SecurityUserEntity::getUsername, username);
        // 本部门ID
        Long deptId = dto.getDeptId();
        queryWrapper.eq(deptId != null, SecurityUserEntity::getDeptId, deptId);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, SecurityUserEntity::getStatus, status);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getSelectWrapper(SecurityUserDto dto) {
        // 查询字段
        return addSelectField(getQueryWrapper(dto));
    }

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> addSelectField(LambdaQueryWrapper<SecurityUserEntity> queryWrapper) {
        return queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getRealName,
                SecurityUserEntity::getGender, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getDataScope, SecurityUserEntity::getCreateTime);
    }

    @Override
    public void handleDtoBefore(SecurityUserDto dto) {
        String username = dto.getUsername();
        if (baseMapper.exist(username, dto.getId()) != null) {
            throw ValidationException.of(ErrorCode.USER_EXISTS, username);
        }
    }

    @Override
    public void handleEntityAfter(SecurityUserEntity entity) {
        // 本部门名称
        Long deptId = entity.getDeptId();
        if (deptId != null) {
            entity.setDeptName(String.valueOf(CacheServerFactory.getCacheServer()
                    .getHash(dept(deptId), "name")));
        }
    }

    //*******************MpHelper覆写结束********************

    @Override
    public Page<SecurityUserDto> page(SecurityUserDto dto) {
        Page<SecurityUserEntity> page = page(handlePageSort(dto), getSelectWrapper(dto));
        List<SecurityUserEntity> entityList = page.getRecords();
        if (entityList.size() > 0) {
            entityList.forEach(this::handleEntityAfter);
        }

        return BeanUtil.convert(page, SecurityUserDto.class);
    }

    @Override
    public List<SecurityUserDto> list(SecurityUserDto dto) {
        List<SecurityUserEntity> entityList = list(getSortWrapper(dto));
        if (entityList.size() > 0) {
            entityList.forEach(this::handleEntityAfter);
        }

        return BeanUtil.convert(entityList, SecurityUserDto.class);
    }

    @Override
    public SecurityUserDto getById(Long id) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getId, id);
        SecurityUserEntity entity = getOne(addSelectField(queryWrapper));
        if (entity != null) {
            handleEntityAfter(entity);

            SecurityUserDto dto = BeanUtil.convert(entity, SecurityUserDto.class);
            // 角色ID集合
            dto.setRoleIdSet(securityRoleUserMapper.getRoleIdSet(id));

            // 岗位ID集合
            dto.setPostIdSet(securityUserPostMapper.getPostIdSet(id));

            return dto;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SecurityUserDto dto) {
        handleDtoBefore(dto);

        SecurityUserEntity entity = BeanUtil.convert(dto, SecurityUserEntity.class);
        // 加密密码
        entity.setPassword(SecurityUtil.encode(defaultPassword));

        // 保存用户
        String currentUsername = SecurityUtil.getCurrentUsername();
        Date now = DateUtil.date();
        // note：这里不能使用字段自动填充，否则deptId会被覆盖！
        entity.setCreateBy(currentUsername);
        entity.setCreateTime(now);
        entity.setUpdateBy(currentUsername);
        entity.setUpdateTime(now);
        if (save(entity)) {
            Long id = entity.getId();

            // 保存角色用户关联
            saveOrUpdateRoleUser(id, dto.getRoleIdSet(), false);

            // 保存用户岗位关联
            saveOrUpdateUserPost(id, dto.getPostIdSet(), false);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SecurityUserDto dto) {
        handleDtoBefore(dto);

        Long id = dto.getId();
        // 更新前的用户名
        String oldUsername = baseMapper.getUsername(id);

        SecurityUserEntity entity = BeanUtil.convert(dto, SecurityUserEntity.class);

        // 更新用户，note：这里不能使用字段自动填充，否则deptId会被覆盖！
        entity.setUpdateBy(SecurityUtil.getCurrentUsername());
        entity.setUpdateTime(DateUtil.date());
        if (updateById(entity)) {
            // 更新角色用户关联
            saveOrUpdateRoleUser(id, dto.getRoleIdSet(), true);

            // 更新用户岗位关联
            saveOrUpdateUserPost(id, dto.getPostIdSet(), true);

            if (!oldUsername.equals(entity.getUsername())) {
                // 如果修改了用户名，让还在线的该用户自动退出
                securityService.logout(id, true);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        if (removeByIds(idSet)) {
            // 删除角色用户关联
            LambdaQueryWrapper<SecurityRoleUserEntity> roleUserQueryWrapper = Wrappers.lambdaQuery();
            roleUserQueryWrapper.in(SecurityRoleUserEntity::getUserId, idSet);
            securityRoleUserMapper.delete(roleUserQueryWrapper);

            // 删除用户岗位关联
            LambdaQueryWrapper<SecurityUserPostEntity> userPostQueryWrapper = Wrappers.lambdaQuery();
            userPostQueryWrapper.in(SecurityUserPostEntity::getUserId, idSet);
            securityUserPostMapper.delete(userPostQueryWrapper);

            // 被删除如果还在线的用户自动退出
            monitorOnlineService.deleteBatch(idSet);

            idSet.forEach(id -> securityService.logout(id, false));
        }
    }

    @Override
    public long count(SecurityUserDto dto) {
        return count(getQueryWrapper(dto));
    }

    @Override
    public SecurityUserEntity getByUsername(String username) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getUsername, username);
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getPassword,
                SecurityUserEntity::getRealName, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getDataScope);

        return getOne(queryWrapper);
    }

    @Override
    public UserInfoDto info() {
        Long id = SecurityUtil.getCurrentUserId();

        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getEmptyWrapper();
        queryWrapper.eq(SecurityUserEntity::getId, id);
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getRealName, SecurityUserEntity::getDeptId,
                SecurityUserEntity::getGender, SecurityUserEntity::getEmail, SecurityUserEntity::getMobile,
                SecurityUserEntity::getHeadUrl, SecurityUserEntity::getHeadPath, SecurityUserEntity::getDescription);

        SecurityUserEntity entity = getOne(queryWrapper);
        if (entity != null) {
            // 本部门名称
            handleEntityAfter(entity);

            UserInfoDto dto = BeanUtil.convert(entity, UserInfoDto.class);
            // 角色名称集合
            Set<Long> roleIdSet = securityRoleUserMapper.getRoleIdSet(id);
            if (roleIdSet.size() > 0) {
                dto.setRoleNameSet(securityRoleMapper.getNameSet(roleIdSet));
            }
            // 岗位名称集合
            Set<Long> postIdSet = securityUserPostMapper.getPostIdSet(id);
            if (postIdSet.size() > 0) {
                dto.setPostNameSet(securityPostMapper.getNameSet(postIdSet));
            }

            return dto;
        }
        return null;
    }

    @Override
    public void updateInfo(UserInfoDto dto, Dict dict) {
        if (dict != null) {
            // 先删除原来的头像
            OssServerFactory.getOssServer().delete(dto.getHeadPath(), false);
            // 新头像
            dto.setHeadUrl(dict.getStr("url"));
            dto.setHeadPath(dict.getStr("path"));
        }

        SecurityUserEntity entity = BeanUtil.convert(dto, SecurityUserEntity.class);

        // 更新用户，note：这里不能使用字段自动填充，否则deptId会被覆盖！
        entity.setUpdateBy(SecurityUtil.getCurrentUsername());
        entity.setUpdateTime(DateUtil.date());
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePasswordAndLogout(PasswordDto dto) {
        LoginUser loginUser = SecurityUtil.getLoginUser();

        if (updatePassword(dto, loginUser)) {
            // 用户退出
            securityService.logout(loginUser.getId(), true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String reset(Long[] ids) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        if (UserTypeEnum.SUPER_ADMIN.getValue() != loginUser.getUserType()) {
            // 只有超级管理员可以重置密码
            throw ValidationException.of(ErrorCode.PASSWORD_RESET_ERROR);
        }

        Set<Long> idSet = Arrays.stream(ids).collect(Collectors.toSet());

        LambdaUpdateWrapper<SecurityUserEntity> updateWrapper = addUserSetField(
                SecurityUtil.encode(defaultPassword), "superAdmin");
        updateWrapper.in(SecurityUserEntity::getId, idSet);
        if (update(updateWrapper)) {
            // 被重置密码如果还在线的用户自动退出
            monitorOnlineService.deleteBatch(idSet);

            idSet.forEach(id -> securityService.logout(id, false));

            return defaultPassword;
        }
        return null;
    }

    /**
     * 保存/修改角色用户关联
     *
     * @param id        ID
     * @param roleIdSet 角色ID集合
     * @param isUpdate  是否为更新，true：是 false：否
     */
    private void saveOrUpdateRoleUser(Long id, Set<Long> roleIdSet, boolean isUpdate) {
        if (isUpdate) {
            // 更新则先删除原来的角色用户关联
            LambdaQueryWrapper<SecurityRoleUserEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SecurityRoleUserEntity::getUserId, id);
            securityRoleUserMapper.delete(queryWrapper);
        }

        if (roleIdSet.size() > 0) {
            // 保存新角色用户关联，note：这里数据量不会很大，直接循环插入就好
            roleIdSet.forEach(roleId -> {
                SecurityRoleUserEntity entity = new SecurityRoleUserEntity();
                entity.setRoleId(roleId);
                entity.setUserId(id);
                securityRoleUserMapper.insert(entity);
            });
        }
    }

    /**
     * 保存/修改用户岗位关联
     *
     * @param id        ID
     * @param postIdSet 岗位ID集合
     * @param isUpdate  是否为更新，true：是 false：否
     */
    private void saveOrUpdateUserPost(Long id, Set<Long> postIdSet, boolean isUpdate) {
        if (isUpdate) {
            // 更新则先删除原来的用户岗位关联
            LambdaQueryWrapper<SecurityUserPostEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SecurityUserPostEntity::getUserId, id);
            securityUserPostMapper.delete(queryWrapper);
        }

        if (postIdSet.size() > 0) {
            // 保存新用户岗位关联，note：这里数据量不会很大，直接循环插入就好
            postIdSet.forEach(postId -> {
                SecurityUserPostEntity entity = new SecurityUserPostEntity();
                entity.setPostId(postId);
                entity.setUserId(id);
                securityUserPostMapper.insert(entity);
            });
        }
    }

    /**
     * 修改密码
     *
     * @param dto       密码Dto
     * @param loginUser 登录用户信息
     * @return 是否修改成功，true：是 false：否
     */
    private boolean updatePassword(PasswordDto dto, LoginUser loginUser) {
        if (!SecurityUtil.matches(dto.getOldPassword(), loginUser.getPassword())) {
            // 原密码不正确
            throw ValidationException.of(ErrorCode.OLD_PASSWORD_ERROR);
        }

        String newPassword = dto.getNewPassword();
        if (!newPassword.equals(dto.getConfirmPassword())) {
            // 密码和确认密码不一致
            throw ValidationException.of(ErrorCode.PASSWORD_CONFIRM_ERROR);
        }

        LambdaUpdateWrapper<SecurityUserEntity> updateWrapper = addUserSetField(
                SecurityUtil.encode(newPassword), loginUser.getUsername());
        updateWrapper.eq(SecurityUserEntity::getId, loginUser.getId());
        return update(updateWrapper);
    }

    /**
     * 获取{@link LambdaUpdateWrapper}
     *
     * @param newPassword 新密码（密文）
     * @param username    用户名
     * @return {@link LambdaUpdateWrapper}
     */
    private LambdaUpdateWrapper<SecurityUserEntity> addUserSetField(String newPassword, String username) {
        // note：通过 update(LambdaUpdateWrapper) 更新时字段自动填充不会生效
        LambdaUpdateWrapper<SecurityUserEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(SecurityUserEntity::getPassword, newPassword);
        updateWrapper.set(SecurityUserEntity::getUpdateBy, username);
        updateWrapper.set(SecurityUserEntity::getUpdateTime, DateUtil.date());

        return updateWrapper;
    }

}
