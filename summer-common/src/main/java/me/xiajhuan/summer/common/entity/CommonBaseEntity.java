package me.xiajhuan.summer.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 通用 Entity基类<br>
 * 包含【id,create_by,create_time,update_by,update_time,dept_id】字段
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CommonBaseEntity extends SimpleBaseEntity {

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private Date updateTime;

    /**
     * 部门ID<br>
     * 使用数据权限功能则必须包含此字段
     */
    @TableField(fill = FieldFill.INSERT)
    private Long deptId;

}
