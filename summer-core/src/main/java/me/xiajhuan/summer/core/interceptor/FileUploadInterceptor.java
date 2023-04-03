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

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Map;

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
     * 文件上传限制，note：
     * <pre>
     *   1.配置位置：/custom/upload-limit.json
     *   2.Key：支持的文件类型后缀名 Value：大小限制（MB）
     *   3.Value不能超过 spring.servlet.multipart.max-file-size 中的配置
     * </pre>
     */
    private Map<String, Integer> uploadLimit = MapUtil.newHashMap(true);

    /**
     * 初始化 {@link uploadLimit}
     *
     * @throws FileNotFoundException 文件没找到异常
     */
    @PostConstruct
    private void init() throws FileNotFoundException {
        FileReader fileReader = FileReader.create(ResourceUtils
                .getFile("classpath:custom/upload-limit.json"));
        uploadLimit = JSONUtil.toBean(fileReader.readString(), Map.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request instanceof MultipartHttpServletRequest && uploadLimit.size() > 0) {
            Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getFileMap();
            if (MapUtil.isNotEmpty(fileMap)) {
                fileMap.values().forEach(this::validateFile);
            }
        }
        return true;
    }

    /**
     * 校验文件
     *
     * @param file {@link MultipartFile}
     */
    private void validateFile(MultipartFile file) {
        // 文件类型是否支持，true：支持 false：不支持
        boolean supported = false;

        // 文件后缀名（扩展名不带“.”）
        String fileSuffix = FileNameUtil.getSuffix(file.getOriginalFilename());
        // 文件大小（B）
        long fileSize = file.getSize();
        for (Map.Entry<String, Integer> entry : uploadLimit.entrySet()) {
            if (fileSuffix.equals(entry.getKey())) {
                int sizeLimit = entry.getValue();
                if (fileSize > DataSize.ofMegabytes(sizeLimit).toBytes()) {
                    // 超过大小限制
                    throw ValidationException.of(ErrorCode.FILE_TYPE_EXCEED, fileSuffix, sizeLimit + "MB");
                } else {
                    supported = true;
                    break;
                }
            }
        }

        if (!supported) {
            throw ValidationException.of(ErrorCode.UNSUPPORTED_FILE_TYPE, fileSuffix);
        }
    }

}
