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

package me.xiajhuan.summer.admin.common.api;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.common.data.Result;
import me.xiajhuan.summer.common.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.IpKeyStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.ParamKeyStrategy;
import me.xiajhuan.summer.common.ratelimiter.strategy.impl.UsernameKeyStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestAPI Controller
 *
 * @author xiajhuan
 * @date 2022/3/8
 */
@RestController
@RequestMapping("api/test")
public class TestApiController {

    /**
     * 默认限流策略测试
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("rateLimiter/default")
    @RateLimiter(0.2)
    public Result<String> testDefault(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * IP限流策略测试
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("rateLimiter/ip")
    @RateLimiter(value = 0.2, keyStrategy = IpKeyStrategy.class)
    public Result<String> testIp(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * 参数限流策略测试
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("rateLimiter/param")
    @RateLimiter(value = 0.2, keyStrategy = ParamKeyStrategy.class)
    public Result<String> testParam(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * 用户名限流策略测试
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("rateLimiter/username")
    @RateLimiter(value = 0.2, keyStrategy = UsernameKeyStrategy.class)
    public Result<String> testUsername(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * 输出到控制台并响应
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    private Result<String> outputAndResponse(String jsonParam) {
        if (JSONUtil.isTypeJSON(jsonParam)) {
            Console.log("接收到的Json数据为：{}", jsonParam);

            return Result.ofSuccess(jsonParam, "成功接收到Json数据");
        }
        return Result.ofFail("参数必须是Json格式数据");
    }

}
