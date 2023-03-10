package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.data.PageAndSort;
import me.xiajhuan.summer.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.common.security.entity.SecurityDeptEntity;

/**
 * 部门 Service
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
public interface SecurityDeptService extends IService<SecurityDeptEntity> {

    IPage<SecurityDeptDto> page(PageAndSort pageAndSort, SecurityDeptDto dto);

    void add(SecurityDeptDto dto);

    void update(SecurityDeptDto dto);

    void delete(String[] ids);

}
