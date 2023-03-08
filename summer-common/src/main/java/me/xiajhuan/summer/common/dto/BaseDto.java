package me.xiajhuan.summer.common.dto;

import cn.hutool.core.lang.Dict;
import lombok.Data;
import me.xiajhuan.summer.common.validation.group.AddGroup;
import me.xiajhuan.summer.common.validation.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * Dto基类
 *
 * @author xiajhuan
 * @date 2022/3/6
 */
@Data
public abstract class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    /**
     * 参数
     *
     * @see Dict
     */
    private Dict params = Dict.create();

}
