package me.xiajhuan.summer.common.security.excel.parser;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.excel.subClass.ExcelDbParser;
import me.xiajhuan.summer.common.security.entity.SecurityRoleEntity;
import me.xiajhuan.summer.common.security.excel.SecurityRoleExcel;
import me.xiajhuan.summer.common.security.mapper.SecurityRoleMapper;
import me.xiajhuan.summer.common.utils.SpringContextUtil;

import java.util.stream.Collectors;

/**
 * 角色Excel数据解析（保存到Db）
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public class SecurityRoleExcelDbParser extends ExcelDbParser<SecurityRoleEntity, SecurityRoleExcel> {

    private SecurityRoleMapper securityRoleMapper;

    /**
     * 构造SecurityRoleExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     */
    private SecurityRoleExcelDbParser(IService<SecurityRoleEntity> service, Class<SecurityRoleEntity> currentEntityClass) {
        super(service, currentEntityClass);

        securityRoleMapper = SpringContextUtil.getBean("securityRoleMapper", SecurityRoleMapper.class);
    }

    /**
     * 构建SecurityRoleExcelDbParser
     *
     * @param service            {@link IService}
     * @param currentEntityClass 当前EntityClass
     * @return SecurityRoleExcelDbParser
     */
    public static SecurityRoleExcelDbParser of(IService<SecurityRoleEntity> service, Class<SecurityRoleEntity> currentEntityClass) {
        return new SecurityRoleExcelDbParser(service, currentEntityClass);
    }

    @Override
    protected void handleParsedDataBefore() {
        // 过滤掉角色名称重复的角色
        entityList = entityList.stream()
                .filter(o -> securityRoleMapper.isExistByName(o.getName()) == null)
                .collect(Collectors.toList());
    }

}
