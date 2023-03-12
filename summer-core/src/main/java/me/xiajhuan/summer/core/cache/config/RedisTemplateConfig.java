/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.core.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis模板配置
 *
 * @author xiajhuan
 * @date 2022/11/30
 */
@Configuration
@ConditionalOnProperty(prefix = "server.cache", name = "type", havingValue = "REDIS")
public class RedisTemplateConfig {

    /**
     * 主机（IP）
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * 端口
     */
    @Value("${spring.redis.port}")
    private Integer port;

    /**
     * 密码
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 数据库
     */
    @Value("${spring.redis.database}")
    private Integer database;

    /**
     * 超时时间（ms）
     */
    @Value("${spring.redis.timeout}")
    private Long timeout;

    /**
     * 最小空闲连接数
     */
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;

    /**
     * 最大空闲连接数
     */
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;

    /**
     * 最大可分配连接数
     */
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxActive;

    /**
     * 抛出异常之前连接分配应阻塞的最大时间（ms）
     */
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Long maxWait;

    /**
     * 注册 {@link RedisConnectionFactory}
     *
     * @return {@link RedisConnectionFactory}
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWait(Duration.ofMillis(maxWait));

        // Lettuce客户端配置
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout))
                .shutdownTimeout(Duration.ofMillis(timeout))
                .poolConfig(poolConfig).build();

        // Standalone模式的客户端
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(host, port);
        serverConfig.setDatabase(database);
        serverConfig.setPassword(password);

        // 创建Lettuce客户端连接工厂
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    /**
     * 注册 {@link RedisTemplate}
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
        // jackson的序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        // String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用 String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的field采用 String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value采用 jackson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value采用 jackson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

}
