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

package me.xiajhuan.summer.core.cache.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import me.xiajhuan.summer.core.utils.AssertUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import me.xiajhuan.summer.core.enums.RedisModeEnum;

import java.time.Duration;
import java.util.Set;

/**
 * Redis配置
 *
 * @author xiajhuan
 * @date 2023/6/5
 * @see RedisConnectionFactory
 * @see RedisTemplate
 * @see RedisStandaloneConfiguration
 * @see RedisClusterConfiguration
 */
@Configuration
@ConditionalOnProperty(prefix = "application.cache", name = "type", havingValue = "REDIS")
public class RedisConfig {

    @Value("${redis.mode}")
    private String mode;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout}")
    private long timeout;

    @Value("${redis.pool.min-idle}")
    private int minIdle;

    @Value("${redis.pool.max-idle}")
    private int maxIdle;

    @Value("${redis.pool.max-active}")
    private int maxActive;

    @Value("${redis.pool.max-wait}")
    private long maxWait;

    @Value("${redis.pool.time-between-eviction-runs}")
    private long timeBetweenEvictionRuns;

    @Value("${redis.standalone.host}")
    private String host;

    @Value("${redis.standalone.port}")
    private int port;

    @Value("${redis.standalone.database}")
    private int database;

    @Value("#{'${redis.cluster.node-set}'.split(',')}")
    private Set<String> nodeSet;

    /**
     * 注册{@link RedisConnectionFactory}
     *
     * @return {@link RedisConnectionFactory}
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 连接池配置
        @SuppressWarnings("rawtypes") GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWait(Duration.ofMillis(maxWait));
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(timeBetweenEvictionRuns));

        // Lettuce连接工厂
        return new LettuceConnectionFactory(getRedisConfiguration(),
                LettucePoolingClientConfiguration.builder()
                        .commandTimeout(Duration.ofMillis(timeout))
                        .poolConfig(poolConfig).build());
    }

    /**
     * 注册{@link RedisTemplate}
     *
     * @param factory {@link RedisConnectionFactory}
     * @return {@link RedisTemplate}
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 连接工厂
        template.setConnectionFactory(factory);

        // 序列化规则配置
        // Jackson的序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        // String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // Key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // Hash的Field采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // Value采用Jackson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash的Value采用Jackson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 获取Redis配置
     *
     * @return {@link RedisConfiguration}
     * @see RedisModeEnum
     */
    private RedisConfiguration getRedisConfiguration() {
        if (RedisModeEnum.STANDALONE.getValue().equals(mode)) {
            // 单机模式
            RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(host, port);
            standaloneConfig.setDatabase(database);
            standaloneConfig.setPassword(password);

            return standaloneConfig;
        } else if (RedisModeEnum.CLUSTER.getValue().equals(mode)) {
            // 集群模式
            AssertUtil.isNotEmpty("nodeSet", nodeSet);
            RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
            nodeSet.forEach(node -> clusterConfig.addClusterNode(RedisNode.fromString(node)));
            clusterConfig.setPassword(password);

            return clusterConfig;
        } else {
            throw new IllegalArgumentException(StrUtil.format("不支持的Redis模式【{}】", mode));
        }
    }

}
