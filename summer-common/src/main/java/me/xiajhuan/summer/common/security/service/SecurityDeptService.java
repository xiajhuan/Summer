package me.xiajhuan.summer.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.xiajhuan.summer.common.security.dto.SecurityDeptDto;
import me.xiajhuan.summer.common.security.entity.SecurityDeptEntity;

import java.util.List;

/**
 * 部门 Service
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
public interface SecurityDeptService extends IService<SecurityDeptEntity> {

    List<SecurityDeptDto> tree();

    void add(SecurityDeptDto dto);

    void update(SecurityDeptDto dto);

    void delete(String[] ids);

}
