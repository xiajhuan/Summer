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

package me.xiajhuan.summer.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型枚举
 *
 * @author xiajhuan
 * @date 2023/5/12
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    /**
     * 文档
     */
    DOCS("docs", "文档", new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "htm", "html", "txt", "pdf"}),

    /**
     * 图像
     */
    IMAGE("image", "图像", new String[]{"bmp", "gif", "jpg", "jpeg", "png"}),

    /**
     * 压缩文件
     */
    COMPRESSED_FILE("compressedFile", "压缩文件", new String[]{"zip", "rar", "7z", "gz"}),

    /**
     * 视频
     */
    VIDEO("video", "视频", new String[]{"mp4", "avi", "rmvb"});

    private final String value;

    private final String name;

    private final String[] suffixArray;

}