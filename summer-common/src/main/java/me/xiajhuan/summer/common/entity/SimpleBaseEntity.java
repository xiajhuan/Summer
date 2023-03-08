package me.xiajhuan.summer.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 简单 Entity基类<br>
 * 包含【id,create_by,create_time】字段
 *
 * @author xiajhuan
 * @date 2022/11/19
 */
@Data
public abstract class SimpleBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，默认策略：主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者<br>
     * 使用数据权限功能则必须包含此字段
     */
    @TableField(fill = FieldFill.INSERT, value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private Date createTime;

}
