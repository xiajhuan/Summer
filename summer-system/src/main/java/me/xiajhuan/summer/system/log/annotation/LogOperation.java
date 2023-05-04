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

package me.xiajhuan.summer.system.log.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.*;

/**
 * 操作日志注解<br>
 * note：添加了{@link AliasFor}必须通过{@link AnnotationUtils}获取才会生效
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {

    @AliasFor("name")
    String value() default StrUtil.EMPTY;

    /**
     * 操作名称
     */
    @AliasFor("value")
    String name() default StrUtil.EMPTY;

    /**
     * 是否保存请求参数，true：是 false：否
     */
    boolean saveRequestParam() default true;

}
