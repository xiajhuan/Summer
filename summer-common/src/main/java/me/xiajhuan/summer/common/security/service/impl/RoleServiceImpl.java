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
import me.xiajhuan.summer.common.security.dto.RoleDto;
import me.xiajhuan.summer.common.security.entity.RoleEntity;
import me.xiajhuan.summer.common.security.excel.RoleExcel;
import me.xiajhuan.summer.common.security.mapper.RoleMapper;
import me.xiajhuan.summer.common.security.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService, MpCommonOperation<RoleDto, RoleEntity> {

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<RoleEntity> getSelectWrapper(Class<RoleEntity> entityClass) {
        LambdaQueryWrapper<RoleEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(RoleEntity::getId, RoleEntity::getName, RoleEntity::getDesc,
                RoleEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<RoleEntity> getQueryWrapper(RoleDto dto, boolean isCount) {
        LambdaQueryWrapper<RoleEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 角色名称
        String name = dto.getName();
        queryWrapper.eq(StrUtil.isNotBlank(name), RoleEntity::getName, name);

        return queryWrapper;
    }

    @Override
    public IPage<RoleEntity> customPage(Page<RoleEntity> page, RoleDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<RoleEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public IPage<RoleDto> page(PageAndSort pageAndSort, RoleDto dto) {
        return ConvertUtil.convert(customPage(handlePageAndSort(pageAndSort), dto), RoleDto.class);
    }

    @Override
    public List<RoleDto> list(RoleDto dto) {
        return ConvertUtil.convert(list(getQueryWrapper(dto, false)), RoleDto.class);
    }

    @Override
    public void add(RoleDto dto) {

    }

    @Override
    public void update(RoleDto dto) {

    }

    @Override
    public void delete(String[] ids) {
        removeByIds(Arrays.stream(ids).collect(Collectors.toSet()));
    }

    @Override
    public List<RoleExcel> excelTemplate() {
        return null;
    }

}
