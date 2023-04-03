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

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.constant.TreeConst;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 树形结构工具
 * <pre>
 *     1.Dto类型必须继承 {@link TreeNode}
 *     2.【id,parentId】必须为 {@link Long}，【weight】必须可比较 {@link Comparable}
 *     3.【id,parentId,name,weight】以外的属性都为扩展属性 {@link TreeConst.Extra}
 * </pre>
 *
 * @author xiajhuan
 * @date 2023/3/10
 * @see cn.hutool.core.lang.tree.TreeUtil
 */
public class TreeUtil extends cn.hutool.core.lang.tree.TreeUtil {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 构造TreeUtil
     */
    private TreeUtil() {
    }

    /**
     * {@link TreeNodeConfig}
     */
    private static TreeNodeConfig treeNodeConfig;

    /**
     * 初始化 {@link treeNodeConfig}
     */
    static {
        int deep = SpringUtil.getBean(SettingConst.CORE, Setting.class)
                .getInt("deep", "Tree", 5);
        deep = Math.max(deep, 0);

        treeNodeConfig = TreeNodeConfig.DEFAULT_CONFIG.setDeep(deep);
    }

    /**
     * 构建树（List<DtoClass>）
     *
     * @param dtoClass   DtoClass
     * @param dtoList    Dto类型列表
     * @param rootId     Root节点ID
     * @param extraField 扩展属性
     * @param <D>        Dto类型
     * @return 树形结构列表（DtoClass）
     */
    public static <D extends TreeNode> List<D> buildDto(Class<D> dtoClass, List<D> dtoList, Long rootId, String... extraField) {
        return BeanUtil.convert(build(dtoClass, dtoList, rootId, extraField), dtoClass);
    }

    /**
     * 构建树（List<Tree>）
     *
     * @param dtoClass   DtoClass
     * @param dtoList    Dto类型列表
     * @param rootId     Root节点ID
     * @param extraField 扩展属性
     * @param <D>        Dto类型
     * @return 树形结构列表（{@link Tree}）
     */
    public static <D extends TreeNode> List<Tree<Long>> build(Class<D> dtoClass, List<D> dtoList, Long rootId, String... extraField) {
        // 获取扩展属性的Getter
        final Map<String, Method> extraGetters = getExtraGetters(dtoClass, extraField);

        return build(dtoList, rootId, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId((Long) treeNode.getId());
                    tree.setParentId((Long) treeNode.getParentId());
                    tree.setName(treeNode.getName());
                    tree.setWeight(treeNode.getWeight());
                    // 扩展属性
                    if (extraGetters.size() > 0) {
                        extraGetters.forEach((extra, getter) -> {
                            try {
                                tree.putExtra(extra, getter.invoke(treeNode));
                            } catch (IllegalAccessException e) {
                                LOGGER.error(e, e.getMessage());
                            } catch (InvocationTargetException e) {
                                LOGGER.error(e, e.getMessage());
                            }
                        });
                    }
                });
    }

    /**
     * 获取扩展属性的Getter Map<br>
     * Key：扩展属性 Value：扩展属性的Getter
     *
     * @param dtoClass   DtoClass
     * @param extraField 扩展属性
     * @param <D>        Dto类型
     * @return 扩展属性的Getter Map
     */
    private static <D extends TreeNode> Map<String, Method> getExtraGetters(Class<D> dtoClass, String... extraField) {
        final Map<String, Method> extraGetters = MapUtil.newHashMap(true);
        if (ArrayUtil.isNotEmpty(extraField)) {
            // 去重
            Set<String> extraSet = Arrays.stream(extraField).collect(Collectors.toSet());
            extraSet.forEach(extra -> {
                // 以“get”开头的方法
                Method extraGetter = ReflectUtil.getMethodByNameIgnoreCase(dtoClass, StrUtil.format("get{}", extra));
                if (extraGetter == null) {
                    // 以“is”开头的方法
                    extraGetter = ReflectUtil.getMethodByNameIgnoreCase(dtoClass, StrUtil.format("is{}", extra));
                }
                if (extraGetter != null) {
                    extraGetters.put(extra, extraGetter);
                } else {
                    LOGGER.warn("【[{}]不存在扩展属性[{}]】", dtoClass.getSimpleName(), extra);
                }
            });
        }
        return extraGetters;
    }

}
