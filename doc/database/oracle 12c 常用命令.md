# Oracle 12c 常用命令

## 数据库管理

以下所有命令需要先使用 `docker exec -it <已启动的 oracle 容器id> bash` 进入容器，再执行 `sqlplus / as sysdba` 后执行。

### 查看所有数据库服务

通过 `v$services` 视图的 `NAEM`属性可查到服务名，此服务名在其他终端连接数据库时需要用到。

```plsql
select * from v$services
```

### 查看所有 pdb

```plsql
select name,open_mode from v$pdbs;
```

### 切换到 pdb

```plsql
alter session set container=ORCLPDB1;
```

### 查看当前连接的数据库

```plsql
show con_name;
```

### 创建用户

先切换到 `ORCLPDB1`，再执行：

```plsql
create user atjob identified by atjob default tablespace USERS temporary tablespace TEMP;
-- Grant/Revoke role privileges 
grant dba to atjob;
-- Grant/Revoke system privileges 
grant unlimited tablespace to atjob;
```

### 测试登录

```bash
sqlplus atjob/atjob@ORCLPDB1
```

## 日常操作

在宿主机上通过 `plsql developer` 登录 `atjob`

```
用户名: atjob
密码：atjob
数据库：127.0.0.1/ORCLPDB1.localdomain
```







以上
