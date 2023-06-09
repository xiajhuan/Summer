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
 * Servlet工具
 *
 * @author xiajhuan
 * @date 2023/3/30
 * @see cn.hutool.extra.servlet.ServletUtil
 */
public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {

    /**
     * 不允许实例化
     */
    private ServletUtil() {
    }

    /**
     * 获取HTTP请求
     *
     * @return {@link HttpServletRequest}或{@code null}
     */
    public static HttpServletRequest getHttpRequest() {
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
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取请求来源
     *
     * @param request {@link HttpServletRequest}
     * @return 请求来源
     */
    public static String getOrigin(HttpServletRequest request) {
        return request == null ? StrUtil.EMPTY : getHeaderIgnoreCase(request, HttpHeaders.ORIGIN);
    }

    /**
     * 获取请求代理
     *
     * @param request {@link HttpServletRequest}
     * @return 请求代理
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request == null ? StrUtil.EMPTY : getHeaderIgnoreCase(request, HttpHeaders.USER_AGENT);
    }

    /**
     * 获取接口唯一标识<br>
     * 格式：URI[Method]，如：/summer/security/user/page[GET]
     *
     * @param request {@link HttpServletRequest}
     * @return 接口唯一标识
     */
    public static String getInterfaceSignature(HttpServletRequest request) {
        return request == null ? StrUtil.EMPTY
                : StrUtil.format("{}[{}]", request.getRequestURI(), request.getMethod());
    }

    /**
     * 获取请求参数（切入点方法），参数格式如下：
     * <ul>
     *   <li>Query：Query【pageNum=1&pageSize=10】</li>
     *   <li>Form-Data：Form-Data【pageNum=1&pageSize=10】或Form-Data【status=1【文件上传】】</li>
     *   <li>Json：Json【{...}】或Json【[...]】</li>
     *   <li>Xml：Xml【...】</li>
     *   <li>Javascript：Javascript【...】</li>
     *   <li>Text：Text【...】</li>
     *   <li>Html：Html【...】</li>
     * </ul>
     *
     * @param point   {@link JoinPoint}
     * @param request {@link HttpServletRequest}
     * @return 请求参数
     * @see ContentTypeConst
     */
    public static String getParamPoint(JoinPoint point, HttpServletRequest request) {
        // 请求参数，note：如果没有参数会返回空数组
        Object[] args = point.getArgs();

        if (request != null && args.length > 0) {
            // Path中的Query参数，note：非GET请求也可能携带Query参数
            String queryString = request.getQueryString();
            // Query参数格式示例：Query【pageNum=1&pageSize=10】
            String queryParam = StrUtil.EMPTY;
            if (StrUtil.isNotBlank(queryString)) {
                queryParam = StrUtil.format("Query【{}】 ",
                        queryString.endsWith("&") ? queryString.substring(0, queryString.length() - 1) : queryString);
            }

            String contentType = request.getContentType();
            if (contentType != null) {
                // 有请求体参数
                // Form参数格式示例：Form-Data【pageNum=1&pageSize=10】或Form-Data【status=1【文件上传】】
                if (StrUtil.startWithAnyIgnoreCase(contentType, ContentTypeConst.FORM_DATA)) {
                    return getFormParam(args);
                }

                // Json参数格式示例：Json【{...}】或Json【[...]】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.JSON)) {
                    final String jsonParam;
                    if (args[0] instanceof String) {
                        jsonParam = String.valueOf(args[0]);
                    } else {
                        jsonParam = JSONUtil.toJsonStr(args[0]);
                    }
                    return concatQueryPrefix("Json", jsonParam, queryParam);
                }

                // Xml参数格式示例：Xml【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.XML)) {
                    return concatQueryPrefix("Xml", String.valueOf(args[0]), queryParam);
                }

                // Javascript参数格式示例：Javascript【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.JAVASCRIPT)) {
                    return concatQueryPrefix("Javascript", String.valueOf(args[0]), queryParam);
                }

                // 普通文本参数格式示例：Text【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.TEXT)) {
                    return concatQueryPrefix("Text", String.valueOf(args[0]), queryParam);
                }

                // Html文本参数格式示例：Html【...】
                if (StrUtil.startWithIgnoreCase(contentType, ContentTypeConst.HTML)) {
                    return concatQueryPrefix("Html", String.valueOf(args[0]), queryParam);
                }
            }

            // 无请求体参数
            return queryParam;
        }

        return StrUtil.EMPTY;
    }

    /**
     * 响应，指定状态码
     *
     * @param response    {@link HttpServletResponse}
     * @param contentType 响应体类型
     * @param status      状态码
     * @param data        数据
     */
    public static void response(HttpServletResponse response, String contentType,
                                int status, Object data) {
        if (response != null) {
            response.setStatus(status);
            write(response, JSONUtil.toJsonStr(data), contentType);
        }
    }

    /**
     * 获取表单参数，若包含文件上传则末尾标记【文件上传】<br>
     * 参数格式示例：Form-Data【pageNum=1&pageSize=10】或Form-Data【status=1【文件上传】】
     *
     * @param args 参数数组
     * @return 表单参数
     */
    private static String getFormParam(Object[] args) {
        // 排除值为空的参数（包括空数组）
        args = Arrays.stream(args).filter(ObjectUtil::isNotEmpty).toArray();

        if (args.length > 0) {
            // 标记是否为文件上传，true：否 false：是
            boolean notMultipart = true;

            StringBuilder formParam = StrUtil.builder();
            for (Object arg : args) {
                if (notMultipart && (arg instanceof MultipartFile || arg instanceof MultipartFile[])) {
                    notMultipart = false;
                } else {
                    if (arg instanceof Object[]) {
                        // 数组参数格式示例：[1,2,3]
                        formParam.append(ArrayUtil.toString(arg));
                    } else {
                        formParam.append(arg.toString());
                    }
                    formParam.append(StrPool.COMMA);
                }
            }
            if (formParam.length() > 0) {
                formParam.deleteCharAt(formParam.length() - 1);
            }
            if (!notMultipart) {
                formParam.append("【文件上传】");
            }

            return StrUtil.format("Form-Data【{}】", formParam);
        }
        return StrUtil.EMPTY;
    }

    /**
     * 拼接Query参数作为前缀<br>
     * 例如：Query【...】 Json【{...}】
     *
     * @param bodyType   请求体类型
     * @param bodyParam  请求体参数
     * @param queryParam Query参数
     * @return 拼接后的参数
     */
    private static String concatQueryPrefix(String bodyType, String bodyParam, String queryParam) {
        return StrUtil.isNotBlank(queryParam)
                ? StrUtil.builder(queryParam, StrUtil.format("{}【{}】", bodyType, bodyParam)).toString()
                : StrUtil.format("{}【{}】", bodyType, bodyParam);
    }

}
