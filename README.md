# Summer

## 介绍
### 基于SpringBoot2.7的轻量级权限管理系统（后端）
高质量的Java代码、分包结构和代码注释，致力于为中小企业提供一套代码规范，注释详尽，文档齐全的应用系统模板，
让大家在这浮躁的代码世界里感受一股把代码写好的清流！

## 主要特性:
- 丰富的个性化配置项和工具集，代码风格简洁统一
- 注释详尽易懂，提供完整的JavaDoc、ApiPost和PDManer文档
- 异常/日志统一规范并做精细化处理
- 提供基于Shiro+Oauth2的轻量级RBAC架构实现
- 提供基于Quartz的轻量级定时调度实现，集群部署时可避免重复执行
- Hutool工具类库深度实践，全项目统一规范工具使用
- 自定义缓存服务api，支持多级缓存，单机部署时可脱离Redis使用
- 可扩展接口限流功能支持，自带多种基本限流策略
- 标准国际化支持，包含异常消息/校验消息的国际化实现
- EasyExcel可扩展工具封装，一行代码即可实现导入导出功能
- MybatisPlus非侵入式封装，提供基于Mp插件的无感式过滤数据权限实现
- Controller层代码标准化处理，不包含任何业务逻辑和受检异常抛出
- Lambda+StreamAPI函数式编码风格实践
- 以上只是沧海一粟，更多细节等待你的发现

## 项目结构
### 模块关系
```
Summer
└─summer-admin -- 管理模块
     └─summer-system -- 系统模块
          └─summer-core -- 核心模块
```

### 目录说明
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
│         └─mapper        业务模块Mapper.xml
│
├─summer-system      系统模块（专注于系统基本功能支持）
│    └─system 
│    │    ├─dictionary    字典模块
│    │    ├─handler       处理器定义
│    │    ├─hook          SpringBoot钩子
│    │    ├─locale        国际化模块
│    │    ├─log           日志模块
│    │    ├─monitor       监控模块
│    │    ├─schedule      定时调度模块
│    │    └─security      权限管理模块
│    │
│    └─resources 
│         └─mapper        系统模块Mapper.xml
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
│    │    ├─properties    自定义属性配置
│    │    ├─ratelimiter   接口限流支持
│    │    ├─utils         工具封装支持
│    │    └─validation    对象校验支持
│    │
│    └─resources 
│         ├─custom        自定义配置
│         └─i18n          国际化配置
```

### 依赖清单
- SpringBoot：2.7.11
- MysqlConnector：8.0.33
- DynamicDatasource：3.6.1
- MybatisPlus：3.5.3.1
- P6spy：3.9.1
- Lombok：1.18.26
- Shiro：1.11.0
- Quartz：2.3.2
- CommonsPool2：2.11.1
- Guava：31.1-jre
- Hutool：5.8.18
- EasyExcel：3.2.1
- MicaXss：2.7.11

### 功能支持
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
|  字典管理 | 自开发    |   🏗          |  字典相关接口                                     |
|  行政区域 | 自开发    |   🏗          |  行政区域相关接口                                     |
|  业务模块Demo | 自开发    |   🏗          |  业务模块Demo相关接口                                     |
|  对象存储 | 第三方SDK    |   🏗          |  对象存储相关接口                                     |
|  短信管理 | SMS4J    |   🏗          |  短信相关接口                                     |
|  邮件管理 | 自开发    |   🏗          |  邮件相关接口                                     |
|  消息通知 | 自开发    |   🏗          |  消息通知相关接口                                     |
|  api日志 | 自开发    |   🏗          |  api日志相关接口                                     |
|  代码生成 | 自开发    |   🏗          |  代码生成相关接口                                     |
|  用户注册 | 自开发    |   🏗          |  用户注册相关接口                                     |
|  流程管理 | LiteFlow    |   🏗          |  流程相关接口                                     |

## 快速开始
环境需求
- Jdk1.8
- Maven3.0+
- Mysql5.7+

核心技术：Springboot2.7.x、Hutool5.8.x、MybatisPlus3.5.x
- [SpringBoot文档](https://spring.io/projects/spring-boot#learn)
- [Hutool文档](https://hutool.cn/docs/#/)
- [MybatisPlus文档](https://baomidou.com/pages/24112f/) 

文档工具：ApiPost7.x、PDManer4.x
- [ApiPost文档](https://v7-wiki.apipost.cn/)
- [PDManer文档](https://www.yuque.com/pdmaner/docs/pdmaner-manual)

## 学习交流
如果觉得还不错麻烦点个Star支持作者~

- Email：xiaJhuan@163.com
- Wechat：ASDA-xiajhuan

## 参与贡献
本项目还处于孵化阶段，暂不接受PR，欢迎各类Feature Issue/Bug Issue

### Commit规范
- feat: 新功能
- fix: 修复 Bug
- docs: 文档修改
- perf: 性能优化
- revert: 版本回退
- ci: CICD 集成相关
- test: 添加测试代码
- refactor: 代码重构
- build: 影响项目构建或依赖修改
- style: 不影响程序逻辑的代码修改
- chore: 不属于以上类型的其他类型(日常事务)