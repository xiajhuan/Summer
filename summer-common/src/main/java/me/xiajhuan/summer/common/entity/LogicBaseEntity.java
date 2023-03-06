package me.xiajhuan.summer.common.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 逻辑删除 Entity基类<br>
 * 包含【id,create_by,create_time,update_by,update_time,dept_id,is_del】字段
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class LogicBaseEntity extends CommonBaseEntity {

    /**
     * 逻辑删除标识（0：未删除 1：已删除）
     */
    @TableLogic
    private Integer isDel;

}
