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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 服务缓存类型
 *
 * @author xiajhuan
 * @date 2022/11/30
 */
@Setter
@Getter
@Component
@ConfigurationProperties("server.cache")
public class ServerCacheProperties {

    /**
     * 缓存类型，默认为：REDIS
     *
     * @see CacheConst.Type#HEAP
     * @see CacheConst.Type#REDIS
     */
    private String type = CacheConst.Type.REDIS;

}