## 📚简介
Summer是一套基于SpringBoot2.7的轻量级权限管理系统，具有项目结构简单、文档类别齐全、代码规范易读、开发门槛低等特点，它提供相对完善的基础设施（内置常用功能实现和上百个通用模块接口），为中小团队或个人开发者提供开箱即用的业务开发平台

## 🍺特性
区别于市面上绝大多数权限管理系统，本项目具有：
- 完善但不赘余的代码注释，可生成标准美观的Javadoc文档
- 常用设计模式的应用实践，功能实现更加简洁优雅、侵入性低且扩展性强
- 精细化的日志和异常处理，能有效帮助你过滤无用信息、快速定位系统异常
- 丰富的个性化配置项和注释说明，简单修改配置即可定制不同的系统需求
- 干净整洁的Controller层代码，不包含任何业务逻辑和受检异常抛出
- 变量方法命名规范且注重编码细节，基本避免冗余代码和IDE警告信息
- 使用专业工具（ApiPost/PDManer）构建项目文档，更加方便团队管理和交付使用

## 🛠️项目结构
### 📙模块关系
```
Summer
└─summer-admin -- 管理模块
     └─summer-system -- 系统模块
          └─summer-core -- 核心模块
```

### 📘目录说明
```
Summer
├─docs               文档
│    ├─apipost       接口文档（使用浏览器打开）
│    ├─javadoc       API文档（使用浏览器打开index.html）
│    ├─pdmaner       数据建模文档（使用PDManer4.x打开）
│    └─sql           Sql脚本
│    
├─summer-admin       管理模块（专注于实际业务开发）
│    └─admin 
│    │    ├─api           api模块
│    │    │    ├─open            开放接口
│    │    │    └─test            测试接口
│    │    ├─business      业务模块
│    │    └─task          定时任务模块
│    │         ├─api             api定时任务
│    │         └─business        业务定时任务
│    │
│    └─resources
│         └─mapper        业务模块 Mapper.xml
│
├─summer-system      系统模块（专注于系统基本功能支持）
│    └─system 
│    │    ├─dictionary    字典模块
│    │    ├─extend        扩展模块
│    │    ├─handler       处理器定义
│    │    ├─hook          SpringBoot钩子
│    │    ├─locale        国际化模块
│    │    ├─message       消息模块
│    │    ├─log           日志模块
│    │    ├─monitor       监控模块
│    │    ├─schedule      定时调度模块
│    │    └─security      权限管理模块
│    │
│    └─resources 
│         └─mapper        系统模块 Mapper.xml
│ 
├─summer-core        核心模块（专注于核心功能、工具集和配置项的封装支持）
│    └─core 
│    │    ├─base          基类定义
│    │    ├─cache         缓存支持
│    │    ├─config        系统基本配置
│    │    ├─constant      常量定义
│    │    ├─converter     转换器定义
│    │    ├─data          数据类定义
│    │    ├─enums         枚举定义
│    │    ├─excel         Excel支持
│    │    ├─exception     异常定义
│    │    ├─interceptor   拦截器定义
│    │    ├─mp            MybatisPlus支持
│    │    ├─oss           对象存储支持
│    │    ├─properties    自定义属性配置
│    │    ├─ratelimiter   接口限流支持
│    │    ├─utils         工具封装支持
│    │    └─validation    对象校验支持
│    │
│    └─resources 
│         └─i18n          国际化配置
```

### 📝依赖清单
- SpringBoot：2.7.13
- MysqlConnector：8.0.33
- DynamicDatasource：3.6.1
- MybatisPlus：3.5.3.1
- P6spy：3.9.1
- Lombok：1.18.28
- Shiro：1.11.0
- Quartz：2.4.0-rc2
- OkHttp：4.11.0
- Freemarker：2.3.32
- CommonsCompress：1.23.0
- CommonsPool2：2.11.1
- Guava：32.0.1-jre
- Hutool：5.8.20
- JavaxMail：1.6.2
- EasyExcel：3.3.2
- MicaXss：2.7.13
- MinIoSdk：8.5.4
- QiNiuSdk：7.13.1

## 🎁功能支持
|  服务     | 使用技术     |   进度         |    备注   |
|----------|-------------|---------------|-----------|
|  角色管理 | 自开发       |   ✅          |  角色相关接口，RBAC架构的核心   |
|  用户管理 | 自开发       |   ✅          |  用户相关接口          |
|  岗位管理 | 自开发       |   ✅         |  岗位相关接口        |
|  菜单管理 | 自开发       |   ✅          |  菜单相关接口，树形结构展现               |
|  部门管理 | 自开发    |   ✅          |  部门相关接口，树形结构展现                                    |
|  国际化名称 | 自开发    |   ✅          |  国际化名称相关接口                                     |
|  操作日志 | 自开发    |   ✅          |  操作日志相关接口                                     |
|  错误日志 | 自开发    |   ✅          |  错误日志相关接口                                     |
|  登录日志 | 自开发    |   ✅          |  登录日志相关接口                                     |
|  系统信息 | 自开发    |   ✅          |  系统信息相关接口                                     |
|  在线用户 | 自开发    |   ✅          |  在线用户相关接口                                     |
|  定时任务 | 自开发    |   ✅          |  定时任务相关接口                                     |
|  定时任务日志 | 自开发    |   ✅          |  定时任务日志相关接口                                     |
|  字典管理 | 自开发    |   ✅          |  字典相关接口                                     |
|  行政区域 | 自开发    |   ✅          |  行政区域相关接口，树形结构展现                                     |
|  对象存储 | 第三方Sdk    |   ✅          |  对象存储相关接口                                     |
|  邮件管理 | 自开发    |   ✅          |  邮件相关接口                                     |
|  邮件日志 | 自开发    |   ✅          |  邮件日志相关接口                                     |
|  消息通知 | 自开发    |   🏗          |  消息通知相关接口                                     |
|  代码生成 | 自开发    |   🏗          |  代码生成相关接口                                     |
|  api管理 | 自开发    |   🏗          |  api相关接口                                     |
|  api日志 | 自开发    |   🏗          |  api日志相关接口                                     |
|  用户注册 | 自开发    |   🏗          |  用户注册相关接口                                     |
|  流程管理 | Flowable    |   🏗          |  流程相关接口                                     |

## ⭐快速开始
### 📦环境需求
- Jdk1.8
- Maven3.0+
- Mysql5.7+

### 🍊核心技术
Springboot2.7.x、Hutool5.8.x、MybatisPlus3.5.x
- [SpringBoot文档](https://spring.io/projects/spring-boot#learn)
- [Hutool文档](https://hutool.cn/docs/#/)
- [MybatisPlus文档](https://baomidou.com/pages/24112f/) 

### 🍐文档工具
ApiPost7.x、PDManer4.x
- [ApiPost文档](https://v7-wiki.apipost.cn/)
- [PDManer文档](https://www.yuque.com/pdmaner/docs/pdmaner-manual)

## 👦学习交流
如果觉得还不错麻烦点个Star支持作者~

- Email：xiaJhuan@163.com
- Wechat：ASDA-xiajhuan

## 🧬参与贡献
- 欢迎提交PR，注意提交到dev分支
- 欢迎提交Issue，请注明使用版本、问题原因、重现步骤等
- 以gitee为主，github为辅

### 🔔️Commit规范
- feat：新功能
- fix：修复Bug
- docs：文档修改
- perf：性能优化
- revert：版本回退
- ci：CICD集成相关
- test：添加测试代码
- refactor：代码重构
- build：影响项目构建或依赖修改
- style：不影响程序逻辑的代码修改
- chore：不属于以上类型的其他类型（日常事务）

## 💳用户权益
- 任何个人、组织、企业可免费使用本项目代码，但请遵守开源协议保留原作者声明
- 授权协议：[MulanPSL-2.0](http://license.coscl.org.cn/MulanPSL2)