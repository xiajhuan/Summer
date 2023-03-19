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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
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
public class TreeUtil {

    private static final Log LOGGER = LogFactory.get();

    /**
     * {@link TreeNodeConfig}
     */
    private static TreeNodeConfig treeNodeConfig;

    /**
     * 初始化 {@link treeNodeConfig}
     */
    static {
        int deep = SpringContextUtil.getBean(SettingBeanConst.CORE, Setting.class)
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
        return ConvertUtil.convert(build(dtoClass, dtoList, rootId, extraField), dtoClass);
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

        return cn.hutool.core.lang.tree.TreeUtil.build(dtoList, rootId, treeNodeConfig,
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
     * 获取ID对应的节点，如果有多个ID相同的节点，只返回第一个<br>
     * 此方法只查找此节点及子节点，采用递归深度优先遍历
     *
     * @param node 要查找的节点
     * @param id   ID
     * @return 节点（{@link Tree}）
     */
    public static Tree<Long> getNode(Tree<Long> node, Long id) {
        return cn.hutool.core.lang.tree.TreeUtil.getNode(node, id);
    }

    /**
     * 获取所有父节点名称列表
     * <p>
     * 比如有个人在研发一部，他上面有研发部，接着上面有技术中心，若包含自己，
     * 返回结果就是：[研发一部, 研发中心, 技术中心]
     * </p>
     *
     * @param node               节点
     * @param includeCurrentNode 是否包含当前节点的名称 true：包含 false：不包含
     * @return 所有父节点名称列表，node为 {@code null} 返回空List
     */
    public static List<CharSequence> getParentsName(Tree<Long> node, boolean includeCurrentNode) {
        return cn.hutool.core.lang.tree.TreeUtil.getParentsName(node, includeCurrentNode);
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
                    LOGGER.warn("TreeUtil WARN【[{}]不存在扩展属性[{}]】", dtoClass.getSimpleName(), extra);
                }
            });
        }
        return extraGetters;
    }

}
