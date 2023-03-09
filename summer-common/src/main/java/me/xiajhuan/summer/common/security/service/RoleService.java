package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.security.dto.RoleDto;
import me.xiajhuan.summer.common.security.entity.RoleEntity;
import me.xiajhuan.summer.common.security.excel.RoleExcel;

import java.util.List;

/**
 * 角色 Service
 *
 * @author xiajhuan
 * @date 2023/3/9
 */
public interface RoleService extends IService<RoleEntity> {

    IPage<RoleDto> page(PageAndSort pageAndSort, RoleDto dto);

    List<RoleDto> list(RoleDto dto);

    void add(RoleDto dto);

    void update(RoleDto dto);

    void delete(String[] ids);

    List<RoleExcel> excelTemplate();

}
