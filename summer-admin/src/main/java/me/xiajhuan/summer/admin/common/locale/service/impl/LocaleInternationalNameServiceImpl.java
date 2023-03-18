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

package me.xiajhuan.summer.admin.common.locale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.xiajhuan.summer.admin.common.locale.dto.LocaleInternationalNameDto;
import me.xiajhuan.summer.admin.common.locale.entity.LocaleInternationalNameEntity;
import me.xiajhuan.summer.admin.common.locale.mapper.LocaleInternationalNameMapper;
import me.xiajhuan.summer.admin.common.locale.service.LocaleInternationalNameService;
import me.xiajhuan.summer.core.mp.standard.MpCommonOperation;
import org.springframework.stereotype.Service;

/**
 * 国际化名称 ServiceImpl
 *
 * @author xiajhuan
 * @date 2023/3/16
 */
@Service
public class LocaleInternationalNameServiceImpl extends ServiceImpl<LocaleInternationalNameMapper, LocaleInternationalNameEntity> implements LocaleInternationalNameService, MpCommonOperation<LocaleInternationalNameDto, LocaleInternationalNameEntity> {

    //*******************MpCommonOperation覆写开始********************


    //*******************MpCommonOperation覆写结束********************

}
