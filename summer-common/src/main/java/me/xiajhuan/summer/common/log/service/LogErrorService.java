package me.xiajhuan.summer.common.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.constant.ThreadPoolConst;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.log.dto.LogErrorDto;
import me.xiajhuan.summer.common.log.entity.LogErrorEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 错误日志 Service
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
public interface LogErrorService extends IService<LogErrorEntity> {

    IPage<LogErrorDto> page(PageAndSort pageAndSort, LogErrorDto dto);

    List<LogErrorDto> list(LogErrorDto dto);

    LogErrorDto getById(Long id);

    /**
     * 异步保存日志
     *
     * @param e       {@link Exception}
     * @param request {@link HttpServletRequest}
     */
    @Async(ThreadPoolConst.ASYNC_TASK_POOL)
    void saveLogAsync(Exception e, HttpServletRequest request);

    /**
     * 清理日志
     */
    void clearLog();

}
