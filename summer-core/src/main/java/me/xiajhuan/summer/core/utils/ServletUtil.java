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

package me.xiajhuan.summer.core.utils;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import me.xiajhuan.summer.core.constant.ContentTypeConst;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Servlet相关工具
 *
 * @author xiajhuan
 * @date 2023/3/30
 * @see cn.hutool.extra.servlet.ServletUtil
 */
public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {

    /**
     * 请求体参数格式
     */
    private static final String BODY_PARAM_FORMAT = "{}【{}】";

    /**
     * 获取HTTP请求
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
        return getHeaderIgnoreCase(request, HttpHeaders.ORIGIN);
    }

    /**
     * 获取请求代理
     *
     * @param request {@link HttpServletRequest}
     * @return 请求代理
     */
    public static String getUserAgent(HttpServletRequest request) {
        return getHeaderIgnoreCase(request, HttpHeaders.USER_AGENT);
    }

    /**
     * 获取接口唯一标识<br>
     * 格式：URI[Method]，如：/summer-single/security/user/page[GET]
     *
     * @param request {@link HttpServletRequest}
     * @return 接口唯一标识
     */
    public static String getInterfaceSignature(HttpServletRequest request) {
        return StrUtil.format("{}[{}]", request.getRequestURI(), request.getMethod());
    }

    /**
     * 获取请求参数（切入点方法）
     *
     * @param point   {@link JoinPoint}
     * @param request {@link HttpServletRequest}
     * @return 请求参数
     */
    public static String getParamPoint(JoinPoint point, HttpServletRequest request) {
        // 请求参数，note：这里如果没有参数会返回空数组
        Object[] args = point.getArgs();

        if (args.length > 0) {
            // Path中的Query参数，note：非GET请求也可能携带Query参数
            String queryString = request.getQueryString();
            // Query参数格式示例：Query【pageNum=1&pageSize=10】
            String queryParam = StrUtil.EMPTY;
            if (StrUtil.isNotBlank(queryString)) {
                queryParam = StrUtil.format("Query【{}】 ",
                        queryString.endsWith("&") ? queryString.substring(0, queryString.length() - 1) : queryString);
            }

            String contentType = request.getContentType();
            if (StrUtil.isNotBlank(contentType)) {
                // 有请求体参数
                // Form参数格式示例：Form-Data【pageNum=1&pageSize=10】或Form-Data【status=1【文件上传】】
                if (StrUtil.startWithAnyIgnoreCase(contentType, ContentTypeConst.FORM_DATA)) {
                    return getFormParam(args);
                }

                // Json参数格式示例：Json【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.JSON)) {
                    final String jsonParam;
                    if (args[0] instanceof String) {
                        jsonParam = String.valueOf(args[0]);
                    } else {
                        jsonParam = JSONUtil.toJsonStr(args[0]);
                    }
                    return concatQuery("Json", jsonParam, queryParam);
                }

                // Xml参数格式示例：Xml【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.XML)) {
                    return concatQuery("Xml", String.valueOf(args[0]), queryParam);
                }

                // Javascript参数格式示例：Javascript【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.JAVASCRIPT)) {
                    return concatQuery("Javascript", String.valueOf(args[0]), queryParam);
                }

                // 普通文本参数格式示例：Text【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.TEXT)) {
                    return concatQuery("Text", String.valueOf(args[0]), queryParam);
                }

                // Html文本参数格式示例：Html【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.HTML)) {
                    return concatQuery("Html", String.valueOf(args[0]), queryParam);
                }
            }

            // 无请求体参数
            if (StrUtil.isNotBlank(queryParam)) {
                return queryParam;
            }
        }

        return StrUtil.EMPTY;
    }

    /**
     * 响应
     *
     * @param response    {@link HttpServletResponse}
     * @param contentType 响应体类型
     * @param status      状态码
     * @param data        数据
     */
    public static void response(HttpServletResponse response, String contentType,
                                int status, Object data) {
        response.setStatus(status);
        write(response, JSONUtil.toJsonStr(data), contentType);
    }

    /**
     * 获取表单参数，若包含文件上传则末尾标记【文件上传】
     *
     * @param args 参数数组
     * @return 表单参数或 {@code null}
     */
    private static String getFormParam(Object[] args) {
        // 排除参数值为空的参数（包括空数组）
        args = Arrays.stream(args).filter(ObjectUtil::isNotEmpty).toArray();

        if (args.length > 0) {
            // 标记是否为文件上传，true：不是 false：是
            boolean notMultipart = true;

            StringBuilder formParam = StrUtil.builder();
            for (int i = 0; i < args.length; i++) {
                if (notMultipart && (args[i] instanceof MultipartFile || args[i] instanceof MultipartFile[])) {
                    notMultipart = false;
                } else {
                    if (args[i] instanceof Object[]) {
                        // 数组参数格式示例：[1,2,3]
                        formParam.append(ArrayUtil.toString(args[i]));
                    } else {
                        formParam.append(args[i].toString());
                    }
                    if (i != args.length - 1) {
                        formParam.append(StrPool.COMMA);
                    }
                }
            }

            if (!notMultipart) {
                formParam.append("【文件上传】");
            }

            return StrUtil.format("Form-Data【{}】", formParam);
        }
        return null;
    }

    /**
     * 拼接Query参数<br>
     * 例如：Query【...】 Json【...】
     *
     * @param prefix     前缀
     * @param bodyParam  请求体参数
     * @param queryParam Query参数
     * @return 拼接后的参数
     */
    private static String concatQuery(String prefix, String bodyParam, String queryParam) {
        return StrUtil.isNotBlank(queryParam)
                ? StrUtil.builder(queryParam, StrUtil.format(BODY_PARAM_FORMAT, prefix, bodyParam)).toString()
                : StrUtil.format(BODY_PARAM_FORMAT, prefix, bodyParam);
    }

}
