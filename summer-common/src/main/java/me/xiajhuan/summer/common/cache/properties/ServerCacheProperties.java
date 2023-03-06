package me.xiajhuan.summer.common.cache.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import me.xiajhuan.summer.common.constant.CacheConst;

/**
 * 服务缓存类型
 *
 * @author xiajhuan
 * @date 2022/11/30
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "server.cache")
public class ServerCacheProperties {

    /**
     * 缓存类型
     *
     * @see CacheConst#HEAP
     * @see CacheConst#REDIS
     */
    private String type;

}
