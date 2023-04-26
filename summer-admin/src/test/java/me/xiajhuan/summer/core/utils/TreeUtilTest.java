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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.CommonMsg;
import me.xiajhuan.summer.system.security.dto.SecurityMenuDto;
import me.xiajhuan.summer.core.constant.TreeConst;
import org.junit.jupiter.api.*;

import java.util.List;

/**
 * 树形结构工具 Test
 *
 * @author xiajhuan
 * @date 2023/3/11
 */
@DisplayName("TreeUtil")
public class TreeUtilTest implements CommonMsg {

    /**
     * 菜单列表
     */
    private static List<SecurityMenuDto> menuDtoList;

    /**
     * 初始化 {@link menuDtoList}
     */
    @BeforeEach
    void init() {
        SecurityMenuDto dto1 = new SecurityMenuDto();
        dto1.setId(1L);
        dto1.setParentId(TreeConst.ROOT);
        dto1.setName("系统管理");
        dto1.setWeight(5);
        dto1.setUrl("/sys");
        dto1.setPermissions("sys");

        SecurityMenuDto dto2 = new SecurityMenuDto();
        dto2.setId(2L);
        dto2.setParentId(1L);
        dto2.setName("用户管理");
        dto2.setWeight(222222);
        dto2.setUrl("/sys/user");
        dto2.setPermissions("sys:user");

        SecurityMenuDto dto3 = new SecurityMenuDto();
        dto3.setId(3L);
        dto3.setParentId(1L);
        dto3.setName("用户添加");
        dto3.setWeight(0);
        dto3.setUrl("/sys/userAdd");
        dto3.setPermissions("sys:userAdd");

        SecurityMenuDto dto4 = new SecurityMenuDto();
        dto4.setId(4L);
        dto4.setParentId(TreeConst.ROOT);
        dto4.setName("店铺管理");
        dto4.setWeight(1);
        dto4.setUrl("/shop");
        dto4.setPermissions("shop");

        SecurityMenuDto dto5 = new SecurityMenuDto();
        dto5.setId(5L);
        dto5.setParentId(4L);
        dto5.setName("商品管理");
        dto5.setWeight(44);
        dto5.setUrl("/shop/product");
        dto5.setPermissions("shop:product");

        SecurityMenuDto dto6 = new SecurityMenuDto();
        dto6.setId(6L);
        dto6.setParentId(4L);
        dto6.setName("商品管理2");
        dto6.setWeight(2);
        dto6.setUrl("/shop/product2");
        dto6.setPermissions("shop:product2");

        menuDtoList = ListUtil.of(dto1, dto2, dto3, dto4, dto5, dto6);
    }

    /**
     * @see TreeUtil#buildDto(Class, List, long, String...)
     */
    @Test
    void buildDto() {
        String methodSignature = "TreeUtil#buildDto(Class, List, long, String...)";
        Console.log(startMsg(methodSignature));

        List<SecurityMenuDto> treeNodes = TreeUtil.buildDto(SecurityMenuDto.class, menuDtoList, TreeConst.ROOT,
                "url", "permissions");
        Assertions.assertEquals(2, treeNodes.size(), "菜单树构建失败！");
        Console.log(JSONUtil.toJsonPrettyStr(treeNodes));

        Console.log(successMsg(methodSignature));
    }

}
