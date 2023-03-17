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

package me.xiajhuan.summer.core.cache.key;

import cn.hutool.core.util.StrUtil;

/**
 * summer-core模块缓存Key
 *
 * @author xiajhuan
 * @date 2023/3/17
 */
public class CoreCacheKey {

    /**
     * Excel数据 Key
     *
     * @param keyParam 填充参数
     * @return Excel数据 Key
     */
    public static String excelData(String keyParam) {
        return StrUtil.format("EXCEL_DATA_{}", keyParam);
    }

}
