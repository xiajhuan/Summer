package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;
import me.xiajhuan.summer.common.enums.ComponentTypeEnum;
import me.xiajhuan.summer.common.enums.OpenModeEnum;

import java.util.Date;

/**
 * 菜单 Entity
 *
 * @author xiajhuan
 * @date 2023/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_menu")
public class SecurityMenuEntity extends SimpleBaseEntity {

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
     * 父菜单ID，一级菜单为0
     */
    private Long pid;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权
     * <p>
     * 多个授权以“,”分隔
     * 格式：模块名:业务名:操作名
     * 例如：
     * <pre>
     *     1.security:user:page,security:user:add
     *     2.log:error:page,log:error:excelExport
     * </pre>
     * </p>
     */
    private String permissions;

    /**
     * 类型
     *
     * @see ComponentTypeEnum
     */
    private Integer type;

    /**
     * 打开方式
     *
     * @see OpenModeEnum
     */
    private Integer openMode;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 上级菜单名称
     */
    @TableField(exist = false)
    private String parentName;

}