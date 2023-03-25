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

package me.xiajhuan.summer.admin.common.base.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.admin.common.base.annotation.LogOperation;
import me.xiajhuan.summer.core.enums.NonLoggedUserEnum;
import me.xiajhuan.summer.core.enums.OperationGroupEnum;
import me.xiajhuan.summer.core.enums.OperationStatusEnum;
import me.xiajhuan.summer.admin.common.log.entity.LogOperationEntity;
import me.xiajhuan.summer.admin.common.log.service.LogOperationService;
import me.xiajhuan.summer.core.utils.HttpContextUtil;
import me.xiajhuan.summer.core.utils.IpUtil;
import me.xiajhuan.summer.core.utils.AopUtil;
import me.xiajhuan.summer.core.utils.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static me.xiajhuan.summer.core.constant.OperationConst.*;

/**
 * 操作日志切面
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
@Aspect
@Component
public class LogOperationAspect {

    @Resource
    private LogOperationService logOperationService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(me.xiajhuan.summer.admin.common.base.annotation.LogOperation)")
    public void pointCut() {
    }

    /**
     * 环绕通知
     *
     * @param point {@link ProceedingJoinPoint}
     * @return 切入点方法执行结果
     * @throws Throwable 异常/错误
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        TimeInterval timer = DateUtil.timer();
        try {
            // 执行切入点方法
            Object result = point.proceed();

            // 保存成功日志
            saveLog(point, timer.interval(), OperationStatusEnum.SUCCESS);

            return result;
        } catch (Throwable e) {
            // 保存失败日志
            saveLog(point, timer.interval(), OperationStatusEnum.FAIL);

            throw e;
        }
    }

    /**
     * 保存日志
     *
     * @param point  {@link ProceedingJoinPoint}
     * @param cost   耗时（ms）
     * @param status 操作状态
     */
    private void saveLog(ProceedingJoinPoint point, long cost, OperationStatusEnum status) {
        // 获取切入点方法所属Controller上的RequestMapping注解
        Class controllerClass = point.getTarget().getClass();
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(controllerClass, RequestMapping.class);

        // 获取切入点方法上的LogOperation注解
        Method method = AopUtil.getMethod(point);
        LogOperation logOperation = AnnotationUtils.findAnnotation(method, LogOperation.class);

        // 请求
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        // 构建操作日志
        LogOperationEntity log = LogOperationEntity.builder()
                .operation(StrUtil.format("【{}】{}", logOperation.name(), requestMapping.path()))
                .operationGroup(getOperationGroup(logOperation.name()))
                .operateBy(SecurityUtil.getCurrentUsername(NonLoggedUserEnum.THIRD_PART.getValue()))
                .status(status.getValue())
                .requestTime((int) cost)
                .ip(IpUtil.getRequestIp(request))
                .userAgent(HttpContextUtil.getUserAgent(request))
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod()).build();

        if (logOperation.saveRequestParam()) {
            // 请求参数
            log.setRequestParams(HttpContextUtil.getParam(point, request));
        }

        // 异步保存日志
        logOperationService.saveAsync(log);
    }

    /**
     * 获取操作分组
     *
     * @param name 操作名称
     * @return 操作分组
     */
    private int getOperationGroup(String name) {
        switch (name) {
            case PAGE:
            case LIST:
            case GET_BY_ID:
            case ADD:
            case UPDATE:
            case DELETE:
                return OperationGroupEnum.COMMON_CRUD.getValue();
            case EXCEL_TEMPLATE:
            case EXCEL_IMPORT:
            case EXCEL_EXPORT:
                return OperationGroupEnum.EXCEL_OPERATION.getValue();
            default:
                return OperationGroupEnum.OTHER_OPERATION.getValue();
        }
    }

}