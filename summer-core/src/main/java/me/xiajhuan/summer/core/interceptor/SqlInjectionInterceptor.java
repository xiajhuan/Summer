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

package me.xiajhuan.summer.core.interceptor;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import org.springframework.web.servlet.HandlerInterceptor;
import me.xiajhuan.summer.core.utils.ServletUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Sql注入拦截器<br>
 * note：仅支持Query/FORM-DATA参数值的过滤
 *
 * @author xiajhuan
 * @date 2023/3/21
 * @see HandlerInterceptor
 * @see ServletUtil#getParamMap(ServletRequest)
 */
public class SqlInjectionInterceptor implements HandlerInterceptor {

    /**
     * 非法关键字数组
     */
    private final String[] illegalKeyWordArray;

    /**
     * 构造私有化
     *
     * @param setting {@link Setting}
     */
    private SqlInjectionInterceptor(Setting setting) {
        String illegalKeyWord = setting.getByGroupWithLog("injection.illegal-key-word", "Sql");
        if (StrUtil.isBlank(illegalKeyWord)) {
            // 没有配置时的默认关键字
            illegalKeyWord = "SELECT,UPDATE,AND,OR,DELETE,INSERT,TRUNCATE,SUBSTR,DECLARE,MASTER,DROP,EXECUTE,UNION,',\",;,\\";
        }
        illegalKeyWordArray = illegalKeyWord.split(StrPool.COMMA);
    }

    /**
     * 构建SqlInjectionInterceptor
     *
     * @param setting {@link Setting}
     * @return SqlInjectionInterceptor
     */
    public static SqlInjectionInterceptor of(Setting setting) {
        return new SqlInjectionInterceptor(setting);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        if (paramMap.size() > 0) {
            filter(paramMap);
        }
        return true;
    }

    /**
     * 过滤
     *
     * @param paramMap 参数Map
     */
    private void filter(Map<String, String> paramMap) {
        paramMap.values().forEach(value -> {
            if (StrUtil.containsAnyIgnoreCase(value, illegalKeyWordArray)) {
                // 参数值包含非法字符
                throw ValidationException.of(ErrorCode.INVALID_SYMBOL);
            }
        });
    }

}
