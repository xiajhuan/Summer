package me.xiajhuan.summer.common.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;
import me.xiajhuan.summer.common.enums.LoginOperationEnum;
import me.xiajhuan.summer.common.enums.LoginStatusEnum;

/**
 * 登录日志 Entity
 *
 * @author xiajhuan
 * @date 2023/3/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("log_login")
public class LogLoginEntity extends SimpleBaseEntity {

    /**
     * 登录用户
     */
    private String loginUser;

    /**
     * 用户操作
     *
     * @see LoginOperationEnum
     */
    private Integer operation;

    /**
     * 登录状态
     *
     * @see LoginStatusEnum
     */
    private Integer status;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

}
