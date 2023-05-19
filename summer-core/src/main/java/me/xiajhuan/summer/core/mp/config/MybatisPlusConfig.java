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

package me.xiajhuan.summer.core.mp.config;

import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.mp.handler.DataScopeHandler;
import me.xiajhuan.summer.core.mp.injector.CustomSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @author xiajhuan
 * @date 2022/11/26
 */
@Configuration
@MapperScan("me.xiajhuan.summer.**.mapper")
public class MybatisPlusConfig {

    /**
     * 注册{@link MybatisPlusInterceptor}，<br>
     * 集成【数据权限/分页/乐观锁/防止全表更新与删除】插件功能
     *
     * @param dataScopeHandler 数据权限处理器
     * @param setting          {@link Setting}
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(DataScopeHandler dataScopeHandler, @Qualifier(SettingConst.CORE) Setting setting) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 数据权限，note：必须在分页插件之前，否则“COUNT(*)语句”不会加入数据权限过滤条件
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(dataScopeHandler);
        mybatisPlusInterceptor.addInnerInterceptor(dataPermissionInterceptor);
        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(setting.getBool("page.handle-overflow", "Mp", true));
        paginationInnerInterceptor.setMaxLimit(setting.getLong("page.max-size-limit", "Mp", 1000L));
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 乐观锁
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor(true));
        // 防止全表更新与删除
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return mybatisPlusInterceptor;
    }

    /**
     * 注册自定义Sql注入器
     *
     * @return 自定义Sql注入器
     */
    @Bean
    public CustomSqlInjector customSqlInjector() {
        return CustomSqlInjector.of();
    }

}
