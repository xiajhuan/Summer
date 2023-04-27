/*
 * Copyright (c) 2023-2033 xiajhuan(xiaJhuan@163.com)
 * Summer is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package me.xiajhuan.summer.system.handler.exception;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.core.constant.SettingConst;
import me.xiajhuan.summer.core.data.Result;
import me.xiajhuan.summer.core.exception.custom.*;
import me.xiajhuan.summer.core.exception.code.ErrorCode;
import me.xiajhuan.summer.core.utils.AssertUtil;
import me.xiajhuan.summer.core.utils.LocaleUtil;
import me.xiajhuan.summer.core.utils.ServletUtil;
import me.xiajhuan.summer.core.utils.ValidationUtil;
import me.xiajhuan.summer.system.log.service.LogErrorService;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 全局异常处理
 *
 * @author xiajhuan
 * @date 2022/11/26
 * @see Exception
 * @see SystemException
 * @see BusinessException
 * @see BindException
 * @see ValidationException
 * @see FileUploadException
 * @see FileDownloadException
 * @see AuthorizationException
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log LOGGER = LogFactory.get();

    @Resource(name = SettingConst.SYSTEM)
    private Setting setting;

    @Resource
    private LogErrorService logErrorService;

    /**
     * 是否保存 {@link SystemException} 日志
     */
    private boolean enableSystem;

    /**
     * 是否保存 {@link BusinessException} 日志
     */
    private boolean enableBusiness;

    /**
     * 忽略的消息代码数组<br>
     * note：仅对 {@link SystemException} 和 {@link BusinessException} 生效
     *
     * @see ErrorCode
     */
    private int[] ignoreCodeArray;

    /**
     * 初始化 {@link enableSystem} {@link enableBusiness} {@link ignoreCodeArray}
     */
    @PostConstruct
    private void init() {
        enableSystem = setting.getBool("enable-system", "Log", true);
        enableBusiness = setting.getBool("enable-business", "Log", true);
        if (enableSystem || enableBusiness) {
            String ignoreCodes = setting.getByGroup("ignore-codes", "Log");
            if (StrUtil.isNotBlank(ignoreCodes)) {
                ignoreCodeArray = Arrays.stream(ignoreCodes.split(StrPool.COMMA))
                        .mapToInt(Integer::parseInt).toArray();
            }
        }
    }

    /**
     * 异常处理（所有受检/非受检异常）
     *
     * @param e {@link Exception}
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e) {
        return logAndResponse(e, true);
    }

    /**
     * 系统/业务异常处理
     *
     * @param e {@link RuntimeException}
     * @return 响应结果
     */
    @ExceptionHandler({SystemException.class, BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleSystemOrBusinessException(RuntimeException e) {
        if (e instanceof SystemException) {
            return logAndResponse(e, enableSystem);
        } else {
            return logAndResponse(e, enableBusiness);
        }
    }

    /**
     * 校验异常处理<br>
     * note：参数校验（Dto类型）（注解 {@link Validated} 校验）
     *
     * @param e {@link BindException}
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBindException(BindException e) {
        StringBuilder message = StrUtil.builder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error -> message.append(error.getField())
                .append("【").append(error.getDefaultMessage()).append("】")
                .append(StrPool.COMMA));

        String msg = message.substring(0, message.length() - 1);
        logInfoOrDebugStacktrace(e, msg);
        return Result.ofFail(msg);
    }

    /**
     * 校验异常处理，note：
     * <pre>
     *   1.参数校验（Dto类型）（{@link ValidationUtil} api校验）
     *   2.{@link AssertUtil} 断言失败
     *   3.直接抛出 {@link ValidationException}
     * </pre>
     *
     * @param e 校验异常
     * @return 响应结果
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleValidationException(ValidationException e) {
        String msg = e.getMessage();
        logInfoOrDebugStacktrace(e, msg);
        return Result.ofFail(msg);
    }

    /**
     * 文件上传/下载异常处理
     *
     * @param e {@link RuntimeException}
     * @return 响应结果
     */
    @ExceptionHandler({FileUploadException.class, FileDownloadException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleFileUploadOrDownloadException(RuntimeException e) {
        String msg = e.getMessage();
        LOGGER.error(e, msg);
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
    public Result<?> handleAuthorizationException(AuthorizationException e) {
        logInfoOrDebugStacktrace(e, null);
        return Result.ofFail(ErrorCode.FORBIDDEN);
    }

    /**
     * 记录日志并响应
     *
     * @param e              {@link Exception}
     * @param isSaveErrorLog 是否保存错误日志，true：保存 false：不保存
     * @return 响应结果
     */
    private Result<?> logAndResponse(Exception e, boolean isSaveErrorLog) {
        boolean isIgnored = isIgnoredCode(e);
        if (isSaveErrorLog && !isIgnored) {
            // 异步保存错误日志
            logErrorService.saveAsync(e, ServletUtil.getHttpRequest());
        }

        String msg = e.getMessage();
        if (isIgnored) {
            logInfoOrDebugStacktrace(e, msg);
        } else {
            LOGGER.error(e, msg);
        }
        return Result.ofFail(msg);
    }

    /**
     * 是否是忽略的消息代码
     *
     * @param e {@link Exception}
     * @return 是否是忽略的消息代码，true：是 false：不是
     */
    private boolean isIgnoredCode(Exception e) {
        if ((e instanceof SystemException || e instanceof BusinessException) && ArrayUtil.isNotEmpty(ignoreCodeArray)) {
            String msg = e.getMessage();
            Locale locale = LocaleUtil.getLocalePriority();
            return Arrays.stream(ignoreCodeArray)
                    .anyMatch(code -> msg.equals(LocaleUtil.getI18nMessage(locale, code)));
        }
        return false;
    }

    /**
     * 记录info日志（不含异常堆栈信息）或debug日志（包含异常堆栈信息）
     *
     * @param e   {@link Exception}
     * @param msg 消息
     */
    private void logInfoOrDebugStacktrace(Exception e, String msg) {
        if (msg == null) {
            msg = e.getMessage();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(e, msg);
        } else {
            LOGGER.info(msg);
        }
    }

}
