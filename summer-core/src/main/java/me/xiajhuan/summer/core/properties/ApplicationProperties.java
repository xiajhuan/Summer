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
import me.xiajhuan.summer.core.constant.CacheConst;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 应用
 *
 * @author xiajhuan
 * @date 2022/11/30
 */
@Setter
@Getter
@Component
@ConfigurationProperties("application")
public class ApplicationProperties {

    /**
     * 缓存
     */
    @NestedConfigurationProperty
    private final Cache cache = new Cache();

    /**
     * 对象存储
     */
    @NestedConfigurationProperty
    private final Oss oss = new Oss();

    @Setter
    @Getter
    public static class Cache {

        /**
         * 类型，默认为：REDIS
         *
         * @see CacheConst.Type#REDIS
         * @see CacheConst.Type#HEAP
         */
        private String type = CacheConst.Type.REDIS;

    }

    @Setter
    @Getter
    public static class Oss {

        /**
         * 类型，默认为：LOCAL
         *
         * @see OssSupportEnum
         */
        private String type = OssSupportEnum.LOCAL.toString();

        /**
         * 是否严格匹配，默认为：false
         * <p>
         * 严格匹配时将检查类型对应的oss配置项是否完整，不完整直接报错！
         * 非严格匹配时如果检查到对应的oss配置项不完整则切换为“LOCAL”，
         * 如果“LOCAL”的oss配置项也不完整则报错！
         * </p>
         */
        private boolean strict = false;

    }

}
