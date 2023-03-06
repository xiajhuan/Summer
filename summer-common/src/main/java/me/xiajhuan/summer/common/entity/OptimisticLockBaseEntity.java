package me.xiajhuan.summer.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 乐观锁控制 Entity基类<br>
 * 包含【id,create_by,create_time,update_by,update_time,dept_id,version】字段
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class OptimisticLockBaseEntity extends CommonBaseEntity {

    /**
     * 乐观锁控制
     */
    @Version
    @TableField(fill = FieldFill.INSERT, value = "version")
    private Integer version;

}
