# summer-single

## 介绍
### 基于SpringBoot2.7的轻量级权限管理系统（后端）
### 主要特性:
1.  丰富的个性化配置项和工具集，代码风格简洁统一
2.  代码注释详尽易懂，并提供ApiPost接口/PDManer数据建模文档
3.  异常/日志统一精细化处理
4.  提供基于Shiro+Oauth2的轻量级RBAC架构实现
5.  Hutool工具类库深度实践，全项目统一规范工具使用
6.  自定义缓存服务api，支持多级缓存，单节点部署时可脱离Redis使用
7.  可扩展接口限流功能支持，自带多种基本限流策略
8.  标准国际化支持，包含异常消息/校验消息的国际化实现
9.  EasyExcel可扩展工具封装，一行代码即可实现导入导出功能
10. MybatisPlus非侵入式封装，并提供基于3.5.2+的新版数据权限实现
11. Lambda+StreamAPI函数式编码风格实践

## 软件架构
### 模块说明
```lua
summer-single
├── summer-admin -- 管理模块
├     ├── business -- 业务模块
├     ├── common -- 通用模块
├     ├── open -- 开放接口模块
└── summer-core -- 核心模块
```

## 快速开始
### 先决条件
首先本机先要安装以下环境，建议先学习了解Springboot、Hutool、MybatisPlus。
- [git](https://git-scm.com/)
- [java8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [maven](http://maven.apache.org/) 

## 学习交流
如果觉得还不错麻烦点个Star支持作者~

```lua
email：xiaJhuan@163.com
wx：ASDA-xiajhuan
```

## 参与贡献
本项目还处于孵化阶段，暂不支持PR,欢迎各类Feature Issue/Bug Issue
