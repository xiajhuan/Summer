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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.ContentTypeConst;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 请求体类型拦截器
 *
 * @author xiajhuan
 * @date 2023/3/21
 * @see HandlerInterceptor
 * @see ContentTypeConst
 */
public class ContentTypeInterceptor implements HandlerInterceptor {

    /**
     * 请求体类型集合
     */
    private final Set<String> contentTypeSet = CollUtil.newHashSet();

    /**
     * 构造私有化
     *
     * @param setting {@link Setting}
     */
    private ContentTypeInterceptor(Setting setting) {
        String contentType = setting.getByGroupWithLog("content-type-support", "Http");
        if (StrUtil.isBlank(contentType)) {
            // 没有配置则默认为：FORM-DATA,JSON
            contentType = "FORM-DATA,JSON";
        }
        initContentTypeSet(contentType);
    }

    /**
     * 构建ContentTypeInterceptor
     *
     * @param setting {@link Setting}
     * @return ContentTypeInterceptor
     */
    public static ContentTypeInterceptor of(Setting setting) {
        return new ContentTypeInterceptor(setting);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String contentType = request.getContentType();
        if (StrUtil.isNotBlank(contentType)) {
            if (StrUtil.startWithAnyIgnoreCase(contentType, ArrayUtil.toArray(contentTypeSet, String.class))) {
                return true;
            } else {
                throw ValidationException.of(ErrorCode.UNSUPPORTED_CONTENT_TYPE);
            }
        } else {
            // 没有请求体参数
            return true;
        }
    }

    /**
     * 初始化请求体类型集合
     *
     * @param contentType 配置的请求体类型
     */
    private void initContentTypeSet(String contentType) {
        if (StrUtil.containsIgnoreCase(contentType, "FORM-DATA")) {
            contentTypeSet.addAll(ListUtil.of(ContentTypeConst.FORM_DATA));
        }
        if (StrUtil.containsIgnoreCase(contentType, "JSON")) {
            contentTypeSet.add(ContentTypeConst.JSON);
        }
        if (StrUtil.containsIgnoreCase(contentType, "XML")) {
            contentTypeSet.add(ContentTypeConst.XML);
        }
        if (StrUtil.containsIgnoreCase(contentType, "JAVASCRIPT")) {
            contentTypeSet.add(ContentTypeConst.JAVASCRIPT);
        }
        if (StrUtil.containsIgnoreCase(contentType, "TEXT")) {
            contentTypeSet.add(ContentTypeConst.TEXT);
        }
        if (StrUtil.containsIgnoreCase(contentType, "HTML")) {
            contentTypeSet.add(ContentTypeConst.HTML);
        }
    }

}
