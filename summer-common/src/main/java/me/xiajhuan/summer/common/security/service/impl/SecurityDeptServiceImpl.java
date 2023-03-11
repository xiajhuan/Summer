package me.xiajhuan.summer.common.security.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.common.constant.DataSourceConst;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;
import me.xiajhuan.summer.common.enums.UserTypeEnum;
import me.xiajhuan.summer.common.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.common.security.entity.SecurityDeptEntity;
import me.xiajhuan.summer.common.security.login.LoginUser;
import me.xiajhuan.summer.common.security.mapper.SecurityDeptMapper;
import me.xiajhuan.summer.common.security.service.SecurityDeptService;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityDeptServiceImpl extends ServiceImpl<SecurityDeptMapper, SecurityDeptEntity> implements SecurityDeptService, MpCommonOperation<SecurityDeptDto, SecurityDeptEntity> {

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityDeptEntity> getSelectWrapper(Class<SecurityDeptEntity> entityClass) {
        LambdaQueryWrapper<SecurityDeptEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(SecurityDeptEntity::getId, SecurityDeptEntity::getParentId, SecurityDeptEntity::getName,
                SecurityDeptEntity::getWeight, SecurityDeptEntity::getParentName, SimpleBaseEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public void fieldFillHook(SecurityDeptEntity entity) {

    }

    @Override
    public IPage<SecurityDeptEntity> customPage(Page<SecurityDeptEntity> page, SecurityDeptDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<SecurityDeptEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public List<SecurityDeptDto> tree() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // 这里不细分数据权限，非超级管理员，一律只能查询本部门及本部门下子部门的数据
        if (UserTypeEnum.GENERAL.getValue() == loginUser.getUserType()) {

        }
        return null;
    }

    @Override
    public void add(SecurityDeptDto dto) {

    }

    @Override
    public void update(SecurityDeptDto dto) {

    }

    @Override
    public void delete(String[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

}
