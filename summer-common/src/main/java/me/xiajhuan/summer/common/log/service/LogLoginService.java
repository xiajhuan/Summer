package me.xiajhuan.summer.common.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.constant.ThreadPoolConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.log.dto.LogLoginDto;
import me.xiajhuan.summer.common.log.entity.LogLoginEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 登录日志 Service
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
public interface LogLoginService extends IService<LogLoginEntity> {

    IPage<LogLoginDto> page(PageAndSort pageAndSort, LogLoginDto dto);

    List<LogLoginDto> list(LogLoginDto dto);

    /**
     * 异步保存日志
     *
     * @param log 日志Entity
     */
    @Async(ThreadPoolConst.ASYNC_TASK_POOL)
    void saveLogAsync(LogLoginEntity log);

    /**
     * 清理日志
     */
    void clearLog();

}
