package me.xiajhuan.summer.common.boot.runner;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（服务启动完毕）
 *
 * @author xiajhuan
 * @date 2022/11/28
 * @see ApplicationRunner
 */
@Component
public class StartedUpRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private ConfigurableApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            LOGGER.info("  _   _   _   _   _   _   _   _");
            LOGGER.info(" / \\ / \\ / \\ / \\ / \\ / \\ / \\ / \\");
            LOGGER.info("( c | o | m | p | l | e | t | e )");
            LOGGER.info(" \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/");
            LOGGER.info("【{}】启动完毕，时间【{}】", applicationName, DateUtil.date());
        }
    }

}
