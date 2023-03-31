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

package me.xiajhuan.summer.admin.common.base.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import me.xiajhuan.summer.core.mp.mapper.MyBaseMapper;

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
@ConfigurationProperties(prefix = "limit.batch")
public class LimitBatchProperties {

    /**
     * 每次批量插入数（JDBC批量提交）
     *
     * @see MyBaseMapper#realSaveBatch(List)
     */
    private int realSaveNumEveryTime = 100;

    /**
     * Excel最大导出数
     */
    private long excelMaxExport = 40000L;

}
