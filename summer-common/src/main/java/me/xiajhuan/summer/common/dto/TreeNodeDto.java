package me.xiajhuan.summer.common.dto;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 树节点Dto
 * <p>
 * 树节点基类，需要构建树形结构则需继承此类，
 * 如：菜单，部门等
 * </p>
 *
 * @author xiajhuan
 * @date 2023/3/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TreeNodeDto<D> extends BaseDto {

    /**
     * 上级ID
     */
    @NotNull(message = "{pid.require}", groups = {AddGroup.class, UpdateGroup.class})
    private Long pid;

    /**
     * 子节点列表
     */
    private List<D> children = CollUtil.newArrayList();

}
