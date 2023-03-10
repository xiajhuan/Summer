package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;

import java.util.Date;

/**
 * 部门 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_dept")
public class SecurityDeptEntity extends SimpleBaseEntity {

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
     * 上级部门ID
     */
    private Long pid;

    /**
     * 所有上级部门ID，用“,”分隔
     */
    private String pidAll;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;

}