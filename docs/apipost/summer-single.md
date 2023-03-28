## 全局公共参数
#### 全局Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
AccessToken | a3d8c73b75ac032da1e6d193bd94f44a | accessToken
Accept-Language | zh_CN | 地区语言
#### 全局Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 全局Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 全局认证方式
```text
noauth
```
#### 全局预执行脚本
```javascript
暂无预执行脚本
```
#### 全局后执行脚本
```javascript
暂无后执行脚本
```
## /business
```text
业务模块
```
#### 公共Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共认证信息
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common
```text
通用模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/log
```text
日志模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/log/operation
```text
操作日志
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/log/operation/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/operation/page?pageNum=1&pageSize=10&field=createTime&order=desc&operationGroup=0&status=1&createTimeStart=2023-03-21 11:22:22&createTimeEnd=2023-03-28 11:22:22

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operationGroup | 0 | Integer | 否 | 操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation
status | 1 | Integer | 否 | 状态  0：失败 1：成功
createTimeStart | 2023-03-21 11:22:22 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-03-28 11:22:22 | Date | 否 | 创建时间区间（结束）
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"total": 81,
		"rows": [
			{
				"id": 1639546798296739842,
				"operation": "【删除】[security/dept]",
				"operationGroup": 0,
				"requestUri": "/summer-single/security/dept/delete",
				"requestMethod": "DELETE",
				"requestParams": "Form-Data【1639546265158758402】",
				"requestTime": 71,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:36:33"
			},
			{
				"id": 1639546523502718978,
				"operation": "【修改】[security/dept]",
				"operationGroup": 0,
				"requestUri": "/summer-single/security/dept/update",
				"requestMethod": "PUT",
				"requestParams": "Form-Data【SecurityDeptDto(id=1639546265158758402, parentId=1636549260430012417, name=模具研发部22, weight=0, createTime=null, children=[], parentName=null)】",
				"requestTime": 65,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:35:27"
			},
			{
				"id": 1639546265225867266,
				"operation": "【新增】[security/dept]",
				"operationGroup": 0,
				"requestUri": "/summer-single/security/dept/add",
				"requestMethod": "POST",
				"requestParams": "Form-Data【SecurityDeptDto(id=null, parentId=1636549260430012417, name=模具造型部11, weight=0, createTime=null, children=[], parentName=null)】",
				"requestTime": 18,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:34:26"
			},
			{
				"id": 1639545854020497409,
				"operation": "【根据ID获取】[security/dept]",
				"operationGroup": 0,
				"requestUri": "/summer-single/security/dept/getById",
				"requestMethod": "GET",
				"requestParams": "Query【id=1636549534884294658】 ",
				"requestTime": 12,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:32:48"
			},
			{
				"id": 1639545469746753538,
				"operation": "【列表】[security/dept]",
				"operationGroup": 0,
				"requestUri": "/summer-single/security/dept/list",
				"requestMethod": "GET",
				"requestParams": "",
				"requestTime": 45,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:31:16"
			},
			{
				"id": 1639542194184073217,
				"operation": "【删除】[locale/internationalName]",
				"operationGroup": 0,
				"requestUri": "/summer-single/locale/internationalName/delete",
				"requestMethod": "DELETE",
				"requestParams": "Form-Data【[1639541999484481537, 1639542125145829378]】",
				"requestTime": 19,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:18:15"
			},
			{
				"id": 1639542125145829379,
				"operation": "【新增】[locale/internationalName]",
				"operationGroup": 0,
				"requestUri": "/summer-single/locale/internationalName/add",
				"requestMethod": "POST",
				"requestParams": "Form-Data【LocaleInternationalNameDto(tableName=security_menu, fieldName=name, fieldValue=权限管理, locale=zh_CN, createTime=null)】",
				"requestTime": 8,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:17:59"
			},
			{
				"id": 1639541999551590402,
				"operation": "【新增】[locale/internationalName]",
				"operationGroup": 0,
				"requestUri": "/summer-single/locale/internationalName/add",
				"requestMethod": "POST",
				"requestParams": "Form-Data【LocaleInternationalNameDto(tableName=security_menu, fieldName=name, fieldValue=权限管理, locale=zh_CN, createTime=null)】",
				"requestTime": 11,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:17:29"
			},
			{
				"id": 1639541708877934593,
				"operation": "【修改】[locale/internationalName]",
				"operationGroup": 0,
				"requestUri": "/summer-single/locale/internationalName/update",
				"requestMethod": "PUT",
				"requestParams": "Form-Data【LocaleInternationalNameDto(tableName=security_menu, fieldName=name, fieldValue=Role Management, locale=en_US, createTime=null)】",
				"requestTime": 44,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:16:19"
			},
			{
				"id": 1639541338478948354,
				"operation": "【新增】[locale/internationalName]",
				"operationGroup": 0,
				"requestUri": "/summer-single/locale/internationalName/add",
				"requestMethod": "POST",
				"requestParams": "Form-Data【LocaleInternationalNameDto(tableName=security_menu, fieldName=name, fieldValue=权限管理, locale=zh_CN, createTime=null)】",
				"requestTime": 144,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"status": 1,
				"operateBy": "admin",
				"createTime": "2023-03-25 16:14:51"
			}
		]
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/log/operation/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/operation/excelExport?field=createTime&order=desc&operationGroup=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-03-28 12:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operationGroup | 0 | Integer | 否 | 操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation
status | 1 | Integer | 否 | 状态  0：失败 1：成功
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-03-28 12:00:00 | Date | 否 | 创建时间区间（结束）
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/log/error
```text
错误日志
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/log/error/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/error/page?pageNum=1&pageSize=10&field=createTime&order=desc&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-03-28 12:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-03-28 12:00:00 | Date | 否 | 创建时间区间（结束）
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"total": 80,
		"rows": [
			{
				"id": 1639257157270171650,
				"requestUri": "/summer-single/locale/internationalName/getById",
				"requestMethod": "GET",
				"requestParams": "{\"id\":\"1637653692089552898\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-24 21:25:37",
				"errorInfo": null
			},
			{
				"id": 1639177296119812097,
				"requestUri": "/summer-single/log/operation/page",
				"requestMethod": "GET",
				"requestParams": "{\"pageNum\":\"1\",\"pageSize\":\"10\",\"field\":\"createTime\",\"order\":\"desc\",\"operationGroup\":\"0\",\"status\":\"1\",\"params.createTimeStart\":\"2023-03-03 00:00:00\",\"params.createTimeEnd\":\"2023-04-04 00:00:00\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-24 16:08:17",
				"errorInfo": null
			},
			{
				"id": 1639177189697736706,
				"requestUri": "/summer-single/log/operation/page",
				"requestMethod": "GET",
				"requestParams": "{\"pageNum\":\"1\",\"pageSize\":\"10\",\"field\":\"createTime\",\"order\":\"desc\",\"operationGroup\":\"0\",\"status\":\"1\",\"params.createTimeStart\":\"2023-03-03 00:00:00\",\"params.createTimeEnd\":\"2023-04-04 00:00:00\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-24 16:07:51",
				"errorInfo": null
			},
			{
				"id": 1639177058382467073,
				"requestUri": "/summer-single/log/operation/page",
				"requestMethod": "GET",
				"requestParams": "{\"pageNum\":\"1\",\"pageSize\":\"10\",\"field\":\"createTime\",\"order\":\"desc\",\"operationGroup\":\"0\",\"status\":\"1\",\"params.createTimeStart\":\"2023-03-03 12:00:00\",\"params.createTimeEnd\":\"2023-03-04 12:00:00\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-24 16:07:20",
				"errorInfo": null
			},
			{
				"id": 1639176550020239362,
				"requestUri": "/summer-single/log/operation/excelExport",
				"requestMethod": "GET",
				"requestParams": "{\"operationGroup\":\"0\",\"status\":\"1\",\"params.createTimeStart\":\"2023-03-03 12:00:00\",\"params.createTimeEnd\":\"2023-03-13 12:00:00\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-24 16:05:19",
				"errorInfo": null
			},
			{
				"id": 1638807323308396546,
				"requestUri": "/summer-single/security/login",
				"requestMethod": "POST",
				"requestParams": "{\"username\":\"admin\",\"password\":\"admin\",\"captcha\":\"51chs\",\"uuid\":\"00376e8000bffa7a538274d69fff20f2\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:38:08",
				"errorInfo": null
			},
			{
				"id": 1638806355745378305,
				"requestUri": "/summer-single/security/login",
				"requestMethod": "POST",
				"requestParams": "{\"username\":\"admin\",\"password\":\"admin\",\"captcha\":\"xlj84\",\"uuid\":\"00376e8000bffa7a538274d69fff20f2\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:34:17",
				"errorInfo": null
			},
			{
				"id": 1638791623810428929,
				"requestUri": "/summer-single/security/login",
				"requestMethod": "POST",
				"requestParams": "{\"username\":\"admin\",\"password\":\"admin\",\"captcha\":\"s1wbs\",\"uuid\":\"00376e8000bffa7a538274d69fff20f2\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 14:35:45",
				"errorInfo": null
			},
			{
				"id": 1638791121626411010,
				"requestUri": "/summer-single/security/login",
				"requestMethod": "POST",
				"requestParams": "{\"username\":\"admin\",\"password\":\"admin\",\"captcha\":\"zoo3v\",\"uuid\":\"00376e8000bffa7a538274d69fff20f2\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 14:33:45",
				"errorInfo": null
			},
			{
				"id": 1638785125180973057,
				"requestUri": "/summer-single/security/login",
				"requestMethod": "POST",
				"requestParams": "{\"username\":\"admin\",\"password\":\"admin\",\"captcha\":\"1nvf3\",\"uuid\":\"00376e8000bffa7a538274d69fff20f2\"}",
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 14:09:56",
				"errorInfo": null
			}
		]
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/log/error/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/error/getById?id=1639257157270171650

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1639257157270171650 | Integer | 是 | ID
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"id": 1639257157270171650,
		"requestUri": null,
		"requestMethod": null,
		"requestParams": null,
		"userAgent": null,
		"ip": null,
		"createTime": null,
		"errorInfo": "java.lang.UnsupportedOperationException: 类型【java.lang.Long】不支持empty判空\r\n\tat me.xiajhuan.summer.core.utils.AssertUtil.throwUnsupportedOperationException(AssertUtil.java:170)\r\n\tat me.xiajhuan.summer.core.utils.AssertUtil.paramValueAssert(AssertUtil.java:153)\r\n\tat me.xiajhuan.summer.core.utils.AssertUtil.isNotEmpty(AssertUtil.java:100)\r\n\tat me.xiajhuan.summer.admin.common.locale.controller.LocaleInternationalNameController.getById(LocaleInternationalNameController.java:81)\r\n\tat me.xiajhuan.summer.admin.common.locale.controller.LocaleInternationalNameController$$FastClassBySpringCGLIB$$cede9274.invoke(<generated>)\r\n\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)\r\n\tat me.xiajhuan.summer.admin.common.base.aspect.LogOperationAspect.around(LogOperationAspect.java:75)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:498)\r\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:634)\r\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:624)\r\n\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:72)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:708)\r\n\tat me.xiajhuan.summer.admin.common.locale.controller.LocaleInternationalNameController$$EnhancerBySpringCGLIB$$11c0caee.getById(<generated>)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:498)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1072)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:965)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:502)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:596)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:209)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\r\n\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:154)\r\n\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\r\n\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:154)\r\n\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\r\n\tat org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:458)\r\n\tat org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:373)\r\n\tat org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\r\n\tat org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\r\n\tat org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\r\n\tat org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:370)\r\n\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:154)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\r\n\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:154)\r\n\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\r\n\tat org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\r\n\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:154)\r\n\tat org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\r\n\tat org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:458)\r\n\tat org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:373)\r\n\tat org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\r\n\tat org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\r\n\tat org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\r\n\tat org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:370)\r\n\tat org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:154)\r\n\tat org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:354)\r\n\tat org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:267)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:96)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:492)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:130)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:389)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:926)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1791)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:748)\r\n"
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/log/error/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/error/excelExport?field=create_time&order=desc&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-03-28 12:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | create_time | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-03-28 12:00:00 | Date | 否 | 创建时间区间（结束）
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/log/login
```text
登录日志
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/log/login/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/login/page?pageNum=1&pageSize=10&field=createTime&order=desc&operation=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-03-28 12:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operation | 0 | Integer | 否 | 用户操作 0：用户登录 1：用户退出
status | 1 | Integer | 否 | 登录状态 0：失败1：成功 2：账号已锁定
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-03-28 12:00:00 | Date | 否 | 创建时间区间（结束）
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"total": 18,
		"rows": [
			{
				"id": 1639464962463821825,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-25 11:11:21"
			},
			{
				"id": 1639463233479454721,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-25 11:04:29"
			},
			{
				"id": 1639162828333731842,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-24 15:10:47"
			},
			{
				"id": 1638814455986634753,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 16:06:29"
			},
			{
				"id": 1638811542060998657,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:54:54"
			},
			{
				"id": 1638810190643679233,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:49:32"
			},
			{
				"id": 1638807868756676609,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:40:18"
			},
			{
				"id": 1638807190546092033,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:37:36"
			},
			{
				"id": 1638806355380473858,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 15:34:17"
			},
			{
				"id": 1638794153000906754,
				"loginUser": "admin",
				"operation": 0,
				"status": 1,
				"userAgent": "ApiPOST Runtime +https://www.apipost.cn",
				"ip": "127.0.0.1",
				"createTime": "2023-03-23 14:45:48"
			}
		]
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/log/login/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/login/excelExport?field=create_time&order=desc&operation=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-03-28 12:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | create_time | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operation | 0 | Integer | 否 | 用户操作 0：用户登录 1：用户退出
status | 1 | Integer | 否 | 登录状态 0：失败1：成功 2：账号已锁定
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-03-28 12:00:00 | Date | 否 | 创建时间区间（结束）
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/security
```text
权限相关模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/security/获取验证码
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/captcha?uuid=00376e8000bffa7a538274d69fff20f2

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
uuid | 00376e8000bffa7a538274d69fff20f2 | String | 是 | uuid
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "服务器繁忙，请稍后再试~",
	"data": null
}
```
## /common/security/用户登录
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/login

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
username | admin | String | 是 | 用户名
password | admin | String | 是 | 密码
captcha | 7g8g7 | String | 是 | 验证码
uuid | 00376e8000bffa7a538274d69fff20f2 | String | 是 | uuid
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"accessToken": "eee4eb696457036abaadb38f6fd4c1df",
		"expireTime": 12
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "验证码不正确",
	"data": null
}
```
## /common/security/用户退出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/logout

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "Token失效，请重新登录"
}
```
## /common/security/user
```text
用户
```
#### 公共Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共认证信息
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/security/role
```text
角色
```
#### 公共Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共认证信息
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/security/dept
```text
部门
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/security/dept/列表
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/dept/list

#### 请求方式
> GET

#### Content-Type
> form-data

#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": [
		{
			"id": 1636548298722242561,
			"parentId": 0,
			"name": "罗邦洁具股份有限公司",
			"weight": 0,
			"extra": null,
			"createTime": "2023-03-17 10:01:35",
			"children": [
				{
					"id": 1636548627622785026,
					"parentId": 1636548298722242561,
					"name": "泰国工厂",
					"weight": 0,
					"extra": null,
					"createTime": "2023-03-17 10:02:53",
					"children": [
						{
							"id": 1636548954166128641,
							"parentId": 1636548627622785026,
							"name": "市场部",
							"weight": 0,
							"extra": null,
							"createTime": "2023-03-17 10:04:11",
							"children": [],
							"parentName": "泰国工厂"
						},
						{
							"id": 1636549079382880258,
							"parentId": 1636548627622785026,
							"name": "销售部",
							"weight": 1,
							"extra": null,
							"createTime": "2023-03-17 10:04:41",
							"children": [],
							"parentName": "泰国工厂"
						}
					],
					"parentName": "罗邦洁具股份有限公司"
				},
				{
					"id": 1636548808003022850,
					"parentId": 1636548298722242561,
					"name": "椒江工厂",
					"weight": 1,
					"extra": null,
					"createTime": "2023-03-17 10:03:36",
					"children": [
						{
							"id": 1636549217312567298,
							"parentId": 1636548808003022850,
							"name": "产品部",
							"weight": 0,
							"extra": null,
							"createTime": "2023-03-17 10:05:14",
							"children": [],
							"parentName": "椒江工厂"
						},
						{
							"id": 1636549260430012417,
							"parentId": 1636548808003022850,
							"name": "技术部",
							"weight": 1,
							"extra": null,
							"createTime": "2023-03-17 10:05:24",
							"children": [
								{
									"id": 1636549534884294658,
									"parentId": 1636549260430012417,
									"name": "模具研发部",
									"weight": 0,
									"extra": null,
									"createTime": "2023-03-17 10:06:29",
									"children": [],
									"parentName": "技术部"
								}
							],
							"parentName": "椒江工厂"
						},
						{
							"id": 1636549294890414082,
							"parentId": 1636548808003022850,
							"name": "销售部",
							"weight": 2,
							"extra": null,
							"createTime": "2023-03-17 10:05:32",
							"children": [],
							"parentName": "椒江工厂"
						}
					],
					"parentName": "罗邦洁具股份有限公司"
				}
			],
			"parentName": null
		}
	]
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/security/dept/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/dept/getById?id=1636549534884294658

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1636549534884294658 | Integer | 是 | ID
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"id": 1636549534884294658,
		"parentId": 1636549260430012417,
		"name": "模具研发部",
		"weight": 0,
		"extra": null,
		"createTime": "2023-03-17 10:06:29",
		"children": [],
		"parentName": "技术部"
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/security/dept/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/dept/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
parentId | 1636549260430012417 | Integer | 是 | 上级部门ID
name | 模具造型部 | String | 是 | 部门名称
weight | 0 | Integer | 是 | 顺序，越小优先级越高
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/security/dept/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/dept/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1639546265158758402 | Integer | 是 | ID
parentId | 1636549260430012417 | Integer | 是 | 上级部门ID
name | 模具研发部 | String | 是 | 部门名称
weight | 0 | Integer | 是 | 顺序，越小优先级越高
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/security/dept/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/dept/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1639546265158758402 | Integer | 是 | ID
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/security/menu
```text
菜单
```
#### 公共Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 公共认证信息
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/locale
```text
国际化模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/locale/internationalName
```text
国际化名称
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /common/locale/internationalName/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/page?pageNum=1&pageSize=10&field=createTime,fieldValue&order=desc,asc&tableName=security_menu

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime,fieldValue | String | 否 | 排序字段，以“,”分隔
order | desc,asc | String | 否 | 排序规则，以“,”分隔
tableName | security_menu | String | 否 | 表名
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"total": 10,
		"rows": [
			{
				"id": 1637654766259195906,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "Delete",
				"locale": "en_US",
				"createTime": "2023-03-20 11:18:17"
			},
			{
				"id": 1637654712052011010,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "删除",
				"locale": "zh_CN",
				"createTime": "2023-03-20 11:18:04"
			},
			{
				"id": 1637654647296151554,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "Edit",
				"locale": "en_US",
				"createTime": "2023-03-20 11:17:49"
			},
			{
				"id": 1637654587099500545,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "修改",
				"locale": "zh_CN",
				"createTime": "2023-03-20 11:17:34"
			},
			{
				"id": 1637654533332717570,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "Add",
				"locale": "en_US",
				"createTime": "2023-03-20 11:17:22"
			},
			{
				"id": 1637654466978828290,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "新增",
				"locale": "zh_CN",
				"createTime": "2023-03-20 11:17:06"
			},
			{
				"id": 1637654312922042370,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "View",
				"locale": "en_US",
				"createTime": "2023-03-20 11:16:29"
			},
			{
				"id": 1637654261814448130,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "查看",
				"locale": "zh_CN",
				"createTime": "2023-03-20 11:16:17"
			},
			{
				"id": 1637653834494562306,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "Department Management",
				"locale": "en_US",
				"createTime": "2023-03-20 11:14:35"
			},
			{
				"id": 1637653692089552898,
				"tableName": "security_menu",
				"fieldName": "name",
				"fieldValue": "部门管理",
				"locale": "zh_CN",
				"createTime": "2023-03-20 11:14:01"
			}
		]
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/getById?id=1637653692089552898

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1637653692089552898 | Integer | 是 | ID
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": null,
	"data": {
		"id": 1637653692089552898,
		"tableName": "security_menu",
		"fieldName": "name",
		"fieldValue": "部门管理",
		"locale": "zh_CN",
		"createTime": "2023-03-20 11:14:01"
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
tableName | security_menu | String | 是 | 表名
fieldName | name | String | 是 | 字段名
fieldValue | 权限管理 | String | 是 | 字段值
locale | zh_CN | String | 是 | 地区语言
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1639541338105655298 | Integer | 是 | ID
tableName | security_menu | String | 是 | 表名
fieldName | name | String | 是 | 字段名
fieldValue | Role Management | String | 是 | 字段值
locale | en_US | String | 是 | 地区语言
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
ids | 1639541999484481537,1639542125145829378 | Integer | 是 | ID数组，以“,”分隔
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/Excel模板下载
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/excelTemplate

#### 请求方式
> GET

#### Content-Type
> form-data

#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/Excel导入
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/excelImport

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
file | d:\Users\jk\Downloads\国际化名称模板.xls | File | 是 | Excel文件
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "操作成功",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /common/locale/internationalName/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/locale/internationalName/excelExport?field=create_time&order=desc&tableName=security_menu

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | create_time | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
tableName | security_menu | String | 否 | 表名
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "拒绝访问，没有权限",
	"data": null
}
```
## /open
```text
开放模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /open/api
```text
api模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /open/api/demo
```text
demo
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /open/api/demo/hello
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/open/api/demo/hello

#### 请求方式
> POST

#### Content-Type
> json

#### 请求Body参数
```javascript
{"greet": "Hellow summer-single", "author": "xiajhuan"}
```
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "成功接收到数据",
	"data": "{\"greet\":\"Hellow summer-single\",\"author\":\"xiajhuan\"}"
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "参数必须是Json格式",
	"data": null
}
```
## /open/test
```text
测试模块
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /open/test/rateLimiter
```text
限流
```
#### Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Query参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### Body参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
## /open/test/rateLimiter/基本限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/open/test/rateLimiter/base

#### 请求方式
> POST

#### Content-Type
> json

#### 请求Body参数
```javascript
{"greet": "Hellow summer-single", "author": "xiajhuan"}
```
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "成功接收到数据",
	"data": "{\"greet\":\"Hellow summer-single\",\"author\":\"xiajhuan\"}"
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "服务器繁忙，请稍后再试~",
	"data": null
}
```
## /open/test/rateLimiter/IP限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/open/test/rateLimiter/ip

#### 请求方式
> POST

#### Content-Type
> json

#### 请求Body参数
```javascript
{"greet": "Hellow summer-single", "author": "xiajhuan"}
```
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "成功接收到数据",
	"data": "{\"greet\":\"Hellow summer-single\",\"author\":\"xiajhuan\"}"
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "服务器繁忙，请稍后再试~",
	"data": null
}
```
## /open/test/rateLimiter/参数限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/open/test/rateLimiter/param

#### 请求方式
> POST

#### Content-Type
> json

#### 请求Body参数
```javascript
{"greet": "Hellow summer-single", "author": "xiajhuan"}
```
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "成功接收到数据",
	"data": "{\"greet\":\"Hellow summer-single\",\"author\":\"xiajhuan\"}"
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "服务器繁忙，请稍后再试~",
	"data": null
}
```
## /open/test/rateLimiter/用户名限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/open/test/rateLimiter/username

#### 请求方式
> POST

#### Content-Type
> json

#### 请求Body参数
```javascript
{"greet": "Hellow summer-single", "author": "xiajhuan"}
```
#### 认证方式
```text
noauth
```
#### 预执行脚本
```javascript
暂无预执行脚本
```
#### 后执行脚本
```javascript
暂无后执行脚本
```
#### 成功响应示例
```javascript
{
	"code": "1",
	"msg": "成功接收到数据",
	"data": "{\"greet\":\"Hellow summer-single\",\"author\":\"xiajhuan\"}"
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "服务器繁忙，请稍后再试~",
	"data": null
}
```