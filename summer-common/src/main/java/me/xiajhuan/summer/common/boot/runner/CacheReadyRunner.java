package me.xiajhuan.summer.common.boot.runner;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import me.xiajhuan.summer.common.cache.properties.ServerCacheProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ApplicationRunner（缓存设施装载完毕）
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Component
public class CacheReadyRunner implements ApplicationRunner {

    private static final Log LOGGER = LogFactory.get();

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private ServerCacheProperties serverCacheProperties;

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("【{}】缓存设施装载完毕，缓存类型【{}】", applicationName, serverCacheProperties.getType());
    }

}
