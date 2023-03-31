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

package me.xiajhuan.summer.core.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import me.xiajhuan.summer.core.utils.ServletUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Sql注入 拦截器<br>
 * note：仅支持Query/FORM-DATA参数值的过滤
 *
 * @author xiajhuan
 * @date 2023/3/21
 * @see HandlerInterceptor
 * @see ServletUtil#getParamMap(ServletRequest)
 */
@Component
public class SqlInjectionInterceptor implements HandlerInterceptor {

    @Resource(name = SettingBeanConst.CORE)
    private Setting setting;

    /**
     * 是否开启Sql注入过滤
     */
    private boolean enable;

    /**
     * 非法关键字数组
     */
    private String[] illegalKeyWordArray;

    /**
     * 初始化 {@link enable} {@link illegalKeyWordArray}
     */
    @PostConstruct
    private void init() {
        enable = setting.getBool("enable-injection-filter", "Sql", true);
        if (enable) {
            String illegalKeyWord = setting.getByGroupWithLog("injection.illegal-key-word", "Sql");
            if (StrUtil.isBlank(illegalKeyWord)) {
                // 没有配置时的默认关键字
                illegalKeyWord = "SELECT,UPDATE,AND,OR,DELETE,INSERT,TRUNCATE,SUBSTR,DECLARE,MASTER,DROP,EXECUTE,UNION,',\",;,\\";
            }
            illegalKeyWordArray = illegalKeyWord.split(StrPool.COMMA);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (enable) {
            Map<String, String> paramMap = ServletUtil.getParamMap(request);
            if (MapUtil.isNotEmpty(paramMap)) {
                filter(paramMap);
            }
        }
        return true;
    }

    /**
     * 过滤
     *
     * @param paramMap 参数 Map
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
