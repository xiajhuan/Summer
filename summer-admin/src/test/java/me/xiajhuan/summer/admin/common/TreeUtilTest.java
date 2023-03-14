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

package me.xiajhuan.summer.admin.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.admin.common.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.core.utils.TreeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 树形结构工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
@SpringBootTest
public class TreeUtilTest {

    /**
     * 菜单列表
     */
    private static List<SecurityMenuDto> menuDtoList;

    /**
     * 初始化菜单列表
     */
    @BeforeEach
    public void init() {
        menuDtoList = CollUtil.newArrayList(
                SecurityMenuDto.builder()
                        .id(1L).parentId(0L).name("系统管理").weight(5)
                        .url("/sys").permissions("sys").build(),
                SecurityMenuDto.builder()
                        .id(2L).parentId(1L).name("用户管理").weight(222222)
                        .url("/sys/user").permissions("sys:user").build(),
                SecurityMenuDto.builder()
                        .id(3L).parentId(1L).name("用户添加").weight(0)
                        .url("/sys/userAdd").permissions("sys:userAdd").build(),
                SecurityMenuDto.builder()
                        .id(4L).parentId(0L).name("店铺管理").weight(1)
                        .url("/shop").permissions("shop").build(),
                SecurityMenuDto.builder()
                        .id(5L).parentId(4L).name("商品管理").weight(44)
                        .url("/shop/product").permissions("shop:product").build(),
                SecurityMenuDto.builder()
                        .id(6L).parentId(4L).name("商品管理2").weight(2)
                        .url("/shop/product2").permissions("shop:product2").build()
        );
    }

    /**
     * @see TreeUtil#buildWithD(Class, List, Long, String...)
     */
    @Test
    public void test() {
        List<SecurityMenuDto> treeNodes = TreeUtil.buildWithD(SecurityMenuDto.class, menuDtoList, 0L,
                "url", "permissions");
        Assertions.assertTrue(treeNodes.size() == 2, "菜单树构建失败！");
        Console.log(JSONUtil.toJsonPrettyStr(treeNodes));

        Console.log("Successful test!");
    }

}