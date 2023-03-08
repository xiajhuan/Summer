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
import me.xiajhuan.summer.common.log.dto.LogOperationDto;
import me.xiajhuan.summer.common.log.entity.LogOperationEntity;
import me.xiajhuan.summer.common.log.mapper.LogOperationMapper;
import me.xiajhuan.summer.common.log.service.LogOperationService;
import me.xiajhuan.summer.common.mp.standard.MpCommonOperation;
import me.xiajhuan.summer.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志 ServiceImpl
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Service
@DS(DataSourceConst.COMMON)
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperationEntity> implements LogOperationService, MpCommonOperation<LogOperationDto, LogOperationEntity> {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    //*******************MpCommonOperation覆写开始********************

    @Override
    public LambdaQueryWrapper<LogOperationEntity> getSelectWrapper(Class<LogOperationEntity> entityClass) {
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = Wrappers.lambdaQuery();
        // 查询字段
        queryWrapper.select(LogOperationEntity::getId, LogOperationEntity::getOperation, LogOperationEntity::getOperationGroup,
                LogOperationEntity::getRequestUri, LogOperationEntity::getRequestMethod, LogOperationEntity::getRequestParams,
                LogOperationEntity::getRequestTime, LogOperationEntity::getUserAgent, LogOperationEntity::getIp,
                LogOperationEntity::getStatus, LogOperationEntity::getOperateBy, LogOperationEntity::getCreateTime);

        return queryWrapper;
    }

    @Override
    public LambdaQueryWrapper<LogOperationEntity> getQueryWrapper(LogOperationDto dto, boolean isCount) {
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = getQueryWrapperUnconditional(dto, isCount);
        // 动态Sql查询条件
        // 操作分组
        Integer operationGroup = dto.getOperationGroup();
        queryWrapper.eq(operationGroup != null, LogOperationEntity::getOperationGroup, operationGroup);
        // 状态
        Integer status = dto.getStatus();
        queryWrapper.eq(status != null, LogOperationEntity::getStatus, status);
        // 创建时间（闭区间）
        Date createTimeStart = dto.getParams().getDate("createTimeStart");
        if (createTimeStart != null) {
            queryWrapper.ge(LogOperationEntity::getCreateTime, createTimeStart);
        }
        Date createTimeEnd = dto.getParams().getDate("createTimeEnd");
        if (createTimeEnd != null) {
            queryWrapper.le(LogOperationEntity::getCreateTime, createTimeEnd);
        }

        return queryWrapper;
    }

    @Override
    public IPage<LogOperationEntity> customPage(Page<LogOperationEntity> page, LogOperationDto dto) {
        // 关闭MP分页时内置的count查询
        page.setSearchCount(false);

        IPage<LogOperationEntity> pageResult = page(page, getQueryWrapper(dto, false));

        pageResult.setTotal(count(getQueryWrapper(dto, true)));

        return pageResult;
    }

    //*******************MpCommonOperation覆写结束********************

    @Override
    public IPage<LogOperationDto> page(PageAndSort pageAndSort, LogOperationDto dto) {
        return ConvertUtil.convert(customPage(handlePageAndSort(pageAndSort), dto), LogOperationDto.class);
    }

    @Override
    public List<LogOperationDto> list(LogOperationDto dto) {
        return ConvertUtil.convert(list(getQueryWrapper(dto, false)), LogOperationDto.class);
    }

    @Override
    public void saveLogAsync(LogOperationEntity log) {
        save(log);
    }

    @Override
    public void clearLog() {
        // 删除操作日志
        LambdaQueryWrapper<LogOperationEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(LogOperationEntity::getId);
        queryWrapper.lt(LogOperationEntity::getCreateTime, DateUtil.offsetDay(DateUtil.date(), setting.getInt("operation.clear.day-limit", "Log")));

        List<LogOperationEntity> entityList = list(queryWrapper);

        if (CollUtil.isNotEmpty(entityList)) {
            removeByIds(entityList.stream().map(LogOperationEntity::getId)
                    .collect(Collectors.toSet()));
        }
    }

}
