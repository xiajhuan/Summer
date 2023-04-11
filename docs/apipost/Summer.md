## 全局公共参数
#### 全局Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
AccessToken | e38968bfe9e6383d224ae0363a24c1ee | accessToken
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
## /api
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
## /api/open
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
## /api/open/demo
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
## /api/open/demo/hello
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/api/open/demo/hello

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
## /api/test
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
## /api/test/rateLimiter
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
## /api/test/rateLimiter/基本限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/api/test/rateLimiter/base

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
## /api/test/rateLimiter/IP限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/api/test/rateLimiter/ip

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
## /api/test/rateLimiter/参数限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/api/test/rateLimiter/param

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
## /api/test/rateLimiter/用户名限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/api/test/rateLimiter/username

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
## /system
```text
系统模块
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
## /system/security
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
## /system/security/获取验证码
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/captcha?uuid=00376e8000bffa7a538274d69fff20f2

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/security/用户登录
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/login

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
username | superAdmin | String | 是 | 用户名
password | 123456 | String | 是 | 密码
captcha | v8lip | String | 是 | 验证码
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
## /system/security/登录信息
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/info

#### 请求方式
> GET

#### Content-Type
> none

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
		"id": 1498688513380720706,
		"username": "superAdmin",
		"realName": "超级管理员",
		"headUrl": null,
		"gender": null,
		"email": null,
		"mobile": null,
		"deptId": null,
		"status": null,
		"dataScope": 0,
		"createTime": null,
		"deptName": null,
		"roleIdSet": null,
		"postIdSet": null
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "Token失效，请重新登录"
}
```
## /system/security/用户退出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/logout

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
## /system/security/role
```text
角色
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
## /system/security/role/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/role/page?pageNum=1&pageSize=10&field=createTime&order=desc&name=

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
name | - | String | 否 | 角色名称
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
		"total": 2,
		"rows": [
			{
				"id": 1644591359763062786,
				"name": "运维人员",
				"description": "拥有查看日志的权限",
				"createTime": "2023-04-08 14:41:50",
				"menuIdSet": [],
				"deptIdSet": []
			},
			{
				"id": 1644589480094113793,
				"name": "系统管理员",
				"description": "拥有系统管理权限",
				"createTime": "2023-04-08 14:34:22",
				"menuIdSet": [],
				"deptIdSet": []
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
## /system/security/role/列表
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/role/list

#### 请求方式
> GET

#### Content-Type
> none

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
			"id": 1644589480094113793,
			"name": "系统管理员",
			"description": null,
			"createTime": null,
			"menuIdSet": [],
			"deptIdSet": []
		},
		{
			"id": 1644591359763062786,
			"name": "运维人员",
			"description": null,
			"createTime": null,
			"menuIdSet": [],
			"deptIdSet": []
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
## /system/security/role/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/role/getById?id=1644591359763062786

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644591359763062786 | Integer | 是 | ID
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
		"id": 1644591359763062786,
		"name": "运维人员",
		"description": "拥有查看日志的权限",
		"createTime": "2023-04-08 14:41:50",
		"menuIdSet": [
			1644250886280286210,
			1644251921644228609,
			1644254129991421954,
			1644254438981603329,
			1644249552424505346,
			1644254772470714369,
			1644253821047377922,
			1644251503899938817,
			1644253286663688193,
			1644252869271719938
		],
		"deptIdSet": [
			1636548627622785026,
			1636548954166128641,
			1636549079382880258
		]
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "id不能为空",
	"data": null
}
```
## /system/security/role/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/role/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
name | 系统管理员 | String | 是 | 角色名称
description | 拥有系统管理权限 | String | 否 | 描述
menuIdSet | 1644147910819700738,1644152845405667330,1644153599092736001,1644154672847142914,1644155270992642049,1644157656800935937,1644158403013754882,1644158848205570050,1644159233980874754,1644159962489536514,1644160692621393922,1644161676202459138,1644162022752632834,1644162242701934594,1644162484654555138,1644247399261769730,1644247996216086530,1644248300751917057,1644248585805205505,1644249552424505346,1644250886280286210,1644251503899938817,1644251921644228609,1644252869271719938,1644253286663688193,1644253821047377922,1644254129991421954,1644254438981603329,1644254772470714369,1644256030950658049,1644256659760713730,1644256943945781250,1644257173638451202,1644257432523476994,1644257749889683457,1644258035748278274,1644258340347023362,1644587091928719361,1644587720784912385,1644587976218025985,1644588210742534145,1645732824954413057,1645733333916426241,1645733692504252418,1645734043018043393,1645734305539530753,1645738549151768577,1645740146187542529,1645740925552140289,1645742458788356097 | Integer | 否 | 菜单ID集合，以“,”分隔
deptIdSet | 1636548298722242561,1636548627622785026,1636548808003022850,1636548954166128641,1636549079382880258,1636549217312567298,1636549260430012417,1636549294890414082,1636549534884294658 | Integer | 否 | 部门ID集合，以“,”分隔
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
	"msg": "角色【超级管理员】已存在",
	"data": null
}
```
## /system/security/role/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/role/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644589480094113793 | Integer | 是 | ID
name | 系统管理员 | String | 是 | 角色名称
description | 拥有系统管理权限 | String | 否 | 描述
menuIdSet | 1644147910819700738,1644152845405667330,1644153599092736001,1644154672847142914,1644155270992642049,1644157656800935937,1644158403013754882,1644158848205570050,1644159233980874754,1644159962489536514,1644160692621393922,1644161676202459138,1644162022752632834,1644162242701934594,1644162484654555138,1644247399261769730,1644247996216086530,1644248300751917057,1644248585805205505,1644249552424505346,1644250886280286210,1644251503899938817,1644251921644228609,1644252869271719938,1644253286663688193,1644253821047377922,1644254129991421954,1644254438981603329,1644254772470714369,1644256030950658049,1644256659760713730,1644256943945781250,1644257173638451202,1644257432523476994,1644257749889683457,1644258035748278274,1644258340347023362,1644587091928719361,1644587720784912385,1644587976218025985,1644588210742534145,1645732824954413057,1645733333916426241,1645733692504252418,1645734043018043393,1645734305539530753,1645738549151768577,1645740146187542529,1645740925552140289,1645742458788356097 | Integer | 否 | 菜单ID集合，以“,”分隔
deptIdSet | 1636548298722242561,1636548627622785026,1636548808003022850,1636548954166128641,1636549079382880258,1636549217312567298,1636549260430012417,1636549294890414082,1636549534884294658 | Integer | 否 | 部门ID集合，以“,”分隔
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
	"msg": "id【ID不能为空】",
	"data": null
}
```
## /system/security/role/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/role/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
ids | 1644281738557005825 | Integer | 是 | ID数组，以“,”分隔
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
	"msg": "ids不能为空",
	"data": null
}
```
## /system/security/user
```text
用户
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
## /system/security/user/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/page?pageNum=1&pageSize=10&field=createTime&order=desc&username=&deptId=&status=1

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
username | - | String | 否 | 用户名
deptId | - | Integer | 否 | 本部门ID
status | 1 | Integer | 否 | 状态  0：停用 1：正常
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
		"total": 2,
		"rows": [
			{
				"id": 1645393565563150337,
				"username": "test233",
				"realName": "测试账号",
				"headUrl": "xxx",
				"gender": 0,
				"email": "",
				"mobile": "",
				"deptId": 1636548627622785026,
				"status": 1,
				"dataScope": 3,
				"createTime": "2023-04-10 19:49:31",
				"deptName": "泰国工厂",
				"roleIdSet": [],
				"postIdSet": []
			},
			{
				"id": 1645392640257744897,
				"username": "systemAdmin",
				"realName": "系统管理员",
				"headUrl": "",
				"gender": 2,
				"email": "xiajhuan@163.com",
				"mobile": "",
				"deptId": 1636548298722242561,
				"status": 1,
				"dataScope": 1,
				"createTime": "2023-04-10 19:45:50",
				"deptName": "罗邦洁具股份有限公司",
				"roleIdSet": [],
				"postIdSet": []
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
## /system/security/user/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/getById?id=1645392640257744897

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1645392640257744897 | Integer | 是 | ID
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
		"id": 1645392640257744897,
		"username": "systemAdmin",
		"realName": "系统管理员",
		"headUrl": "",
		"gender": 2,
		"email": "xiajhuan@163.com",
		"mobile": "",
		"deptId": 1636548298722242561,
		"status": 1,
		"dataScope": 1,
		"createTime": "2023-04-10 19:45:50",
		"deptName": "罗邦洁具股份有限公司",
		"roleIdSet": [
			1644589480094113793,
			1644591359763062786
		],
		"postIdSet": [
			1644573719426424833
		]
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "id不能为空",
	"data": null
}
```
## /system/security/user/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
username | test233 | String | 是 | 用户名
deptId | 1636548627622785026 | Integer | 是 | 本部门ID
password | 123456 | String | 是 | 密码
confirmPassword | 123456 | String | 是 | 确认密码
realName | 测试账号 | String | 是 | 真实姓名
gender | 0 | Integer | 是 | 性别 0：男 1：女 2：保密
email | - | String | 否 | 邮箱
mobile | - | String | 否 | 手机号
roleIdSet | 1644591359763062786 | Integer | 否 | 角色ID集合，以“,”分隔
postIdSet | 1644573768797577217 | Integer | 否 | 岗位ID集合，以“,”分隔
dataScope | 3 | Integer | 是 | 数据权限 0：全部 1：基于角色 2：本部门 3：本部门及以下 4：仅本人
headUrl | xxx | String | 否 | 头像URL
status | 1 | Integer | 是 | 状态 0：停用 1：正常
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
	"msg": "mobile【手机号格式不正确】",
	"data": null
}
```
## /system/security/user/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1645392640257744897 | Integer | 是 | ID
username | systemAdmin | String | 是 | 用户名
deptId | 1636548298722242561 | Integer | 是 | 本部门ID
password | - | String | 否 | 密码
confirmPassword | - | String | 否 | 确认密码
realName | 系统管理员 | String | 是 | 真实姓名
gender | 2 | Integer | 是 | 性别 0：男 1：女 2：保密
email | xiajhuan@163.com | String | 否 | 邮箱
mobile | 13615752444 | String | 否 | 手机号
roleIdSet | 1644589480094113793 | Integer | 否 | 角色ID集合，以“,”分隔
postIdSet | 1644573719426424833,1644573768797577217 | Integer | 否 | 岗位ID集合，以“,”分隔
dataScope | 3 | Integer | 是 | 数据权限 0：全部 1：基于角色 2：本部门 3：本部门及以下 4：仅本人
headUrl | - | String | 否 | 头像URL
status | 1 | Integer | 是 | 状态 0：停用 1：正常
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
	"msg": "用户【testAdmin】已存在",
	"data": null
}
```
## /system/security/user/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
ids | 1645351146897440769 | Integer | 是 | ID数组，以“,”分隔
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
	"msg": "ids不能为空",
	"data": null
}
```
## /system/security/user/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/excelExport?field=create_time&order=desc&username=&deptId=&status=1

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | create_time | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
username | - | String | 否 | 用户名
deptId | - | Integer | 否 | 本部门ID
status | 1 | Integer | 否 | 状态  0：停用 1：正常
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
## /system/security/user/修改密码
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/password

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
oldPassword | xiajhuan@163.com | String | 是 | 原密码
newPassword | 123456 | String | 是 | 新密码
confirmPassword | 123456 | String | 是 | 确认密码
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
	"msg": "原密码不正确",
	"data": null
}
```
## /system/security/user/重置密码
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/user/reset

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
ids | 1498688513380720706 | Integer | 是 | ID数组，以“,”分隔
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
	"msg": "密码成功重置为：123456",
	"data": null
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "ids不能为空",
	"data": null
}
```
## /system/security/post
```text
岗位
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
## /system/security/post/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/post/page?pageNum=1&pageSize=10&field=createTime&order=desc&codeOrName=&status=1

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
codeOrName | - | String | 否 | 岗位编码或名称
status | 1 | Integer | 否 | 状态  0：停用 1：正常
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
		"total": 2,
		"rows": [
			{
				"id": 1644573719426424833,
				"code": "1002",
				"name": "管理岗",
				"status": 1,
				"createTime": "2023-04-08 13:31:44"
			},
			{
				"id": 1644572881052164098,
				"code": "1001",
				"name": "技术岗",
				"status": 1,
				"createTime": "2023-04-08 13:28:24"
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
## /system/security/post/列表
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/post/list

#### 请求方式
> GET

#### Content-Type
> none

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
			"id": 1644572881052164098,
			"code": null,
			"name": "技术岗",
			"status": null,
			"createTime": null
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
## /system/security/post/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/post/getById?id=1644573768797577217

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644573768797577217 | Integer | 是 | ID
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
		"id": 1644573768797577217,
		"code": "1003",
		"name": "测试岗233",
		"status": 0,
		"createTime": "2023-04-08 13:31:56"
	}
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "id不能为空",
	"data": null
}
```
## /system/security/post/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/post/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
code | 1004 | String | 是 | 岗位编码
name | 运维岗 | String | 是 | 岗位名称
status | 1 | Integer | 是 | 状态  0：停用 1：正常
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
	"msg": "岗位【1001】已存在",
	"data": null
}
```
## /system/security/post/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/post/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644573768797577217 | Integer | 是 | ID
name | 测试岗233 | String | 是 | 岗位名称
status | 0 | String | 是 | 状态  0：停用 1：正常
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
	"msg": "code【岗位编码必须为空】",
	"data": null
}
```
## /system/security/post/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/post/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
ids | 1644578050695405570 | Integer | 是 | ID数组，以“,”分隔
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
	"msg": "ids不能为空",
	"data": null
}
```
## /system/security/menu
```text
菜单
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
## /system/security/menu/列表
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/list

#### 请求方式
> GET

#### Content-Type
> none

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
			"id": 1644147910819700738,
			"parentId": 0,
			"name": "权限管理",
			"weight": 0,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-safetycertificate",
			"createTime": "2023-04-07 09:19:43",
			"children": [
				{
					"id": 1644155270992642049,
					"parentId": 1644147910819700738,
					"name": "角色管理",
					"weight": 0,
					"extra": null,
					"url": "security/role",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-team",
					"createTime": "2023-04-07 09:48:58",
					"children": [
						{
							"id": 1644247399261769730,
							"parentId": 1644155270992642049,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:role:page,security:role:list,security:role:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:55:03",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644247996216086530,
							"parentId": 1644155270992642049,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:role:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:57:26",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644248300751917057,
							"parentId": 1644155270992642049,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:role:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:58:38",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644248585805205505,
							"parentId": 1644155270992642049,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:role:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:59:46",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644152845405667330,
					"parentId": 1644147910819700738,
					"name": "用户管理",
					"weight": 1,
					"extra": null,
					"url": "security/user",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-user",
					"createTime": "2023-04-07 09:39:20",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644153599092736001,
					"parentId": 1644147910819700738,
					"name": "岗位管理",
					"weight": 2,
					"extra": null,
					"url": "security/post",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-pic-left",
					"createTime": "2023-04-07 09:42:20",
					"children": [
						{
							"id": 1644587091928719361,
							"parentId": 1644153599092736001,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:post:page,security:post:list,security:post:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:24:52",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644587720784912385,
							"parentId": 1644153599092736001,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:post:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:27:22",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644587976218025985,
							"parentId": 1644153599092736001,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:post:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:28:23",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644588210742534145,
							"parentId": 1644153599092736001,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:post:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:29:19",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644160692621393922,
					"parentId": 1644147910819700738,
					"name": "菜单管理",
					"weight": 3,
					"extra": null,
					"url": "security/menu",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-unorderedlist",
					"createTime": "2023-04-07 10:10:31",
					"children": [
						{
							"id": 1644161676202459138,
							"parentId": 1644160692621393922,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:menu:list,security:menu:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:14:25",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644162022752632834,
							"parentId": 1644160692621393922,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:menu:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:15:48",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644162242701934594,
							"parentId": 1644160692621393922,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:menu:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:16:40",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644162484654555138,
							"parentId": 1644160692621393922,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:menu:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:17:38",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644154672847142914,
					"parentId": 1644147910819700738,
					"name": "部门管理",
					"weight": 4,
					"extra": null,
					"url": "security/dept",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-apartment",
					"createTime": "2023-04-07 09:46:36",
					"children": [
						{
							"id": 1644157656800935937,
							"parentId": 1644154672847142914,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:dept:list,security:dept:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 09:58:27",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644158403013754882,
							"parentId": 1644154672847142914,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:dept:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:01:25",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644158848205570050,
							"parentId": 1644154672847142914,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:dept:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:03:11",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644159233980874754,
							"parentId": 1644154672847142914,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:dept:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:04:43",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				}
			],
			"parentName": null
		},
		{
			"id": 1644159962489536514,
			"parentId": 0,
			"name": "系统设置",
			"weight": 1,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-setting",
			"createTime": "2023-04-07 10:07:37",
			"children": [
				{
					"id": 1644256030950658049,
					"parentId": 1644159962489536514,
					"name": "国际化名称",
					"weight": 1,
					"extra": null,
					"url": "locale/internationalName",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-file-word",
					"createTime": "2023-04-07 16:29:21",
					"children": [
						{
							"id": 1644256659760713730,
							"parentId": 1644256030950658049,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:page,locale:internationalName:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:31:51",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644256943945781250,
							"parentId": 1644256030950658049,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:32:59",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644257173638451202,
							"parentId": 1644256030950658049,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:33:54",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644257432523476994,
							"parentId": 1644256030950658049,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:34:55",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644257749889683457,
							"parentId": 1644256030950658049,
							"name": "模板下载",
							"weight": 4,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:excelTemplate",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:36:11",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644258035748278274,
							"parentId": 1644256030950658049,
							"name": "导入",
							"weight": 5,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:excelImport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:37:19",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644258340347023362,
							"parentId": 1644256030950658049,
							"name": "导出",
							"weight": 6,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:38:32",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				}
			],
			"parentName": null
		},
		{
			"id": 1644249552424505346,
			"parentId": 0,
			"name": "日志管理",
			"weight": 2,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-container",
			"createTime": "2023-04-07 16:03:37",
			"children": [
				{
					"id": 1644250886280286210,
					"parentId": 1644249552424505346,
					"name": "操作日志",
					"weight": 0,
					"extra": null,
					"url": "log/operation",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-solution",
					"createTime": "2023-04-07 16:08:55",
					"children": [
						{
							"id": 1644252869271719938,
							"parentId": 1644250886280286210,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "log:operation:page",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:16:47",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644253286663688193,
							"parentId": 1644250886280286210,
							"name": "导出",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "log:operation:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:18:27",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644251503899938817,
					"parentId": 1644249552424505346,
					"name": "错误日志",
					"weight": 1,
					"extra": null,
					"url": "log/error",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-file-exception",
					"createTime": "2023-04-07 16:11:22",
					"children": [
						{
							"id": 1644253821047377922,
							"parentId": 1644251503899938817,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "log:error:page,log:error:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:20:34",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644254129991421954,
							"parentId": 1644251503899938817,
							"name": "导出",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "log:error:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:21:48",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644251921644228609,
					"parentId": 1644249552424505346,
					"name": "登录日志",
					"weight": 2,
					"extra": null,
					"url": "log/login",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-filedone",
					"createTime": "2023-04-07 16:13:02",
					"children": [
						{
							"id": 1644254438981603329,
							"parentId": 1644251921644228609,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "log:login:page",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:23:02",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644254772470714369,
							"parentId": 1644251921644228609,
							"name": "导出",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "log:login:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:24:21",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
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
## /system/security/menu/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/getById?id=1644147910819700738

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644147910819700738 | Integer | 是 | ID
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
		"id": 1644147910819700738,
		"parentId": 0,
		"name": "权限管理",
		"weight": 0,
		"extra": null,
		"url": "",
		"permissions": "",
		"type": 0,
		"openMode": 0,
		"icon": "icon-safetycertificate",
		"createTime": "2023-04-07 09:19:43",
		"children": [],
		"parentName": null
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
## /system/security/menu/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
parentId | 1645738549151768577 | Integer | 是 | 上级菜单ID
name | System information | String | 是 | 菜单名称
url | monitor/system | String | 否 | 菜单URL
permissions | monitor:system:info | String | 否 | 授权
type | 0 | Integer | 是 | 类型 0：菜单 1：按钮
openMode | 0 | Integer | 否 | 打开方式 0：内部 1：外部
icon | icon-database | String | 否 | 图标
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
	"msg": "name【菜单名称不能为空】",
	"data": null
}
```
## /system/security/menu/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644162484654555138 | Integer | 是 | ID
parentId | 1644160692621393922 | Integer | 是 | 上级菜单ID
name | 删除233 | String | 是 | 菜单名称
url | - | String | 否 | 菜单URL
permissions | security:menu:remove | String | 否 | 授权
type | 1 | Integer | 是 | 类型 0：菜单 1：按钮
openMode | - | Integer | 否 | 打开方式 0：内部 1：外部
icon | - | String | 否 | 图标
weight | 3 | Integer | 是 | 顺序，越小优先级越高
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
	"msg": "上级菜单不能为自身",
	"data": null
}
```
## /system/security/menu/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644177630772723713 | Integer | 是 | ID
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
## /system/security/menu/导航
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/nav

#### 请求方式
> GET

#### Content-Type
> none

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
			"id": 1644147910819700738,
			"parentId": 0,
			"name": "权限管理",
			"weight": 0,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-safetycertificate",
			"createTime": "2023-04-07 09:19:43",
			"children": [
				{
					"id": 1644152845405667330,
					"parentId": 1644147910819700738,
					"name": "用户管理",
					"weight": 0,
					"extra": null,
					"url": "security/user",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-user",
					"createTime": "2023-04-07 09:39:20",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644153599092736001,
					"parentId": 1644147910819700738,
					"name": "岗位管理",
					"weight": 1,
					"extra": null,
					"url": "security/post",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-pic-left",
					"createTime": "2023-04-07 09:42:20",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644154672847142914,
					"parentId": 1644147910819700738,
					"name": "部门管理",
					"weight": 2,
					"extra": null,
					"url": "security/dept",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-apartment",
					"createTime": "2023-04-07 09:46:36",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644155270992642049,
					"parentId": 1644147910819700738,
					"name": "角色管理",
					"weight": 3,
					"extra": null,
					"url": "security/role",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-team",
					"createTime": "2023-04-07 09:48:58",
					"children": [],
					"parentName": null
				}
			],
			"parentName": null
		},
		{
			"id": 1644159962489536514,
			"parentId": 0,
			"name": "系统设置",
			"weight": 1,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-setting",
			"createTime": "2023-04-07 10:07:37",
			"children": [
				{
					"id": 1644160692621393922,
					"parentId": 1644159962489536514,
					"name": "菜单管理",
					"weight": 0,
					"extra": null,
					"url": "security/menu",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-unorderedlist",
					"createTime": "2023-04-07 10:10:31",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644256030950658049,
					"parentId": 1644159962489536514,
					"name": "国际化名称",
					"weight": 1,
					"extra": null,
					"url": "locale/internationalName",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-file-word",
					"createTime": "2023-04-07 16:29:21",
					"children": [],
					"parentName": null
				}
			],
			"parentName": null
		},
		{
			"id": 1644249552424505346,
			"parentId": 0,
			"name": "日志管理",
			"weight": 2,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-container",
			"createTime": "2023-04-07 16:03:37",
			"children": [
				{
					"id": 1644250886280286210,
					"parentId": 1644249552424505346,
					"name": "操作日志",
					"weight": 0,
					"extra": null,
					"url": "log/operation",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-solution",
					"createTime": "2023-04-07 16:08:55",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644251503899938817,
					"parentId": 1644249552424505346,
					"name": "错误日志",
					"weight": 1,
					"extra": null,
					"url": "log/error",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-file-exception",
					"createTime": "2023-04-07 16:11:22",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644251921644228609,
					"parentId": 1644249552424505346,
					"name": "登录日志",
					"weight": 2,
					"extra": null,
					"url": "log/login",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-filedone",
					"createTime": "2023-04-07 16:13:02",
					"children": [],
					"parentName": null
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
	"msg": "Token失效，请重新登录"
}
```
## /system/security/menu/全部
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/all

#### 请求方式
> GET

#### Content-Type
> none

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
			"id": 1644147910819700738,
			"parentId": 0,
			"name": "权限管理",
			"weight": 0,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-safetycertificate",
			"createTime": "2023-04-07 09:19:43",
			"children": [
				{
					"id": 1644155270992642049,
					"parentId": 1644147910819700738,
					"name": "角色管理",
					"weight": 0,
					"extra": null,
					"url": "security/role",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-team",
					"createTime": "2023-04-07 09:48:58",
					"children": [
						{
							"id": 1644247399261769730,
							"parentId": 1644155270992642049,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:role:page,security:role:list,security:role:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:55:03",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644247996216086530,
							"parentId": 1644155270992642049,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:role:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:57:26",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644248300751917057,
							"parentId": 1644155270992642049,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:role:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:58:38",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644248585805205505,
							"parentId": 1644155270992642049,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:role:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 15:59:46",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644152845405667330,
					"parentId": 1644147910819700738,
					"name": "用户管理",
					"weight": 1,
					"extra": null,
					"url": "security/user",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-user",
					"createTime": "2023-04-07 09:39:20",
					"children": [],
					"parentName": null
				},
				{
					"id": 1644153599092736001,
					"parentId": 1644147910819700738,
					"name": "岗位管理",
					"weight": 2,
					"extra": null,
					"url": "security/post",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-pic-left",
					"createTime": "2023-04-07 09:42:20",
					"children": [
						{
							"id": 1644587091928719361,
							"parentId": 1644153599092736001,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:post:page,security:post:list,security:post:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:24:52",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644587720784912385,
							"parentId": 1644153599092736001,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:post:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:27:22",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644587976218025985,
							"parentId": 1644153599092736001,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:post:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:28:23",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644588210742534145,
							"parentId": 1644153599092736001,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:post:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-08 14:29:19",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644160692621393922,
					"parentId": 1644147910819700738,
					"name": "菜单管理",
					"weight": 3,
					"extra": null,
					"url": "security/menu",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-unorderedlist",
					"createTime": "2023-04-07 10:10:31",
					"children": [
						{
							"id": 1644161676202459138,
							"parentId": 1644160692621393922,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:menu:list,security:menu:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:14:25",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644162022752632834,
							"parentId": 1644160692621393922,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:menu:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:15:48",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644162242701934594,
							"parentId": 1644160692621393922,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:menu:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:16:40",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644162484654555138,
							"parentId": 1644160692621393922,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:menu:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:17:38",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644154672847142914,
					"parentId": 1644147910819700738,
					"name": "部门管理",
					"weight": 4,
					"extra": null,
					"url": "security/dept",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-apartment",
					"createTime": "2023-04-07 09:46:36",
					"children": [
						{
							"id": 1644157656800935937,
							"parentId": 1644154672847142914,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "security:dept:list,security:dept:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 09:58:27",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644158403013754882,
							"parentId": 1644154672847142914,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "security:dept:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:01:25",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644158848205570050,
							"parentId": 1644154672847142914,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "security:dept:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:03:11",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644159233980874754,
							"parentId": 1644154672847142914,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "security:dept:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 10:04:43",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				}
			],
			"parentName": null
		},
		{
			"id": 1644159962489536514,
			"parentId": 0,
			"name": "系统设置",
			"weight": 1,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-setting",
			"createTime": "2023-04-07 10:07:37",
			"children": [
				{
					"id": 1644256030950658049,
					"parentId": 1644159962489536514,
					"name": "国际化名称",
					"weight": 1,
					"extra": null,
					"url": "locale/internationalName",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-file-word",
					"createTime": "2023-04-07 16:29:21",
					"children": [
						{
							"id": 1644256659760713730,
							"parentId": 1644256030950658049,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:page,locale:internationalName:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:31:51",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644256943945781250,
							"parentId": 1644256030950658049,
							"name": "新增",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:add",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:32:59",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644257173638451202,
							"parentId": 1644256030950658049,
							"name": "修改",
							"weight": 2,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:update",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:33:54",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644257432523476994,
							"parentId": 1644256030950658049,
							"name": "删除",
							"weight": 3,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:delete",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:34:55",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644257749889683457,
							"parentId": 1644256030950658049,
							"name": "模板下载",
							"weight": 4,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:excelTemplate",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:36:11",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644258035748278274,
							"parentId": 1644256030950658049,
							"name": "导入",
							"weight": 5,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:excelImport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:37:19",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644258340347023362,
							"parentId": 1644256030950658049,
							"name": "导出",
							"weight": 6,
							"extra": null,
							"url": "",
							"permissions": "locale:internationalName:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:38:32",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				}
			],
			"parentName": null
		},
		{
			"id": 1644249552424505346,
			"parentId": 0,
			"name": "日志管理",
			"weight": 2,
			"extra": null,
			"url": "",
			"permissions": "",
			"type": 0,
			"openMode": 0,
			"icon": "icon-container",
			"createTime": "2023-04-07 16:03:37",
			"children": [
				{
					"id": 1644250886280286210,
					"parentId": 1644249552424505346,
					"name": "操作日志",
					"weight": 0,
					"extra": null,
					"url": "log/operation",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-solution",
					"createTime": "2023-04-07 16:08:55",
					"children": [
						{
							"id": 1644252869271719938,
							"parentId": 1644250886280286210,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "log:operation:page",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:16:47",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644253286663688193,
							"parentId": 1644250886280286210,
							"name": "导出",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "log:operation:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:18:27",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644251503899938817,
					"parentId": 1644249552424505346,
					"name": "错误日志",
					"weight": 1,
					"extra": null,
					"url": "log/error",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-file-exception",
					"createTime": "2023-04-07 16:11:22",
					"children": [
						{
							"id": 1644253821047377922,
							"parentId": 1644251503899938817,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "log:error:page,log:error:getById",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:20:34",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644254129991421954,
							"parentId": 1644251503899938817,
							"name": "导出",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "log:error:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:21:48",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
				},
				{
					"id": 1644251921644228609,
					"parentId": 1644249552424505346,
					"name": "登录日志",
					"weight": 2,
					"extra": null,
					"url": "log/login",
					"permissions": "",
					"type": 0,
					"openMode": 0,
					"icon": "icon-filedone",
					"createTime": "2023-04-07 16:13:02",
					"children": [
						{
							"id": 1644254438981603329,
							"parentId": 1644251921644228609,
							"name": "查看",
							"weight": 0,
							"extra": null,
							"url": "",
							"permissions": "log:login:page",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:23:02",
							"children": [],
							"parentName": null
						},
						{
							"id": 1644254772470714369,
							"parentId": 1644251921644228609,
							"name": "导出",
							"weight": 1,
							"extra": null,
							"url": "",
							"permissions": "log:login:excelExport",
							"type": 1,
							"openMode": null,
							"icon": "",
							"createTime": "2023-04-07 16:24:21",
							"children": [],
							"parentName": null
						}
					],
					"parentName": null
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
## /system/security/menu/用户权限集合
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/menu/permissions

#### 请求方式
> GET

#### Content-Type
> none

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
		"log:error:excelExport",
		"security:dept:getById",
		"log:login:page",
		"security:post:getById",
		"security:post:update",
		"log:error:getById",
		"locale:internationalName:delete",
		"log:error:page",
		"security:menu:add",
		"security:menu:getById",
		"log:operation:page",
		"security:role:list",
		"security:post:delete",
		"log:login:excelExport",
		"locale:internationalName:excelImport",
		"security:dept:list",
		"security:role:add",
		"security:menu:update",
		"locale:internationalName:page",
		"locale:internationalName:excelExport",
		"log:operation:excelExport",
		"security:dept:add",
		"security:dept:update",
		"security:role:page",
		"locale:internationalName:getById",
		"locale:internationalName:update",
		"security:post:list",
		"locale:internationalName:add",
		"security:post:add",
		"security:menu:list",
		"security:role:update",
		"security:role:getById",
		"security:dept:delete",
		"security:role:delete",
		"locale:internationalName:excelTemplate",
		"security:post:page",
		"security:menu:delete"
	]
}
```
#### 错误响应示例
```javascript
{
	"code": "0",
	"msg": "Token失效，请重新登录"
}
```
## /system/security/dept
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
## /system/security/dept/列表
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/dept/list

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/security/dept/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/dept/getById?id=1636549534884294658

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/security/dept/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/dept/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
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
## /system/security/dept/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/dept/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1636549534884294658 | Integer | 是 | ID
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
## /system/security/dept/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/dept/delete

#### 请求方式
> DELETE

#### Content-Type
> urlencoded

#### 请求Body参数
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
## /system/security/dept/全部
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/security/dept/all

#### 请求方式
> GET

#### Content-Type
> none

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
									"id": 1644714620953980930,
									"parentId": 1636549260430012417,
									"name": "模具研发部",
									"weight": 0,
									"extra": null,
									"createTime": "2023-04-08 22:51:38",
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
## /system/locale
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
## /system/locale/internationalName
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
## /system/locale/internationalName/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/page?pageNum=1&pageSize=10&field=createTime,fieldValue&order=desc,asc&tableName=security_menu

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/locale/internationalName/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/getById?id=1644257749889683458

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1644257749889683458 | Integer | 是 | ID
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
		"id": 1644257749889683458,
		"tableName": "security_menu",
		"lineId": "1644257749889683457",
		"fieldName": "name",
		"fieldValue": "模板下载",
		"locale": "zh_CN",
		"createTime": "2023-04-07 16:36:11"
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
## /system/locale/internationalName/新增
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/add

#### 请求方式
> POST

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
tableName | security_menu | String | 是 | 表名
lineId | 1644157656800935937 | Integer | 是 | 行ID
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
	"msg": "国际化名称行ID【1644157656800935937】+地区语言【zh_CN】已存在",
	"data": null
}
```
## /system/locale/internationalName/修改
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/update

#### 请求方式
> PUT

#### Content-Type
> urlencoded

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1641040580263530498 | Integer | 是 | ID
fieldValue | Role Management | String | 是 | 字段值
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
	"msg": "fieldName【字段名必须为空】",
	"data": null
}
```
## /system/locale/internationalName/删除
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/delete

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
## /system/locale/internationalName/Excel模板下载
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/excelTemplate

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/locale/internationalName/Excel导入
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/excelImport

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
## /system/locale/internationalName/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/locale/internationalName/excelExport?field=create_time&order=desc&tableName=security_menu

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/log
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
## /system/log/operation
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
## /system/log/operation/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/operation/page?pageNum=1&pageSize=10&field=createTime&order=desc&operationGroup=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-04-21 12:00:00

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operationGroup | 0 | Integer | 否 | 操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation
status | 1 | Integer | 否 | 状态  0：失败 1：成功
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-04-21 12:00:00 | Date | 否 | 创建时间区间（结束）
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
## /system/log/operation/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/operation/excelExport?field=createTime&order=desc&operationGroup=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-04-21 12:00:00

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operationGroup | 0 | Integer | 否 | 操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation
status | 1 | Integer | 否 | 状态  0：失败 1：成功
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-04-21 12:00:00 | Date | 否 | 创建时间区间（结束）
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
	"msg": "最多导出300条记录",
	"data": null
}
```
## /system/log/error
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
## /system/log/error/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/error/page?pageNum=1&pageSize=10&field=createTime&order=desc&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-04-21 12:00:00

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-04-21 12:00:00 | Date | 否 | 创建时间区间（结束）
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
## /system/log/error/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/error/getById?id=1639257157270171650

#### 请求方式
> GET

#### Content-Type
> none

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
## /system/log/error/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/error/excelExport?field=create_time&order=desc&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-04-21 12:00:00

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | create_time | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-04-21 12:00:00 | Date | 否 | 创建时间区间（结束）
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
## /system/log/login
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
## /system/log/login/分页
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/login/page?pageNum=1&pageSize=10&field=createTime&order=desc&operation=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-04-21 12:00:00

#### 请求方式
> GET

#### Content-Type
> none

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
createTimeEnd | 2023-04-21 12:00:00 | Date | 否 | 创建时间区间（结束）
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
## /system/log/login/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer/log/login/excelExport?field=create_time&order=desc&operation=0&status=1&createTimeStart=2023-03-21 12:00:00&createTimeEnd=2023-04-21 12:00:00

#### 请求方式
> GET

#### Content-Type
> none

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
field | create_time | String | 否 | 排序字段，以“,”分隔
order | desc | String | 否 | 排序规则，以“,”分隔
operation | 0 | Integer | 否 | 用户操作 0：用户登录 1：用户退出
status | 1 | Integer | 否 | 登录状态 0：失败1：成功 2：账号已锁定
createTimeStart | 2023-03-21 12:00:00 | Date | 否 | 创建时间区间（开始）
createTimeEnd | 2023-04-21 12:00:00 | Date | 否 | 创建时间区间（结束）
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