package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.security.dto.SecurityRoleDto;
import me.xiajhuan.summer.common.security.entity.SecurityRoleEntity;
import me.xiajhuan.summer.common.security.excel.SecurityRoleExcel;

import java.util.List;

/**
 * 角色 Service
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public interface SecurityRoleService extends IService<SecurityRoleEntity> {

    IPage<SecurityRoleDto> page(PageAndSort pageAndSort, SecurityRoleDto dto);

    List<SecurityRoleDto> list(SecurityRoleDto dto);

    void add(SecurityRoleDto dto);

    void update(SecurityRoleDto dto);

    void delete(String[] ids);

    List<SecurityRoleExcel> excelTemplate();

}
