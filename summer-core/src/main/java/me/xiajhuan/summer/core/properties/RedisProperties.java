/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.properties;

import lombok.Getter;
import lombok.Setter;
import me.xiajhuan.summer.core.enums.RedisModeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Redis
 *
 * @author xiajhuan
 * @date 2023/6/5
 */
@Setter
@Getter
@Component
@ConfigurationProperties("redis")
public class RedisProperties {

    /**
     * 密码
     */
    private String password;

    /**
     * 模式，默认为STANDALONE
     *
     * @see RedisModeEnum#STANDALONE
     * @see RedisModeEnum#CLUSTER
     */
    private String mode = RedisModeEnum.STANDALONE.getValue();

    /**
     * 命令超时时间（ms）
     */
    private long timeout;

    /**
     * 连接池
     */
    @NestedConfigurationProperty
    private final Pool pool = new Pool();

    /**
     * 单机模式
     */
    @NestedConfigurationProperty
    private final Standalone standalone = new Standalone();

    /**
     * 集群模式
     */
    @NestedConfigurationProperty
    private final Cluster cluster = new Cluster();

    @Setter
    @Getter
    public static class Pool {

        /**
         * 最小空闲连接数
         */
        private int minIdle;

        /**
         * 最大空闲连接数，推荐：CPU核心数*2
         */
        private int maxIdle;

        /**
         * 最大可分配连接数，推荐：大于CPU核心数*2
         */
        private int maxActive;

        /**
         * 当连接池耗尽时，抛出异常之前应阻塞的最大时间（ms），值为-1则无限期阻塞
         */
        private long maxWait;

        /**
         * 空闲逐出器运行时间间隔（ms，空闲连接释放周期）
         */
        private long timeBetweenEvictionRuns;

    }

    @Setter
    @Getter
    public static class Standalone {

        /**
         * ip或域名
         */
        private String host;

        /**
         * 端口
         */
        private int port;

        /**
         * 数据库
         */
        private int database;

    }

    @Setter
    @Getter
    public static class Cluster {

        /**
         * 节点集合，格式：ip或域名:端口,ip或域名:端口
         */
        private Set<String> nodeSet;

    }

}
