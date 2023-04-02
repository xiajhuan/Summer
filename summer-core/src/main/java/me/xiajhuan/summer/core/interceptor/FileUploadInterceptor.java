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

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.utils.ServletUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * 文件上传拦截器
 *
 * @author xiajhuan
 * @date 2023/4/2
 * @see HandlerInterceptor
 */
@Component
public class FileUploadInterceptor implements HandlerInterceptor {

    /**
     * 文件上传限制（upload-limit.json）<br>
     * Key：允许的类型 Value：大小限制
     */
    private Dict uploadLimit;

    /**
     * 初始化 {@link uploadLimit}
     *
     * @throws FileNotFoundException 文件没找到异常
     */
    @PostConstruct
    private void init() throws FileNotFoundException {
        FileReader fileReader = FileReader.create(ResourceUtils
                .getFile("classpath:custom/upload-limit.json"));
        uploadLimit = JSONUtil.toBean(fileReader.readString(), Dict.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (ServletUtil.isMultipart(request)) {
            // TODO
        }
        return true;
    }

}
