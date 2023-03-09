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
import me.xiajhuan.summer.common.security.dto.SecurityUserDto;
import me.xiajhuan.summer.common.security.entity.SecurityUserEntity;
import me.xiajhuan.summer.common.security.mapper.SecurityUserMapper;
import me.xiajhuan.summer.common.security.service.SecurityUserService;
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
public class SecurityUserServiceImpl extends ServiceImpl<SecurityUserMapper, SecurityUserEntity> implements SecurityUserService, MpCommonOperation<SecurityUserDto, SecurityUserEntity> {

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getSelectWrapper(Class<SecurityUserEntity> entityClass) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(SecurityUserEntity::getId, SecurityUserEntity::getUsername, SecurityUserEntity::getRealName,
                SecurityUserEntity::getHeadUrl, SecurityUserEntity::getGender, SecurityUserEntity::getEmail,
                SecurityUserEntity::getMobile, SecurityUserEntity::getDeptId, SecurityUserEntity::getStatus,
                SecurityUserEntity::getUserType, SecurityUserEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityUserEntity> getQueryWrapper(SecurityUserDto dto, boolean isCount) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 用户名
        String username = dto.getUsername();
        queryWrapper.eq(StrUtil.isNotBlank(username), SecurityUserEntity::getUsername, username);
        // 性别
        Integer gender = dto.getGender();
        queryWrapper.eq(gender != null, SecurityUserEntity::getGender, gender);

        return queryWrapper;
    }

    @Override
    public IPage<SecurityUserEntity> customPage(Page<SecurityUserEntity> page, SecurityUserDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<SecurityUserEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }


    //*******************MpCommonOperation覆写结束********************

    @Override
    public SecurityUserDto getByUsername(String username) {
        LambdaQueryWrapper<SecurityUserEntity> queryWrapper = getSelectWrapper(SecurityUserEntity.class);
        queryWrapper.eq(SecurityUserEntity::getUsername, username);

        return ConvertUtil.convert(getOne(queryWrapper), SecurityUserDto.class);
    }

}
