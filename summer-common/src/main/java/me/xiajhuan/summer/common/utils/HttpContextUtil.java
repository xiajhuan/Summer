package me.xiajhuan.summer.common.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * Http上下文工具
 *
 * @author xiajhuan
 * @date 2022/11/28
 */
public class HttpContextUtil {

    /**
     * 获取请求
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取请求参数-Map
     *
     * @param request {@link HttpServletRequest}
     * @return 请求参数-Map
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> params = MapUtil.newHashMap();

        Enumeration<String> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameter = parameters.nextElement();
            String value = request.getParameter(parameter);
            if (StrUtil.isNotBlank(value)) {
                params.put(parameter, value);
            }
        }

        return params;
    }

    /**
     * 获取请求域名
     *
     * @param request {@link HttpServletRequest}
     * @return 请求域名
     */
    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * 获取请求来源
     *
     * @param request {@link HttpServletRequest}
     * @return 请求来源
     */
    public static String getOrigin(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.ORIGIN);
    }

    /**
     * 获取请求客户端信息
     *
     * @param request {@link HttpServletRequest}
     * @return 请求客户端信息
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    /**
     * 获取请求语言
     *
     * @param request {@link HttpServletRequest}
     * @return 请求语言
     */
    public static String getLanguage(HttpServletRequest request) {
        // 默认语言
        String defaultLanguage = "zh-CN";

        if (request == null) {
            return defaultLanguage;
        }

        // 请求语言
        defaultLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);

        return defaultLanguage;
    }

    /**
     * 获取请求接口唯一标识<br>
     * 格式：URI[Method]，如：/server/common/api/open/demo[POST]
     *
     * @param request {@link HttpServletRequest}
     * @return 请求接口唯一标识
     */
    public static String getInterfaceSignature(HttpServletRequest request) {
        return StrUtil.format("{}[{}]", request.getRequestURI(), request.getMethod());
    }

    /**
     * 获取请求参数
     *
     * @param joinPoint {@link JoinPoint}
     * @param request   {@link HttpServletRequest}
     * @return 请求参数
     */
    public static String getParamValues(JoinPoint joinPoint, HttpServletRequest request) {
        // 请求参数，note：这里如果没有参数会返回空数组
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            // 存在方法参数，则必然存在请求参数
            String queryString = request.getQueryString();
            if (StrUtil.isNotBlank(queryString)) {
                // Query参数，去除末尾的“&”
                return queryString.endsWith("&") ? queryString.substring(0, queryString.length() - 1) : queryString;
            } else if (args.length == 1 && args[0] instanceof String && JSONUtil.isJson(String.valueOf(args[0]))) {
                // 参数为Json字符串
                return String.valueOf(args[0]);
            } else {
                // 表单参数
                return getFormDataParams(args);
            }
        }

        return StrUtil.EMPTY;
    }

    /**
     * 获取表单参数，若包含文件上传则末尾标记【文件上传】
     *
     * @param args 方法参数数组
     * @return 表单参数
     */
    private static String getFormDataParams(Object[] args) {
        // 排除参数值为空的参数（包括空数组）
        args = Arrays.stream(args).filter(arg -> ObjectUtil.isNotEmpty(arg)).toArray();

        // 标记当前参数是否为文件上传
        boolean notMultipart = true;

        StringBuilder paramStr = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (notMultipart && (args[i] instanceof MultipartFile || args[i] instanceof MultipartFile[])) {
                notMultipart = false;
            } else {
                if (args[i] instanceof Object[]) {
                    // 数组参数做输出处理，格式为：[1,2,3]
                    paramStr.append(ArrayUtil.toString(args[i]));
                } else {
                    paramStr.append(args[i].toString());
                }
                if (i != args.length - 1) {
                    paramStr.append(StrUtil.COMMA);
                }
            }

        }

        if (!notMultipart) {
            paramStr.append("【文件上传】");
        }

        return paramStr.toString();
    }

    /**
     * 响应
     *
     * @param response    {@link HttpServletResponse}
     * @param contentType 媒体格式
     * @param status      状态码
     * @param value       数据
     * @throws IOException I/O异常
     */
    public static void makeResponse(HttpServletResponse response, String contentType,
                                    int status, Object value) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JSONUtil.toJsonStr(value).getBytes());
    }

}