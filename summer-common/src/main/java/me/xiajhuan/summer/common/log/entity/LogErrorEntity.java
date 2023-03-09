package me.xiajhuan.summer.common.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;

/**
 * 错误日志 Entity
 *
 * @author xiajhuan
 * @date 2022/11/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("log_error")
public class LogErrorEntity extends SimpleBaseEntity {

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 异常堆栈信息
     */
    private String errorInfo;

}