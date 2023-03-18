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

package me.xiajhuan.summer.admin.open.api;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.data.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo OpenApi
 *
 * @author xiajhuan
 * @date 2022/12/4
 */
@RestController
@RequestMapping("open/api/demo")
public class DemoController {

    /**
     * hello
     *
     * @param json Json参数
     * @return 响应结果
     */
    @PostMapping("hello")
    public Result<String> hello(@RequestBody String json) {
        return printAndResponse(json);
    }

    /**
     * 打印到控制台并响应
     *
     * @param json Json参数
     * @return 响应结果
     */
    private Result<String> printAndResponse(String json) {
        if (JSONUtil.isTypeJSON(json)) {
            Console.log("接收到的数据为：{}", json);
            return Result.ofSuccess(json, "成功接收到数据");
        }
        return Result.ofFail("参数必须是Json格式");
    }

}
