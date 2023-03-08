package me.xiajhuan.summer.common.schedule.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.xiajhuan.summer.common.log.service.LogErrorService;
import me.xiajhuan.summer.common.log.service.LogLoginService;
import me.xiajhuan.summer.common.log.service.LogOperationService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 通用定时任务
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
@Setter
@Accessors(chain = true)
public class CommonTaskJob {

    private static final Log LOGGER = LogFactory.get();

    private LogOperationService logOperationService;

    private LogErrorService logErrorService;

    private LogLoginService logLoginService;

    /**
     * 清理操作日志和错误日志<br>
     * note：按标准时间计，每天1：00执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearOperationAndErrorLog() {
        TimeInterval timer = DateUtil.timer();
        LOGGER.info("【CommonTaskJob】【clearOperationAndErrorLog】Job开始执行：{}", DateUtil.date());

        logOperationService.clearLog();
        logErrorService.clearLog();
        logLoginService.clearLog();

        LOGGER.info("【CommonTaskJob】【clearOperationAndErrorLog】Job执行结束：{}，耗时【{}】ms", DateUtil.date(), timer.interval());
    }

}
