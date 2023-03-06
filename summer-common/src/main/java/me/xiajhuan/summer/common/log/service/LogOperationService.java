package me.xiajhuan.summer.common.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.constant.ThreadPoolConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.log.dto.LogOperationDto;
import me.xiajhuan.summer.common.log.entity.LogOperationEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 操作日志 Service
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
public interface LogOperationService extends IService<LogOperationEntity> {

    IPage<LogOperationDto> page(PageAndSort pageAndSort, LogOperationDto dto);

    List<LogOperationDto> list(LogOperationDto dto);

    /**
     * 异步保存日志
     *
     * @param log 日志Entity
     */
    @Async(ThreadPoolConst.ASYNC_TASK_POOL)
    void saveLogAsync(LogOperationEntity log);

    /**
     * 清理日志
     */
    void clearLog();

}
