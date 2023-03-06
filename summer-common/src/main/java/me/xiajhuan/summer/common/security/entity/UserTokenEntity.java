package me.xiajhuan.summer.common.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;

import java.util.Date;

/**
 * 用户Token Entity
 *
 * @author xiajhuan
 * @date 2023/02/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("c_user_token")
public class UserTokenEntity extends SimpleBaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * accessToken
     */
    private String token;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private Date updateTime;

}