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

package me.xiajhuan.summer.core.mp.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import me.xiajhuan.summer.core.data.LoginUser;
import me.xiajhuan.summer.core.base.entity.CommonEntity;
import me.xiajhuan.summer.core.constant.DataScopeConst;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.enums.NonLoggedUserEnum;
import me.xiajhuan.summer.core.enums.UserTypeEnum;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import me.xiajhuan.summer.core.utils.WildcardUtil;
import net.sf.jsqlparser.expression.Expression;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限处理器，note：
 * <pre>
 *   1.表对应Entity必须包含create_by,dept_id字段，推荐继承 {@link CommonEntity}
 *   2.对”SELECT/COUNT/UPDATE/DELETE语句“生效
 *   3.若想局部关闭数据权限过滤，可以采用以下两种方式：
 *     3.1 在Mapper和Mapper.Method上使用 {@link InterceptorIgnore}
 *     3.2 在common.setting中配置Mp组下的dataScope.ignore
 * </pre>
 * 参考：https://gitee.com/baomidou/mybatis-plus/pulls/219
 *
 * @author xiajhuan
 * @date 2023/3/7
 * @see MultiDataPermissionHandler#getSqlSegment(Table, Expression, String)
 * @see DataPermissionInterceptor#setWhere(PlainSelect, String)
 */
@Component
public class DataScopeHandler implements MultiDataPermissionHandler {

    @Resource(name = SettingConst.CORE)
    private Setting setting;

    /**
     * 数据权限忽略的表名数组
     */
    private String[] dataScopeIgnoreArray;

    /**
     * 初始化 {@link dataScopeIgnoreArray}
     */
    @PostConstruct
    private void init() {
        String dataScopeIgnore = setting.getByGroup("data-scope.ignore", "Mp");

        if (StrUtil.isNotBlank(dataScopeIgnore)) {
            dataScopeIgnoreArray = dataScopeIgnore.split(StrPool.COMMA);
        }
    }

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        // note：数据权限功能只适用于内部接口，非登录用户不受限制
        if (loginUser.getId() == null) {
            return null;
        }

        // 超级管理员或拥有所有数据权限的用户不做处理
        if (UserTypeEnum.SUPER_ADMIN.getValue() == loginUser.getUserType()
                || DataScopeConst.Type.ALL.getValue() == loginUser.getDataScope()) {
            return null;
        }

        // 表名匹配被忽略的表配置则不过滤数据权限
        if (ArrayUtil.isNotEmpty(dataScopeIgnoreArray)
                && Arrays.stream(dataScopeIgnoreArray)
                .anyMatch(ignore -> WildcardUtil.matches(table.getName(), ignore))) {
            return null;
        }

        // 为了兼容隐式内连接，没有别名时条件就需要加上表名
        final String prefix;
        if (table.getAlias() != null) {
            prefix = table.getAlias().getName() + StrPool.DOT;
        } else {
            prefix = table.getName() + StrPool.DOT;
        }

        return getParenthesis(prefix, loginUser);
    }

    /**
     * 获取 {@link Parenthesis}（追加过滤Sql片段）
     *
     * @param prefix    前缀
     * @param loginUser 登录用户信息
     * @return {@link Parenthesis}
     * @see NonLoggedUserEnum
     */
    private Parenthesis getParenthesis(String prefix, LoginUser loginUser) {
        final Expression filterCondition;
        switch (loginUser.getDataScope()) {
            case 1:
                filterCondition = getFilterConditionByDeptIds(prefix, loginUser.getDeptIdRoleBasedSet());
                break;
            case 2:
                filterCondition = getFilterConditionByDeptIds(prefix, CollUtil.newHashSet(loginUser.getDeptId()));
                break;
            case 3:
                filterCondition = getFilterConditionByDeptIds(prefix, loginUser.getDeptAndChildIdSet());
                break;
            case 4:
                // 仅本人
                // Sql片段示例：t.create_by = '16042'
                filterCondition = new EqualsTo()
                        .withLeftExpression(new Column(prefix + DataScopeConst.Recorder.USERNAME))
                        .withRightExpression(new StringValue(loginUser.getUsername()));
                break;
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的数据权限类型【{}】", loginUser.getDataScope()));
        }

        return new Parenthesis(filterCondition);
    }

    /**
     * 根据部门ID集合获取过滤条件<br>
     * 基于 角色/本部门/本部门及以下
     *
     * @param prefix    前缀
     * @param deptIdSet 部门ID集合
     * @return {@link Expression}
     * @see DataScopeConst.Type#ROLE_BASED
     * @see DataScopeConst.Type#DEPT_SELF
     * @see DataScopeConst.Type#DEPT_AND_CHILD
     */
    private Expression getFilterConditionByDeptIds(String prefix, Set<Long> deptIdSet) {
        // Sql片段示例：dept_id IN (1,2,3)
        return new InExpression()
                .withLeftExpression(new Column(prefix + DataScopeConst.Recorder.DEPT_ID))
                .withRightItemsList(new ExpressionList(deptIdSet.stream().map(LongValue::new).collect(Collectors.toList())));
    }

}
