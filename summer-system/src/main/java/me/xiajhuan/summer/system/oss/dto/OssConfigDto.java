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

package me.xiajhuan.summer.system.oss.dto;

import lombok.Data;
import me.xiajhuan.summer.core.enums.OssSupportEnum;
import me.xiajhuan.summer.core.validation.group.LocalGroup;
import me.xiajhuan.summer.core.validation.group.MinIoGroup;
import me.xiajhuan.summer.core.validation.group.QiniuGroup;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 对象存储配置 Dto
 *
 * @author xiajhuan
 * @date 2023/4/28
 */
@Data
public class OssConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     *
     * @see OssSupportEnum
     */
    @NotNull(message = "{oss.config.type.require}", groups = {LocalGroup.class, MinIoGroup.class, QiniuGroup.class})
    @Range(min = 0, max = 2, message = "{oss.config.type.range}", groups = {LocalGroup.class, MinIoGroup.class, QiniuGroup.class})
    private Integer type;

    //*******************本地********************

    /**
     * 本地存储绑定域名
     */
    @NotBlank(message = "{oss.config.localDomain.require}", groups = LocalGroup.class)
    @URL(message = "{oss.config.localDomain.url}", groups = LocalGroup.class)
    private String localDomain;

    /**
     * 本地存储路径前缀
     */
    private String localPrefix;

    /**
     * 本地存储目录
     */
    @NotBlank(message = "{oss.config.localPath.require}", groups = LocalGroup.class)
    private String localPath;

    //*******************MinIo********************

    /**
     * minIoEndPoint
     */
    @NotBlank(message = "{oss.config.minIoEndPoint.require}", groups = MinIoGroup.class)
    private String minIoEndPoint;

    /**
     * minIoAccessKey
     */
    @NotBlank(message = "{oss.config.minIoAccessKey.require}", groups = MinIoGroup.class)
    private String minIoAccessKey;

    /**
     * minIoSecretKey
     */
    @NotBlank(message = "{oss.config.minIoSecretKey.require}", groups = MinIoGroup.class)
    private String minIoSecretKey;

    /**
     * minIoBucketName
     */
    @NotBlank(message = "{oss.config.minIoBucketName.require}", groups = MinIoGroup.class)
    private String minIoBucketName;

    /**
     * MinIo路径前缀
     */
    private String minIoPrefix;

    //*******************七牛云********************

    /**
     * 七牛云accessKey
     */
    @NotBlank(message = "{oss.config.qiniuAccessKey.require}", groups = QiniuGroup.class)
    private String qiniuAccessKey;

    /**
     * 七牛云secretKey
     */
    @NotBlank(message = "{oss.config.qiniuSecretKey.require}", groups = QiniuGroup.class)
    private String qiniuSecretKey;

    /**
     * 七牛云bucketName
     */
    @NotBlank(message = "{oss.config.qiniuBucketName.require}", groups = QiniuGroup.class)
    private String qiniuBucketName;

    /**
     * 七牛云路径前缀
     */
    private String qiniuPrefix;

}
