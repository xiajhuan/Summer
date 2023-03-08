package me.xiajhuan.summer.common.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import me.xiajhuan.summer.common.annotation.LogOperation;
import me.xiajhuan.summer.common.constant.StrTemplateConst;
import me.xiajhuan.summer.common.enums.DefaultUserEnum;
import me.xiajhuan.summer.common.enums.OperationGroupEnum;
import me.xiajhuan.summer.common.enums.OperationStatusEnum;
import me.xiajhuan.summer.common.log.entity.LogOperationEntity;
import me.xiajhuan.summer.common.log.service.LogOperationService;
import me.xiajhuan.summer.common.utils.HttpContextUtil;
import me.xiajhuan.summer.common.utils.IpUtil;
import me.xiajhuan.summer.common.utils.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import static me.xiajhuan.summer.common.constant.OperationConst.*;

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
    @Pointcut("@annotation(me.xiajhuan.summer.common.annotation.LogOperation)")
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
        // 开始计时
        TimeInterval timer = DateUtil.timer();
        try {
            // 执行方法
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
     * 记录日志
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param cost      操作耗时（ms）
     * @param status    操作状态
     * @throws Exception 异常
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long cost, OperationStatusEnum status) throws Exception {
        // 获取切入点Controller上的RequestMapping注解
        Class currentController = joinPoint.getTarget().getClass();
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(currentController, RequestMapping.class);

        // 获取切入点方法上的LogOperation注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = currentController.getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        LogOperation logOperation = AnnotationUtils.findAnnotation(method, LogOperation.class);

        // 请求
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        // 构建日志Entity
        LogOperationEntity log = LogOperationEntity.builder()
                .operation(StrUtil.format(StrTemplateConst.OPERATION_NAME, logOperation.name(), requestMapping.path()))
                .operationGroup(getOperationGroupValue(logOperation.name()))
                .operateBy(SecurityUtil.getCurrentUsername(DefaultUserEnum.THIRD_PART.getValue()))
                .status(status.getValue())
                .requestTime((int) cost)
                .ip(IpUtil.getRequestOriginIp(request))
                .userAgent(HttpContextUtil.getUserAgent(request))
                .requestUri(request.getRequestURI())
                .requestMethod(request.getMethod()).build();

        if (logOperation.isSaveRequestData()) {
            // 请求参数值
            log.setRequestParams(HttpContextUtil.getParamValues(joinPoint, request));
        }

        // 异步保存日志
        logOperationService.saveLogAsync(log);
    }

    /**
     * 获取操作分组枚举Value
     *
     * @param name 操作名称
     * @return 操作分组枚举Value
     */
    private int getOperationGroupValue(String name) {
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