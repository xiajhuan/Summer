package me.xiajhuan.summer.common.mp.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import me.xiajhuan.summer.common.entity.CommonBaseEntity;
import me.xiajhuan.summer.common.constant.DataScopeConst;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.enums.DataScopeEnum;
import me.xiajhuan.summer.common.enums.UserTypeEnum;
import me.xiajhuan.summer.common.security.login.LoginUser;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import me.xiajhuan.summer.common.utils.WildcardUtil;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限处理器
 * <p>
 * note1：表对应Entity必须包含create_by,dept_id字段，推荐继承 {@link CommonBaseEntity}
 * note2：若想局部关闭数据权限过滤，可以采用以下两种方式：
 * <pre>
 *    1.在Mapper和Mapper.Method上使用 {@link InterceptorIgnore}
 *    2.在common.setting中配置Mp组下的dataScope.ignore
 * </pre>
 * </p>
 * https://github.com/JSQLParser/JSqlParser
 *
 * @author xiajhuan
 * @date 2023/3/7
 * @see DataPermissionInterceptor#setWhere(PlainSelect, String)
 * @see MultiDataPermissionHandler#getSqlSegment(Table, Expression, String)
 */
@Component
public class DataScopeHandler implements MultiDataPermissionHandler {

    @Resource(name = SettingBeanConst.COMMON)
    private Setting setting;

    /**
     * 数据权限忽略的表名称数组
     */
    private static String[] dataScopeIgnoreArray = null;

    @PostConstruct
    void init() {
        String dataScopeIgnore = setting.getByGroup("dataScope.ignore", "Mp");

        if (StrUtil.isNotBlank(dataScopeIgnore)) {
            dataScopeIgnoreArray = dataScopeIgnore.split(StrPool.COMMA);
        }
    }

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        if (ArrayUtil.isNotEmpty(dataScopeIgnoreArray) && ignoreMatches(table.getName())) {
            // 忽略的表
            return null;
        }

        // 为了兼容隐式内连接，没有别名时条件就需要加上表名
        final String prefix;
        if (table.getAlias() != null) {
            prefix = table.getAlias().getName() + StrPool.DOT;
        } else {
            prefix = table.getName() + StrPool.DOT;
        }

        return getParenthesis(prefix);
    }

    /**
     * 检查表名是否匹配被忽略的表配置
     *
     * @param tableName 表名
     * @return 是否匹配，true：匹配 false：不匹配
     */
    private boolean ignoreMatches(String tableName) {
        for (int i = 0; i < dataScopeIgnoreArray.length; i++) {
            if (WildcardUtil.matches(tableName, dataScopeIgnoreArray[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 {@link Parenthesis}
     *
     * @param prefix 前缀
     * @return {@link Parenthesis}
     */
    private Parenthesis getParenthesis(String prefix) {
        // note：这里默认是内部接口，开放接口不适用数据权限功能，所以必然存在登录用户
        LoginUser loginUser = SecurityUtil.getLoginUser();

        // 超级管理员或拥有所有数据权限的用户不做处理
        if (UserTypeEnum.SUPER_ADMIN.getValue() == loginUser.getUserType()
                || DataScopeEnum.ALL.getValue() == loginUser.getDataScope()) {
            return null;
        }

        //*******************追加过滤Sql片段********************

        final Expression filterCondition;
        switch (loginUser.getDataScope()) {
            case 1:
                filterCondition = getFilterConditionByDeptIds(prefix, loginUser.getDeptIdSet());
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
                        .withLeftExpression(new Column(prefix + DataScopeConst.USERNAME_RECORDER))
                        .withRightExpression(new StringValue(loginUser.getUsername()));
                break;
            default:
                throw new IllegalArgumentException(StrUtil.format("不支持的数据权限类型【{}】", loginUser.getDataScope()));
        }

        return new Parenthesis(filterCondition);
    }

    /**
     * 根据部门ID获取过滤条件<br>
     * 基于 角色/本部门/本部门及以下
     *
     * @param prefix     前缀
     * @param deptIdSet 部门ID集合
     * @return {@link Expression}
     * @see DataScopeEnum#ROLE_BASED
     * @see DataScopeEnum#DEPT_SELF
     * @see DataScopeEnum#DEPT_AND_CHILD
     */
    private Expression getFilterConditionByDeptIds(String prefix, Set<Long> deptIdSet) {
        // Sql片段示例：dept_id IN (1,2,3)
        return new InExpression()
                .withLeftExpression(new Column(prefix + DataScopeConst.DEPT_ID_RECORDER))
                .withRightItemsList(new ExpressionList(deptIdSet.stream().map(LongValue::new).collect(Collectors.toList())));
    }

}
