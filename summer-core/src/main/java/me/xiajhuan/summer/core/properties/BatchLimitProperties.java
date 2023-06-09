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

package me.xiajhuan.summer.core.properties;

import lombok.Getter;
import lombok.Setter;
import me.xiajhuan.summer.core.mp.mapper.CustomBaseMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 批量操作限制
 *
 * @author xiajhuan
 * @date 2023/3/31
 */
@Setter
@Getter
@Component
@ConfigurationProperties("batch-limit")
public class BatchLimitProperties {

    /**
     * 每次批量插入数（JDBC批量提交）
     *
     * @see CustomBaseMapper#realSaveBatch(List)
     */
    private int realSaveNumEveryTime = 100;

    /**
     * Excel最大导出数
     */
    private long excelMaxExport = 40000L;

}
