# atjob

领域驱动设计承诺的弹性、自治和整洁是理想，大部分系统仍在使用事务脚本和贫血模型是现实。团队面临的问题往往不是“是否应该使用领域驱动设计”，而是“除事务脚本之外根本没有其他选择”。当开发团队考虑是否引入领域驱动设计时，会担心同时引入大量的概念和争论，开发人员变得无所适从，以及成本大幅上升。实事果真如此吗？

模型复杂度应与领域（业务）复杂度相匹配。事务脚本只提供一种无状态的Service封装所有的业务逻辑，这种极简的模型无法适配比较复杂的业务。当遇到复杂业务时，必将面对“是否允许一个Service调用另一个Service？”这种无解的问题。另一方面，大部分的业务复杂度无需引入最复杂的领域设计实现。我们需要一种可以渐进地引入领域驱动设计的方法，而不去争论“这不是领域驱动设计……”。

`atjob`项目尝试使用主流开源组件以尽可能低的成本实现领域驱动设计中的核心概念，包括：实体、值对象、聚合、资源库、领域事件等。

## 特性

- 开箱即用的RBAC权限管理前后端实现。

- Docker 一键启动。

## 前端技术选型

前端使用 Angular 和 [NgZorro](https://github.com/NG-ZORRO/ng-zorro-antd) 组件库。将来会增加 VUE 和 React 的实现。

## 后端技术选型

- 后端基于 Java11 和 [Spring boot](https://spring.io/projects/spring-boot) 。虽然 .net 框架的C#语言很漂亮，IDE无敌的方便，但微服务的生态与Java差距不小。Spring boot的出现极大的简化了Java的架构难度。如果你是一个资深 .net 程序员，现在转 Java 只需一个月！

- ORM 使用 [Mybatis Plus](https://github.com/baomidou/mybatis-plus) 。既可以享受 Mybatis 的灵活、轻量，又无需管理大量 XML 配置，还支持 lambda，比直接用 Mybatis 简洁优雅太多。

- [lombok](https://github.com/rzwitserloot/lombok) 不仅可以让代码变得更加简洁，它的 @Value 注解还可以表示类为一个值对象。记得先安装 lombok 针对你使用的 IDE 的插件。

- 领域事件使用Spring框架的 `ApplicationEventPublisher`实现，这是最简单的方式。

- 鉴权使用了 [shiro](https://github.com/apache/shiro) 框架很小的一部分功能。

- 数据库版本管理使用 [Flyway](https://github.com/flyway/flyway) 。但它的社区版只支持各数据库最新的几个版本。目前把它当做自动执行建库脚本的工具就好。

## 起步

1. [在 Win10 下安装并配置 Docker](doc/docker/在 Win10 下安装并配置 Docker.md).

2. Clone 本项目并在项目根目录下执行 `docker-compose up -d`
   
   第一次执行会下载包括 Oracle 数据库等 Docker 镜像，如果你在第一步正确配置了镜像源，应该不会耗费太多时间。在下载镜像之后还要进行数据库初始化、下载前后端依赖包、编译等任务，会耗费一些时间。如果想查看详细的运行日志，可使用如下命令：
   
   - 数据库：`docker-compose logs -f db`
   
   - 数据库创建用户：`docker-compose logs -f flyway-init`
   
   - 数据库表结构和初始数据：`docker-compose logs -f flyway`
   
   - 后端服务：`docker-compose logs -f backend`
   
   - 前端：`docker-compose logs -f frontend`
     
     `-f` 参数意为 `follow` ，会使命令处于阻塞状态实时刷新最新日志输出，你可以随时按 `ctrl+c` 退出命令而不会终止容器的运行。
   
   启动完成后即可访问如下地址：
   
   - 前端： http://localhost:9900
   
   - 后端Swagger文档： http://localhost:9003/swagger-ui.html

## 日常开发

- [后端日常开发](doc/backend/后端日常开发.md)。

- [前端日常开发](doc/frontend/前端日常开发.md)。



## License

MIT
