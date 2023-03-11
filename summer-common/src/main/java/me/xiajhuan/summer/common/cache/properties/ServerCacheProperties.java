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
