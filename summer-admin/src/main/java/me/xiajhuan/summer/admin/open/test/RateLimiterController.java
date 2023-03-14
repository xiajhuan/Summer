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

package me.xiajhuan.summer.admin.open.test;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.ratelimiter.annotation.RateLimiter;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.BaseKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.ParamKeyStrategy;
import me.xiajhuan.summer.core.ratelimiter.strategy.impl.UsernameKeyStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流测试 Controller
 *
 * @author xiajhuan
 * @date 2022/3/8
 */
@RestController
@RequestMapping("test/rateLimiter")
public class RateLimiterController {

    /**
     * 基本限流策略
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("base")
    @RateLimiter(value = 0.2, keyStrategy = BaseKeyStrategy.class)
    public Result<String> base(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * IP限流策略
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("ip")
    @RateLimiter(0.2)
    public Result<String> ip(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * 参数限流策略
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("param")
    @RateLimiter(value = 0.2, keyStrategy = ParamKeyStrategy.class)
    public Result<String> param(@RequestBody String jsonParam) {
        return outputAndResponse(jsonParam);
    }

    /**
     * 用户名限流策略
     *
     * @param jsonParam Json格式参数
     * @return 响应结果
     */
    @PostMapping("username")
    @RateLimiter(value = 0.2, keyStrategy = UsernameKeyStrategy.class)
    public Result<String> username(@RequestBody String jsonParam) {
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
