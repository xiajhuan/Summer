package me.xiajhuan.summer.common.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.common.constant.DataSourceConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.common.security.dto.SecurityRoleDto;
import me.xiajhuan.summer.common.security.entity.SecurityRoleEntity;
import me.xiajhuan.summer.common.security.excel.SecurityRoleExcel;
import me.xiajhuan.summer.common.security.mapper.SecurityRoleMapper;
import me.xiajhuan.summer.common.security.service.SecurityRoleService;
import me.xiajhuan.summer.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
@Service
@DS(DataSourceConst.COMMON)
public class SecurityRoleServiceImpl extends ServiceImpl<SecurityRoleMapper, SecurityRoleEntity> implements SecurityRoleService, MpCommonOperation<SecurityRoleDto, SecurityRoleEntity> {

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> getSelectWrapper(Class<SecurityRoleEntity> entityClass) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(SecurityRoleEntity::getId, SecurityRoleEntity::getName, SecurityRoleEntity::getDesc,
                SecurityRoleEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<SecurityRoleEntity> getQueryWrapper(SecurityRoleDto dto, boolean isCount) {
        LambdaQueryWrapper<SecurityRoleEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 角色名称
        String name = dto.getName();
        queryWrapper.eq(StrUtil.isNotBlank(name), SecurityRoleEntity::getName, name);

        return queryWrapper;
    }

    @Override
    public IPage<SecurityRoleEntity> customPage(Page<SecurityRoleEntity> page, SecurityRoleDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<SecurityRoleEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public IPage<SecurityRoleDto> page(PageAndSort pageAndSort, SecurityRoleDto dto) {
        return ConvertUtil.convert(customPage(handlePageAndSort(pageAndSort), dto), SecurityRoleDto.class);
    }

    @Override
    public List<SecurityRoleDto> list(SecurityRoleDto dto) {
        return ConvertUtil.convert(list(getQueryWrapper(dto, false)), SecurityRoleDto.class);
    }

    @Override
    public void add(SecurityRoleDto dto) {

    }

    @Override
    public void update(SecurityRoleDto dto) {

    }

    @Override
    public void delete(String[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public List<SecurityRoleExcel> excelTemplate() {
        return null;
    }

}
