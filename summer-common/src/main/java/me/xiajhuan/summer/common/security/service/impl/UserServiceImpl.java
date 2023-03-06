package me.xiajhuan.summer.common.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.common.constant.DataSourceConst;
import me.xiajhuan.summer.common.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.common.security.dto.UserDto;
import me.xiajhuan.summer.common.security.entity.UserEntity;
import me.xiajhuan.summer.common.security.mapper.UserMapper;
import me.xiajhuan.summer.common.security.service.UserService;
import me.xiajhuan.summer.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

/**
 * 用户 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Service
@DS(DataSourceConst.COMMON)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService, MpCommonOperation<UserDto, UserEntity> {

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<UserEntity> getSelectWrapper(Class<UserEntity> entityClass) {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(UserEntity::getId, UserEntity::getUsername, UserEntity::getRealName,
                UserEntity::getHeadUrl, UserEntity::getGender, UserEntity::getEmail,
                UserEntity::getMobile, UserEntity::getDeptId, UserEntity::getStatus,
                UserEntity::getUserType, UserEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<UserEntity> getQueryWrapper(UserDto dto, boolean isCount) {
        LambdaQueryWrapper<UserEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 用户名
        String username = dto.getUsername();
        queryWrapper.eq(StrUtil.isNotBlank(username), UserEntity::getUsername, username);
        // 性别
        Integer gender = dto.getGender();
        queryWrapper.eq(gender != null, UserEntity::getGender, gender);

        return queryWrapper;
    }

    @Override
    public IPage<UserEntity> customPage(Page<UserEntity> page, UserDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<UserEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }


    //*******************MpCommonOperation覆写结束********************

    @Override
    public UserDto getByUsername(String username) {
        LambdaQueryWrapper<UserEntity> queryWrapper = getSelectWrapper(UserEntity.class);
        queryWrapper.eq(UserEntity::getUsername, username);

        return ConvertUtil.convert(getOne(queryWrapper), UserDto.class);
    }

}
