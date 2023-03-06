package me.xiajhuan.summer.common.log.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.setting.Setting;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.common.constant.DataSourceConst;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.log.dto.LogLoginDto;
import me.xiajhuan.summer.common.log.entity.LogLoginEntity;
import me.xiajhuan.summer.common.log.mapper.LogLoginMapper;
import me.xiajhuan.summer.common.log.service.LogLoginService;
import me.xiajhuan.summer.common.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Service
@DS(DataSourceConst.COMMON)
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLoginEntity> implements LogLoginService, MpCommonOperation<LogLoginDto, LogLoginEntity> {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<LogLoginEntity> getSelectWrapper(Class<LogLoginEntity> entityClass) {
        LambdaQueryWrapper<LogLoginEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(LogLoginEntity::getId, LogLoginEntity::getLoginUser, LogLoginEntity::getOperation,
                LogLoginEntity::getStatus, LogLoginEntity::getUserAgent, LogLoginEntity::getIp,
                LogLoginEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogLoginEntity> getQueryWrapper(LogLoginDto dto, boolean isCount) {
        LambdaQueryWrapper<LogLoginEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 用户操作
        Integer operation = dto.getOperation();
        queryWrapper.eq(operation != null, LogLoginEntity::getOperation, operation);
        // 登录状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogLoginEntity::getStatus, status);
        // 创建时间（闭区间）
        Date createTimeStart = dto.getCreateTimeStart();
        if (createTimeStart != null) {
            queryWrapper.ge(LogLoginEntity::getCreateTime, createTimeStart);
        }
        Date createTimeEnd = dto.getCreateTimeEnd();
        if (createTimeEnd != null) {
            queryWrapper.le(LogLoginEntity::getCreateTime, createTimeEnd);
        }

        return queryWrapper;
    }

    @Override
    public IPage<LogLoginEntity> customPage(Page<LogLoginEntity> page, LogLoginDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<LogLoginEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public IPage<LogLoginDto> page(PageAndSort pageAndSort, LogLoginDto dto) {
        return ConvertUtil.convert(customPage(handlePageAndSort(pageAndSort), dto), LogLoginDto.class);
    }

    @Override
    public List<LogLoginDto> list(LogLoginDto dto) {
        return ConvertUtil.convert(list(getQueryWrapper(dto, false)), LogLoginDto.class);
    }

    @Override
    public void saveLogAsync(LogLoginEntity log) {
        save(log);
    }

    @Override
    public void clearLog() {
        // 删除登录日志
        LambdaQueryWrapper<LogLoginEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(LogLoginEntity::getId);
        queryWrapper.lt(LogLoginEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), setting.getInt("login.clear.day-limit", "Log")));

        List<LogLoginEntity> entityList = list(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            removeByIds(entityList.stream().map(LogLoginEntity::getId)
                    .collect(Collectors.toSet()));
        }
    }

}
