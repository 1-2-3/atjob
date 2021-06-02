# mybatis migrations 安装和使用

## 安装

- 从 `https://github.com/mybatis/migrations/releases` 下载最新版并解压缩。
- 假设解压缩后的文件夹为 `mybatis-migrations-3.3.9`。将 `mybatis-migrations-3.3.9` 所在完整路径添加到环境变量 `MIGRATIONS_HOME`，将 `%MIGRATIONS_HOME%\bin` 添加到环境变量 `PATH` 中。

### 使用 Docker 镜像

Docker 配置可参考 `docker-compose.deploy.yml` 中的 `migrations` 节点。`command` 中直接写命令参数即可，例如 `command: info` 表示在执行 `docker-compose up migrations` 时会在容器中执行 `migrate info`

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

### 命令

#### info

`migrate info` 可查看系统信息。返回值类似：

```log
migrations_1   | OpenJDK 64-Bit Server VM warning: ignoring option PermSize=128m; support was removed in 8.0
migrations_1   | ------------------------------------------------------------------------
migrations_1   | -- MyBatis Migrations - info
migrations_1   | ------------------------------------------------------------------------
migrations_1   | null 3.3.6-SNAPSHOT (null)
migrations_1   | Java version: 1.8.0_181, vendor: Oracle Corporation
migrations_1   | Java home: /usr/lib/jvm/java-8-openjdk-amd64/jre
migrations_1   | Default locale: en_null, platform encoding: UTF-8
migrations_1   | OS name: "Linux", version: "4.19.84-microsoft-standard", arch: "amd64", family: "unix"
migrations_1   | ------------------------------------------------------------------------
migrations_1   | -- MyBatis Migrations SUCCESS
migrations_1   | -- Total time: 0s
migrations_1   | -- Finished at: Sat May 29 16:32:58 UTC 2021
migrations_1   | -- Final Memory: 5M/479M
migrations_1   | ------------------------------------------------------------------------
```

#### bootstrap

`migrate bootstrap` 将会执行 `scripts/bootstrap.sql`。这是一个游离于版本控制之外的脚本，执行它不会记入 `CHANGELOG` 表，无法防止执行 2 次，也无法撤销。只允许在创建 `CHANGELOG` 表之前执行它（除非指定 `--force` 参数）。

#### status

`migrate status` 显示当前数据库状态。在第一次执行 `migrate up` 前执行它会显示：

```log
migrations_1   | ------------------------------------------------------------------------
migrations_1   | -- MyBatis Migrations - status
migrations_1   | ------------------------------------------------------------------------
migrations_1   | ID             Applied At          Description
migrations_1   | ================================================================================
migrations_1   | 20210527122159    ...pending...    create changelog
migrations_1   | 20210527122200    ...pending...    first migration
```

#### up,down

第一次执行 `migrate up` 会执行 `20210527122159_create_changelog.sql` 和 `20210527122200_first_migration.sql`

之后再执行 `migrate status` 会显示：

```log
migrations_1   | ------------------------------------------------------------------------
migrations_1   | -- MyBatis Migrations - status
migrations_1   | ------------------------------------------------------------------------
migrations_1   | ID             Applied At          Description
migrations_1   | ================================================================================
migrations_1   | 20210527122159 2021-05-29 16:46:50 create changelog
migrations_1   | 20210527122200 2021-05-29 16:46:50 first migration
```

与 `CHANGELOG` 表中的数据是一致的：

| ID             | APPLIED_AT          | DESCRIPTION      |
| -------------- | ------------------- | ---------------- |
| 20210527122159 | 2021-05-29 16:46:50 | create changelog |
| 20210527122200 | 2021-05-29 16:46:50 | first migration  |

**注意** `migrate up` 命令会一路将数据库升级至最新版。如果执行前数据库状态比 `scripts` 文件夹里的脚本低几个版本，会连续执行`scripts` 文件夹里的未执行过的脚本。而 `migrate down` 命令是一次降级 1 个版本。

#### new

执行 `migrate new` 会创建一个新的脚本文件：

```bash
migrate new "create blog table"
```

上述命令会在 `scripts` 文件夹里创建新文件，名为 `20210530123819_create_blog_table.sql`。文件名第一个下划线前面的部分是时间戳，既可以保证唯一，又是一个有序的版本号。该时间戳也会作为 `CHANGELOG` 表的 `ID` 的值。

`20210530123819_create_blog_table.sql` 文件中的 `-- //@UNDO` 上面的 sql 语句会被 `up` 命令执行；`-- //@UNDO` 下面的 sql 语句会被 `down` 命令执行。

**注意**

1. 如果运行 2 次 `migrate new "create blog table"` 命令，会创建 2 个不同时间戳的 sql 文件。
2. `development.properties` 配置文件里的 `time_zone=GMT+8:00` 配置会影响 `migrate new` 创建的 sql 文件的时间戳，但不会影响 `migrate up` 命令生成的 `CHANGELOG` 表的 `APPLIED_AT` 字段的值。`APPLIED_AT` 取的也不是数据库服务器时间，所以不能直接在 `docker-compose.deploy.yml` 中配置 `migrations` 服务使用 `image`，而是创建了 `Dockerfile`，并在 `Dockerfile` 中配置了时区。

#### version

`migrate version` 可以将数据库置为想要的任意一个版本。例如 `migrate version 20210527122200` 将一直降级到 `first migration` 那个版本。

#### pending

当多人协作时，会发生某人创建并提交了一个时间戳晚于数据库当前版本但尚未执行过的 sql 脚本的情况。此时执行 `migrate up` 命令不会执行此脚本。使用 `migrate status` 命令可以查看到该脚本处于 `pending` 状态。

```log
migrations_1   | ------------------------------------------------------------------------
migrations_1   | -- MyBatis Migrations - status
migrations_1   | ------------------------------------------------------------------------
migrations_1   | ID             Applied At          Description
migrations_1   | ================================================================================
migrations_1   | 20210527122159 2021-05-29 16:46:50 create changelog
migrations_1   | 20210527122200 2021-05-29 16:46:50 first migration
migrations_1   | 20210530113819    ...pending...    create post table
migrations_1   | 20210530123819 2021-05-30 13:46:15 create blog table
```

此时需要检视此脚本是否与其后的脚本有冲突。然后重新编排脚本，降级到该脚本所在版本，再执行 `migrate up` 命令。如果发现该脚本本身比较独立（例如只是创建了一个表），也可以使用 `migrate pending` 命令直接执行此脚本。

#### script

如果生成环境无法安装 mybatis migrations，可使用 `migrate script` 命令生成从一个版本号升级到另一个版本号的升级脚本。

例如：

```bash
migrate script 20210527122200 20210530123819 > do.sql
```

或由高版本到低版本的 undo 脚本：

```bash
migrate script 20210530123819 20210527122200 > undo.sql
```

**注意** `migrate script 20210527122200 20210530123819` 生成的脚本里是不包含 `20210527122200` 这个版本的 sql 脚本里的内容的。

要想生成包含全部脚本的文件，可以：

```bash
migrate script 0 20210530123819 > do.sql
```

在 `docker-compose.deploy.yml` 的 `command` 写 `migrate script 20210527122200 20210530123819 > do.sql` 会报错 `The script command requires a range of versions from v1 - v2.`。因为整个 `command` 里的内容都会作为 `migrate` 的参数。这个目前还没有找到好的解决方法。

### 存储过程或视图

想要在 sql 脚本中创建存储过程或视图时，由于我们在 `development.properties` 配置了 `delimiter=;`，`mybatis-migrations` 会非常傻的一遇到 `;` 就认为脚本结束并把 `;` 的 sql 语句发送到数据库中执行，导致执行的存储过程代码是不完整的而报错。

这个问题可通过使用 `-- @DELIMITER` 这个特殊的注释动态修改分隔符来解决。

1. 在存储过程开始前，使用 `-- @DELIMITER $$$$$` 避免在存储过程内部出现的 `;` 导致提前执行 sql 代码。
2. 在存储过程结束后，使用 `-- @DELIMITER /` 将分隔符修改为`/`，然后在下一行输出一个 `/` 来执行 sql 代码。
3. 之后再使用 `-- @DELIMITER ;` 把分隔符恢复成 `;`.

之所以不在存储过程开始前就使用 `/` 做为分隔符，是因为如果存储过程代码里有类似 `4 / 3` 这样的代码也会将 `除号` 误认为是分隔符。

完整的例子如下：

```sql
-- // 存储过程1
-- Migration SQL that makes the change goes here.

-- @DELIMITER $$$$$
create or replace procedure TEST_字典表_INSERT(名称_IN in varchar2, 编码_IN in varchar2) is
v_名称 varchar2(200);
begin
  select 'aaa' into v_名称 from dual;
  select to_char(4 / 2) into v_名称 from dual;
end TEST_字典表_INSERT;
-- @DELIMITER /
/
-- @DELIMITER ;


-- @DELIMITER $$$$$
create or replace procedure TEST_字典表_UPDATE(名称_IN in varchar2, 编码_IN in varchar2) is
v_名称 varchar2(200);
begin
  select 'aaa' into v_名称 from dual;
end TEST_字典表_UPDATE;
-- @DELIMITER /
/
-- @DELIMITER ;

-- //@UNDO
-- SQL to undo the change goes here.

drop procedure TEST_字典表_INSERT;
drop procedure TEST_字典表_UPDATE;
```

## 实战意义

### 开发环境下

1. 每个开发人员独立的数据库。这样可以避免测试数据被其他人更改而造成混乱，以及避免数据库变更而后端代码尚未完成相应的修改时报错的问题，极大提高效率。其他人对数据库的变更可以通过获取最新的 script 脚本并简单的执行 `migrate up` 来应用到自己的数据库中。当然独立的测试/演示数据库也是必须的，这些数据库可以通过配置 CI 来自动化升级。
2. 数据库变更可追溯。数据库由哪位开发人员什么时候做了哪些改变都可追溯。必要的时候还可以回滚。
3. 便于测试升级脚本。如果测试数据库已经是最新的，再执行升级脚本往往会报错，这也是为什么交给运维人员的升级脚本往往都是未经测试的。现在我们可以这样来做：首先，使用 `migrate script 版本B 版本D > do.sql` 生成从 `版本B` 到 `版本D` 的升级脚本。然后使用 `migrate version 版本B` 将测试库降级到 `版本B`。然后测试执行 `do.sql`。当然最好有专门用于测试的数据库，否则降级操作有删除表操作时容易引起数据不一致。

### 生成环境下

1. 当前数据库版本很明确。如果出现问题可以先查看当前数据库版本是否与软件版本相匹配。
2. 数据库变更可追溯。可以查看什么时间应用了哪个变更脚本。
3. 回滚意义不大甚至很危险。一般来说数据库变更都是尽量向前兼容。除非有表名或者列名的变更，否则回滚意义不大。甚至如果运维犯迷糊误执行了 `migrate down` 命令还有可能误删除表。所以生产环境也没必要安装 `mybatis migrations` 了，还是由开发人员使用 `migrate script` 生成升级脚本比较好。
