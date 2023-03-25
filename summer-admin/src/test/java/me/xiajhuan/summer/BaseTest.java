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

package me.xiajhuan.summer;

import cn.hutool.core.util.StrUtil;

/**
 * 基本 Test
 *
 * @author xiajhuan
 * @date 2023/3/24
 */
public abstract class BaseTest {

    /**
     * 匹配失败消息
     */
    protected static final String MATCHES_FAIL_MSG = "{} matches {} failed!";

    /**
     * 开始消息
     *
     * @param methodSignature 方法签名
     * @return 开始消息
     */
    protected String startMsg(String methodSignature) {
        return StrUtil.format("{} start!\n", methodSignature);
    }

    /**
     * 成功消息
     *
     * @param methodSignature 方法签名
     * @return 成功消息
     */
    protected String successMsg(String methodSignature) {
        return StrUtil.format("\n{} success!", methodSignature);
    }

}
