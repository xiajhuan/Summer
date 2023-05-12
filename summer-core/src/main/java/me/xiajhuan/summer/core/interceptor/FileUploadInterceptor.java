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
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.enums.FileTypeEnum;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.exception.custom.SystemException;
import me.xiajhuan.summer.core.exception.custom.ValidationException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

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
 * @see FileTypeEnum
 */
public class FileUploadInterceptor implements HandlerInterceptor {

    /**
     * 文件上传限制，note：
     * <ul>
     *   <li>配置位置：classpath:upload-limit.json</li>
     *   <li>
     *     Key：支持的文件类型后缀（不包括”.“），参考{@link FileTypeEnum}<br>
     *     Value：大小限制（B、KB、MB、GB）
     *   </li>
     *   <li>Value不能超过spring.servlet.multipart.max-file-size配置的值</li>
     * </ul>
     */
    private final Map<String, String> uploadLimit;

    /**
     * 构造私有化
     *
     * @throws FileNotFoundException 如果文件没找到
     */
    @SuppressWarnings("unchecked")
    private FileUploadInterceptor() throws FileNotFoundException {
        FileReader fileReader = FileReader.create(ResourceUtils
                .getFile("classpath:upload-limit.json"));
        try {
            uploadLimit = JSONUtil.toBean(fileReader.readString(), Map.class);
        } catch (RuntimeException e) {
            throw SystemException.of(e, ErrorCode.JSON_PARSE_ERROR, e.getMessage());
        }
    }

    /**
     * 构建FileUploadInterceptor
     *
     * @return FileUploadInterceptor
     * @throws FileNotFoundException 如果文件没找到
     */
    public static FileUploadInterceptor of() throws FileNotFoundException {
        return new FileUploadInterceptor();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request instanceof MultipartHttpServletRequest && MapUtil.isNotEmpty(uploadLimit)) {
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
        // 文件类型是否支持，true：是 false：否
        boolean supported = false;

        // 文件后缀名（不包括“.”）
        String fileSuffix = FileNameUtil.getSuffix(file.getOriginalFilename());
        // 文件大小（B）
        long fileSize = file.getSize();
        for (Map.Entry<String, String> entry : uploadLimit.entrySet()) {
            if (entry.getKey().equals(fileSuffix)) {
                String sizeLimit = entry.getValue();
                if (fileSize > DataSizeUtil.parse(sizeLimit)) {
                    // 超过大小限制
                    throw ValidationException.of(ErrorCode.FILE_TYPE_EXCEED, fileSuffix, sizeLimit);
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
