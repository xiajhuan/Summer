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

package me.xiajhuan.summer.admin.api.test;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.BaseKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.ParamKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.UsernameKeyStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流 OpenTest
 *
 * @author xiajhuan
 * @date 2022/3/8
 */
@RestController
@RequestMapping("api/test/rateLimiter")
public class RateLimiterController {

    /**
     * 基本限流策略
     *
     * @param json Json格式参数
     * @return 响应结果
     */
    @PostMapping("base")
    @RateLimiter(value = 0.2, msg = ErrorCode.SERVER_BUSY, keyStrategy = BaseKeyStrategy.class)
    public Result base(@RequestBody String json) {
        return printAndResponse(json);
    }

    /**
     * IP限流策略
     *
     * @param json Json格式参数
     * @return 响应结果
     */
    @PostMapping("ip")
    @RateLimiter(value = 0.2, msg = ErrorCode.SERVER_BUSY)
    public Result ip(@RequestBody String json) {
        return printAndResponse(json);
    }

    /**
     * 参数限流策略
     *
     * @param json Json格式参数
     * @return 响应结果
     */
    @PostMapping("param")
    @RateLimiter(value = 0.2, msg = ErrorCode.SERVER_BUSY, keyStrategy = ParamKeyStrategy.class)
    public Result param(@RequestBody String json) {
        return printAndResponse(json);
    }

    /**
     * 用户名限流策略
     *
     * @param json Json格式参数
     * @return 响应结果
     */
    @PostMapping("username")
    @RateLimiter(value = 0.2, msg = ErrorCode.SERVER_BUSY, keyStrategy = UsernameKeyStrategy.class)
    public Result username(@RequestBody String json) {
        return printAndResponse(json);
    }

    /**
     * 打印到控制台并响应
     *
     * @param json Json格式参数
     * @return 响应结果
     */
    private Result printAndResponse(String json) {
        if (JSONUtil.isTypeJSON(json)) {
            Console.log("接收到Json数据：{}", json);
            return Result.ofSuccess();
        }
        return Result.ofFail("参数必须是Json格式");
    }

}
