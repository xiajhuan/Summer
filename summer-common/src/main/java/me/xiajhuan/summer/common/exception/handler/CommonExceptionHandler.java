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

package me.xiajhuan.summer.common.exception.handler;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import me.xiajhuan.summer.common.constant.SettingBeanConst;
import me.xiajhuan.summer.common.data.Result;
import me.xiajhuan.summer.common.exception.BusinessException;
import me.xiajhuan.summer.common.exception.ErrorCode;
import me.xiajhuan.summer.common.exception.FileDownloadException;
import me.xiajhuan.summer.common.log.service.LogErrorService;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import me.xiajhuan.summer.common.utils.MessageUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.util.List;

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
    private Setting setting;

    @Resource
    private LogErrorService logErrorService;

    /**
     * 记录日志并响应
     *
     * @param e              {@link Exception}
     * @param isSaveErrorLog 是否保存错误日志，true：保存 false：不保存
     * @return 响应结果
     */
    private Result logAndResponse(Exception e, boolean isSaveErrorLog) {
        if (isSaveErrorLog) {
            // 异步保存错误日志
            logErrorService.saveLogAsync(e, HttpContextUtil.getHttpServletRequest());
        }

        String msg = e.getMessage();
        LOGGER.error(e, msg);
        return Result.ofFail(msg);
    }

    /**
     * 异常处理（所有受检/非受检异常）
     *
     * @param e {@link Exception}
     * @return 响应结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        boolean isSaveErrorLog = true;

        // 处理限流切面的异常
        Exception cause = (Exception) e.getCause();
        if (cause != null && cause instanceof BusinessException && "服务器繁忙，请稍后再试~".equals(cause.getMessage())) {
            e = cause;
            if (!setting.getBool("error.enable-business", "Log")) {
                isSaveErrorLog = false;
            }
        }

        return logAndResponse(e, isSaveErrorLog);
    }

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return 响应结果
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleBusinessException(BusinessException e) {
        return logAndResponse(e, setting.getBool("error.enable-business", "Log"));
    }

    /**
     * 参数校验（Dto类型传参）异常处理
     *
     * @param e {@link BindException}
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException e) {
        StringBuilder message = StrUtil.builder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append("【").append(error.getDefaultMessage()).append("】").append(StrPool.COMMA);
        }
        return Result.ofFail(message.substring(0, message.length() - 1));
    }

    /**
     * 文件下载异常处理
     *
     * @param e 文件下载异常
     */
    @ExceptionHandler(value = FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleFileDownloadException(FileDownloadException e) {
        LOGGER.error(e, "文件下载异常【{}】", e.getMessage());
    }

    /**
     * 授权异常处理
     *
     * @return 响应结果
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleAccessDeniedException() {
        return Result.ofFail(MessageUtil.getI18nMessage(ErrorCode.FORBIDDEN));
    }

}
