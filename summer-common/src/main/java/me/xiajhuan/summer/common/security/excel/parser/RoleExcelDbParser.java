package me.xiajhuan.summer.common.security.excel.parser;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.excel.subClass.ExcelDbParser;
import me.xiajhuan.summer.common.security.entity.RoleEntity;
import me.xiajhuan.summer.common.security.excel.RoleExcel;
import me.xiajhuan.summer.common.security.mapper.RoleMapper;
import me.xiajhuan.summer.common.utils.SpringContextUtil;

import java.util.stream.Collectors;

/**
 * 角色Excel数据解析（保存到Db）
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public class RoleExcelDbParser extends ExcelDbParser<RoleEntity, RoleExcel> {

    private RoleMapper roleMapper;

    /**
     * 构造RoleExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     */
    private RoleExcelDbParser(IService<RoleEntity> service, Class<RoleEntity> currentEntityClass) {
        super(service, currentEntityClass);

        roleMapper = SpringContextUtil.getBean("roleMapper", RoleMapper.class);
    }

    /**
     * 构建RoleExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     * @return RoleExcelDbParser
     */
    public static RoleExcelDbParser of(IService<RoleEntity> service, Class<RoleEntity> currentEntityClass) {
        return new RoleExcelDbParser(service, currentEntityClass);
    }

    @Override
    protected void handleParsedDataBefore() {
        // 过滤掉角色名称重复的角色
        entityList = entityList.stream()
                .filter(o -> roleMapper.isExistByName(o.getName()) == null)
                .collect(Collectors.toList());
    }

}
