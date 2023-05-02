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

package me.xiajhuan.summer.core.ratelimiter.strategy.impl;

import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.core.ratelimiter.strategy.KeyStrategy;
import me.xiajhuan.summer.core.utils.ServletUtil;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 参数限流Key策略（Key为：接口签名#方法参数），例如：
 * /summer/security/user/page[GET]#pageNum=1&pageSize=10
 * </p>
 * note：入参数据量特别大时不建议使用
 *
 * @author xiajhuan
 * @date 2022/12/5
 */
public class ParamKeyStrategy implements KeyStrategy {

    //*******************单例处理开始********************

    private static volatile ParamKeyStrategy instance = null;

    public static ParamKeyStrategy getInstance() {
        if (instance == null) {
            synchronized (ParamKeyStrategy.class) {
                if (instance == null) {
                    instance = new ParamKeyStrategy();
                }
            }
        }
        return instance;
    }

    private ParamKeyStrategy() {
    }

    //*******************单例处理结束********************

    @Override
    public String getKey(JoinPoint point, HttpServletRequest request, String username) {
        return StrUtil.format(KEY_FORMAT, ServletUtil.getInterfaceSignature(request), ServletUtil.getParamPoint(point, request));
    }

    @Override
    public String extraMsgTemplate() {
        return "，Key-Param【{}】";
    }

}
