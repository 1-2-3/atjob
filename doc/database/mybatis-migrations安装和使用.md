# mybatis migrations 安装和使用

## 安装

- 从 `https://github.com/mybatis/migrations/releases` 下载最新版并解压缩。
- 假设解压缩后的文件夹为 `mybatis-migrations-3.3.9`。将 `mybatis-migrations-3.3.9` 所在完整路径添加到环境变量 `MIGRATIONS_HOME`，将 `%MIGRATIONS_HOME%\bin` 添加到环境变量 `PATH` 中。

## 使用

### 初始化

1. 在项目目录中创建一个文件夹 `mybatis-migrations`。
2. 在 `mybatis-migrations` 目录下执行：

```bash
migrate init
```

此命令将创建若干目录和配置文件。

#### ./Drivers 文件夹

将 `ojdbc8-12.2.0.1.jar` 复制到 `drivers` 文件夹。

#### ./environments 文件夹

在 `development.properties` 配置时区、数据库驱动和密码等等。

> 从 3.3.6 版开始，用户可以通过环境变量或系统属性来重写环境设置。例如，您可以声明一个环境变量 MIGRATIONS_PASSWORD，而不是在环境文件中写入数据库密码。

> 如果是 `MySQL` 数据库，`Send_full_script` 必须设置为 `false` 才能执行包含多个语句的脚本。

#### ./scripts 文件夹

这个目录包含您的迁移 SQL 文件。这些文件包含用于升级和降级数据库结构的 DDL。默认情况下，该目录将包含创建变更日志表的脚本，以及一个空的迁移示例脚本。要创建一个新的迁移脚本，请使用 `new` 命令。要运行所有挂起的迁移，请使用 `up` 命令。要撤销上次应用的迁移，可以使用 `down` 命令等。

## bootstrap 命令
