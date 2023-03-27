/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * summer-single is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.admin.common.base.handler.exception;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingBeanConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.*;
import me.xiajhuan.summer.admin.common.log.service.LogErrorService;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import me.xiajhuan.summer.core.ratelimiter.aspect.RateLimiterAspect;
import me.xiajhuan.summer.core.interceptor.ContentTypeInterceptor;
import me.xiajhuan.summer.core.interceptor.SqlInjectionInterceptor;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import me.xiajhuan.summer.core.utils.ValidationUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * 通用全局异常处理
 *
 * @author xiajhuan
 * @date 2022/11/26
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingBeanConst.COMMON)
    private Setting commonSetting;

    @Resource
    private LogErrorService logErrorService;

    /**
     * 是否记录业务异常（BusinessException）日志
     */
    private boolean enableBusinessErrorLog;

    /**
     * 初始化 {@link enableBusinessErrorLog}
     */
    @PostConstruct
    private void init() {
        enableBusinessErrorLog = commonSetting.getBool("error.enable-business", "Log", true);
    }

    /**
     * 异常处理（所有受检/非受检异常）
     *
     * @param e {@link Exception}
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        // 处理限流切面的异常
        Exception cause = (Exception) e.getCause();
        if (cause != null) {
            e = cause;
        }

        return logAndResponse(e, enableBusinessErrorLog);
    }

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return 响应结果
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleBusinessException(BusinessException e) {
        return logAndResponse(e, enableBusinessErrorLog);
    }

    /**
     * 参数校验（Dto类型）异常处理（注解校验）
     *
     * @param e {@link BindException}
     * @return 响应结果
     * @see Validated
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException e) {
        StringBuilder message = StrUtil.builder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error -> message.append(error.getField())
                .append("【").append(error.getDefaultMessage()).append("】")
                .append(StrPool.COMMA));

        String msg = message.substring(0, message.length() - 1);
        logDebug(e, msg);
        return Result.ofFail(msg);
    }

    /**
     * 参数校验（Dto类型）异常处理（api校验）
     *
     * @param e 校验异常
     * @return 响应结果
     * @see ValidationUtil#validate(List, Class[])
     * @see ValidationUtil#validate(Object, Class[])
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleValidationException(ValidationException e) {
        String msg = e.getMessage();
        logDebug(e, msg);
        return Result.ofFail(msg);
    }

    /**
     * 文件上传/下载异常处理
     *
     * @param uploadException   文件上传异常
     * @param downloadException 文件下载异常
     */
    @ExceptionHandler({FileUploadException.class, FileDownloadException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleFileUploadOrDownloadException(FileUploadException uploadException, FileDownloadException downloadException) {
        final String msg;
        if (uploadException != null) {
            msg = uploadException.getMessage();
            LOGGER.error(uploadException, msg);
        } else {
            msg = downloadException.getMessage();
            LOGGER.error(downloadException, msg);
        }

        return Result.ofFail(msg);
    }

    /**
     * 授权异常处理
     *
     * @param e {@link AuthorizationException}
     * @return 响应结果
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleAuthorizationException(AuthorizationException e) {
        logDebug(e, null);
        return Result.ofFail(ErrorCode.FORBIDDEN);
    }

    /**
     * 记录日志并响应
     *
     * @param e              {@link Exception}
     * @param isSaveErrorLog 是否保存错误日志，true：保存 false：不保存
     * @return 响应结果
     */
    private Result logAndResponse(Exception e, boolean isSaveErrorLog) {
        boolean isSpecialBusinessException = isSpecialBusinessException(e);
        if (isSaveErrorLog && !isSpecialBusinessException) {
            // 异步保存错误日志
            logErrorService.saveAsync(e, HttpContextUtil.getHttpServletRequest());
        }

        String msg = e.getMessage();
        if (isSpecialBusinessException) {
            // 不记录异常堆栈信息
            LOGGER.info(msg);
            logDebug(e, null);
        } else {
            LOGGER.error(e, msg);
        }
        return Result.ofFail(msg);
    }

    /**
     * 是否是特殊指定的BusinessException
     *
     * @param e {@link Exception}
     * @return 是否是特殊指定的BusinessException，true：是 false：不是
     * @see RateLimiterAspect#before(JoinPoint)
     * @see ContentTypeInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     * @see SqlInjectionInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    private boolean isSpecialBusinessException(Exception e) {
        if (e instanceof BusinessException) {
            String msg = e.getMessage();
            Locale locale = LocaleUtil.getLocalePriority();
            // 限流成功时抛出的异常
            if (msg.equals(LocaleUtil.getI18nMessage(locale, ErrorCode.SERVER_BUSY))) {
                return true;
            }
            // 请求体参数不支持时抛出的异常
            if (msg.equals(LocaleUtil.getI18nMessage(locale, ErrorCode.UNSUPPORTED_CONTENT_TYPE))) {
                return true;
            }
            // Sql注入过滤时抛出的异常
            if (msg.equals(LocaleUtil.getI18nMessage(locale, ErrorCode.INVALID_SYMBOL))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 记录debug日志
     *
     * @param e   {@link Exception}
     * @param msg 消息
     */
    private void logDebug(Exception e, String msg) {
        if (LOGGER.isDebugEnabled()) {
            if (StrUtil.isNotBlank(msg)) {
                LOGGER.debug(e, msg);
            } else {
                LOGGER.debug(e);
            }
        }
    }

}
