package me.xiajhuan.summer.common.log.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import me.xiajhuan.summer.common.entity.SimpleBaseEntity;
import me.xiajhuan.summer.common.enums.OperationGroupEnum;
import me.xiajhuan.summer.common.enums.OperationStatusEnum;

/**
 * 操作日志 Entity
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("c_log_operation")
public class LogOperationEntity extends SimpleBaseEntity {

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 操作分组
     *
     * @see OperationGroupEnum
     */
    private Integer operationGroup;

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
     * 请求时长（ms）
     */
    private Integer requestTime;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 状态
     *
     * @see OperationStatusEnum
     */
    private Integer status;

    /**
     * 操作人
     */
    private String operateBy;

    //*******************改造lombok @Builder生成代码********************

    public static LogOperationEntityBuilder builder() {
        return new LogOperationEntityBuilder();
    }

    public static class LogOperationEntityBuilder {

        private String operation;

        private Integer operationGroup;

        private String requestUri;

        private String requestMethod;

        private String requestParams;

        private Integer requestTime;

        private String userAgent;

        private String ip;

        private Integer status;

        private String operateBy;

        LogOperationEntityBuilder() {
        }

        public LogOperationEntityBuilder operation(final String operation) {
            if (StrUtil.isNotBlank(operation)) {
                this.operation = operation;
            }
            return this;
        }

        public LogOperationEntityBuilder operationGroup(final Integer operationGroup) {
            this.operationGroup = operationGroup;
            return this;
        }

        public LogOperationEntityBuilder requestUri(final String requestUri) {
            this.requestUri = requestUri;
            return this;
        }

        public LogOperationEntityBuilder requestMethod(final String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public LogOperationEntityBuilder requestParams(final String requestParams) {
            this.requestParams = requestParams;
            return this;
        }

        public LogOperationEntityBuilder requestTime(final Integer requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public LogOperationEntityBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public LogOperationEntityBuilder ip(final String ip) {
            this.ip = ip;
            return this;
        }

        public LogOperationEntityBuilder status(final Integer status) {
            this.status = status;
            return this;
        }

        public LogOperationEntityBuilder operateBy(final String operateBy) {
            this.operateBy = operateBy;
            return this;
        }

        public LogOperationEntity build() {
            return new LogOperationEntity(this.operation, this.operationGroup, this.requestUri, this.requestMethod, this.requestParams, this.requestTime, this.userAgent, this.ip, this.status, this.operateBy);
        }

        public String toString() {
            return "LogOperationEntity.LogOperationEntityBuilder(operation=" + this.operation + ", operationGroup=" + operationGroup + ", requestUri=" + this.requestUri + ", requestMethod=" + this.requestMethod + ", requestParams=" + this.requestParams + ", requestTime=" + this.requestTime + ", userAgent=" + this.userAgent + ", ip=" + this.ip + ", status=" + this.status + ", operateBy=" + this.operateBy + ")";
        }

    }

}