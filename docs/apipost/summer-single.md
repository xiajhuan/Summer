## 全局公共参数
#### 全局Header参数
参数名 | 示例值 | 参数描述
--- | --- | ---
暂无参数
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
> http://localhost:6666/summer-single/log/operation/page?pageNum=1&pageSize=10&field=createTime&order=desc&operationGroup=0&status=1&params.createTimeStart=2023-03-0312:00:00&params.createTimeEnd=2023-03-0412:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段
order | desc | String | 否 | 排序规则
operationGroup | 0 | Integer | 否 | 操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation
status | 1 | Integer | 否 | 状态  0：失败 1：成功
params.createTimeStart | 2023-03-03%2012:00:00 | Date | 否 | 创建时间区间（开始）
params.createTimeEnd | 2023-03-04%2012:00:00 | Date | 否 | 创建时间区间（结束）
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
## /common/log/operation/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/operation/excelExport

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
operationGroup | 0 | Integer | 否 | 操作分组 0：Common Crud 1：Excel Opeation 2：Other Operation
status | 1 | Integer | 否 | 状态  0：失败 1：成功
params.createTimeStart | 2023-03-03 12:00:00 | Date | 否 | 创建时间区间（开始）
params.createTimeEnd | 2023-03-04 12:00:00 | Date | 否 | 创建时间区间（结束）
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
> http://localhost:6666/summer-single/log/error/page?pageNum=1&pageSize=10&field=createTime&order=desc&params.createTimeStart=2023-03-0312:00:00&params.createTimeEnd=2023-03-0412:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段
order | desc | String | 否 | 排序规则
params.createTimeStart | 2023-03-0312:00:00 | Date | 否 | 创建时间区间（开始）
params.createTimeEnd | 2023-03-0412:00:00 | Date | 否 | 创建时间区间（结束）
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
## /common/log/error/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/error/getById?id=1

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1 | Integer | 是 | ID
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
## /common/log/error/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/error/excelExport

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
params.createTimeStart | 2023-03-03 12:00:00 | Date | 否 | 创建时间区间（开始）
params.createTimeEnd | 2023-03-04 12:00:00 | Date | 否 | 创建时间区间（结束）
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
> http://localhost:6666/summer-single/log/login/page?pageNum=1&pageSize=10&field=createTime&order=desc&operation=0&status=1&params.createTimeStart=2023-03-0312:00:00&params.createTimeEnd=2023-03-0412:00:00

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
pageNum | 1 | Integer | 否 | 当前页码
pageSize | 10 | Integer | 否 | 每页记录数
field | createTime | String | 否 | 排序字段
order | desc | String | 否 | 排序规则
operation | 0 | Integer | 否 | 用户操作 0：用户登录 1：用户退出
status | 1 | Integer | 否 | 登录状态 0：失败1：成功 2：账号已锁定
params.createTimeStart | 2023-03-0312:00:00 | Date | 否 | 创建时间区间（开始）
params.createTimeEnd | 2023-03-0412:00:00 | Date | 否 | 创建时间区间（结束）
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
## /common/log/login/Excel导出
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/log/login/excelExport

#### 请求方式
> POST

#### Content-Type
> form-data

#### 请求Body参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
operation | 0 | Integer | 否 | 用户操作 0：用户登录 1：用户退出
status | 1 | Integer | 否 | 登录状态 0：失败1：成功 2：账号已锁定
params.createTimeStart | 2023-03-03 12:00:00 | Date | 否 | 创建时间区间（开始）
params.createTimeEnd | 2023-03-04 12:00:00 | Date | 否 | 创建时间区间（结束）
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
password | 123456 | String | 是 | 密码
captcha | 2438 | String | 是 | 验证码
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
## /common/security/dept/根据ID获取
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/security/dept/getById?id=1

#### 请求方式
> GET

#### Content-Type
> form-data

#### 请求Query参数
参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述
--- | --- | --- | --- | ---
id | 1 | Integer | 是 | ID
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
parentId | 0 | Integer | 是 | 上级部门ID
name | 罗邦洁具股份有限公司 | String | 是 | 部门名称
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
id | 1 | Integer | 是 | ID
parentId | 0 | Integer | 是 | 上级部门ID
name | 罗邦洁具股份有限公司 | String | 是 | 部门名称
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
id | 1 | Integer | 是 | ID
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
> http://localhost:6666/summer-single/api/demo/hello

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
> http://localhost:6666/summer-single/test/rateLimiter/base

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
## /open/test/rateLimiter/IP限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/test/rateLimiter/ip

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
## /open/test/rateLimiter/参数限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/test/rateLimiter/param

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
## /open/test/rateLimiter/用户名限流策略
```text
暂无描述
```
#### 接口状态
> 已完成

#### 接口URL
> http://localhost:6666/summer-single/test/rateLimiter/username

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