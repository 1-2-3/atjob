# mybatis migrations ��װ��ʹ��

## ��װ

- �� `https://github.com/mybatis/migrations/releases` �������°沢��ѹ����
- �����ѹ������ļ���Ϊ `mybatis-migrations-3.3.9`���� `mybatis-migrations-3.3.9` ��������·����ӵ��������� `MIGRATIONS_HOME`���� `%MIGRATIONS_HOME%\bin` ��ӵ��������� `PATH` �С�

### ʹ�� Docker ����

Docker ���ÿɲο� `docker-compose.deploy.yml` �е� `migrations` �ڵ㡣`command` ��ֱ��д����������ɣ����� `command: info` ��ʾ��ִ�� `docker-compose up migrations` ʱ����������ִ�� `migrate info`

## ʹ��

### ��ʼ��

1. ����ĿĿ¼�д���һ���ļ��� `mybatis-migrations`��
2. �� `mybatis-migrations` Ŀ¼��ִ�У�

```bash
migrate init
```

�������������Ŀ¼�������ļ���

#### ./Drivers �ļ���

�� `ojdbc8-12.2.0.1.jar` ���Ƶ� `drivers` �ļ��С�

#### ./environments �ļ���

�� `development.properties` ����ʱ�������ݿ�����������ȵȡ�

> �� 3.3.6 �濪ʼ���û�����ͨ������������ϵͳ��������д�������á����磬����������һ���������� MIGRATIONS_PASSWORD���������ڻ����ļ���д�����ݿ����롣

> ����� `MySQL` ���ݿ⣬`Send_full_script` ��������Ϊ `false` ����ִ�а���������Ľű���

#### ./scripts �ļ���

���Ŀ¼��������Ǩ�� SQL �ļ�����Щ�ļ��������������ͽ������ݿ�ṹ�� DDL��Ĭ������£���Ŀ¼���������������־��Ľű����Լ�һ���յ�Ǩ��ʾ���ű���Ҫ����һ���µ�Ǩ�ƽű�����ʹ�� `new` ���Ҫ�������й����Ǩ�ƣ���ʹ�� `up` ���Ҫ�����ϴ�Ӧ�õ�Ǩ�ƣ�����ʹ�� `down` ����ȡ�

### ����

#### info

`migrate info` �ɲ鿴ϵͳ��Ϣ������ֵ���ƣ�

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

`migrate bootstrap` ����ִ�� `scripts/bootstrap.sql`������һ�������ڰ汾����֮��Ľű���ִ����������� `CHANGELOG` ���޷���ִֹ�� 2 �Σ�Ҳ�޷�������ֻ�����ڴ��� `CHANGELOG` ��֮ǰִ����������ָ�� `--force` ��������

#### status

`migrate status` ��ʾ��ǰ���ݿ�״̬���ڵ�һ��ִ�� `migrate up` ǰִ��������ʾ��

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

��һ��ִ�� `migrate up` ��ִ�� `20210527122159_create_changelog.sql` �� `20210527122200_first_migration.sql`

֮����ִ�� `migrate status` ����ʾ��

```log
migrations_1   | ------------------------------------------------------------------------
migrations_1   | -- MyBatis Migrations - status
migrations_1   | ------------------------------------------------------------------------
migrations_1   | ID             Applied At          Description
migrations_1   | ================================================================================
migrations_1   | 20210527122159 2021-05-29 16:46:50 create changelog
migrations_1   | 20210527122200 2021-05-29 16:46:50 first migration
```

�� `CHANGELOG` ���е�������һ�µģ�

| ID             | APPLIED_AT          | DESCRIPTION      |
| -------------- | ------------------- | ---------------- |
| 20210527122159 | 2021-05-29 16:46:50 | create changelog |
| 20210527122200 | 2021-05-29 16:46:50 | first migration  |

**ע��** `migrate up` �����һ·�����ݿ����������°档���ִ��ǰ���ݿ�״̬�� `scripts` �ļ�����Ľű��ͼ����汾��������ִ��`scripts` �ļ������δִ�й��Ľű����� `migrate down` ������һ�ν��� 1 ���汾��

#### new

ִ�� `migrate new` �ᴴ��һ���µĽű��ļ���

```bash
migrate new "create blog table"
```

����������� `scripts` �ļ����ﴴ�����ļ�����Ϊ `20210530123819_create_blog_table.sql`���ļ�����һ���»���ǰ��Ĳ�����ʱ������ȿ��Ա�֤Ψһ������һ������İ汾�š���ʱ���Ҳ����Ϊ `CHANGELOG` ��� `ID` ��ֵ��

`20210530123819_create_blog_table.sql` �ļ��е� `-- //@UNDO` ����� sql ���ᱻ `up` ����ִ�У�`-- //@UNDO` ����� sql ���ᱻ `down` ����ִ�С�

**ע��**

1. ������� 2 �� `migrate new "create blog table"` ����ᴴ�� 2 ����ͬʱ����� sql �ļ���
2. `development.properties` �����ļ���� `time_zone=GMT+8:00` ���û�Ӱ�� `migrate new` ������ sql �ļ���ʱ�����������Ӱ�� `migrate up` �������ɵ� `CHANGELOG` ��� `APPLIED_AT` �ֶε�ֵ��`APPLIED_AT` ȡ��Ҳ�������ݿ������ʱ�䣬���Բ���ֱ���� `docker-compose.deploy.yml` ������ `migrations` ����ʹ�� `image`�����Ǵ����� `Dockerfile`������ `Dockerfile` ��������ʱ����

#### version

`migrate version` ���Խ����ݿ���Ϊ��Ҫ������һ���汾������ `migrate version 20210527122200` ��һֱ������ `first migration` �Ǹ��汾��

#### pending

������Э��ʱ���ᷢ��ĳ�˴������ύ��һ��ʱ����������ݿ⵱ǰ�汾����δִ�й��� sql �ű����������ʱִ�� `migrate up` �����ִ�д˽ű���ʹ�� `migrate status` ������Բ鿴���ýű����� `pending` ״̬��

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

��ʱ��Ҫ���Ӵ˽ű��Ƿ������Ľű��г�ͻ��Ȼ�����±��Žű����������ýű����ڰ汾����ִ�� `migrate up` ���������ָýű�����Ƚ϶���������ֻ�Ǵ�����һ������Ҳ����ʹ�� `migrate pending` ����ֱ��ִ�д˽ű���

#### script

������ɻ����޷���װ mybatis migrations����ʹ�� `migrate script` �������ɴ�һ���汾����������һ���汾�ŵ������ű���

���磺

```bash
migrate script 20210527122200 20210530123819 > do.sql
```

���ɸ߰汾���Ͱ汾�� undo �ű���

```bash
migrate script 20210530123819 20210527122200 > undo.sql
```

**ע��** `migrate script 20210527122200 20210530123819` ���ɵĽű����ǲ����� `20210527122200` ����汾�� sql �ű�������ݵġ�

Ҫ�����ɰ���ȫ���ű����ļ������ԣ�

```bash
migrate script 0 20210530123819 > do.sql
```

�� `docker-compose.deploy.yml` �� `command` д `migrate script 20210527122200 20210530123819 > do.sql` �ᱨ�� `The script command requires a range of versions from v1 - v2.`����Ϊ���� `command` ������ݶ�����Ϊ `migrate` �Ĳ��������Ŀǰ��û���ҵ��õĽ��������

### �洢���̻���ͼ

��Ҫ�� sql �ű��д����洢���̻���ͼʱ������������ `development.properties` ������ `delimiter=;`��`mybatis-migrations` ��ǳ�ɵ��һ���� `;` ����Ϊ�ű��������� `;` �� sql ��䷢�͵����ݿ���ִ�У�����ִ�еĴ洢���̴����ǲ������Ķ�����

��������ͨ��ʹ�� `-- @DELIMITER` ��������ע�Ͷ�̬�޸ķָ����������

1. �ڴ洢���̿�ʼǰ��ʹ�� `-- @DELIMITER $$$$$` �����ڴ洢�����ڲ����ֵ� `;` ������ǰִ�� sql ���롣
2. �ڴ洢���̽�����ʹ�� `-- @DELIMITER /` ���ָ����޸�Ϊ`/`��Ȼ������һ�����һ�� `/` ��ִ�� sql ���롣
3. ֮����ʹ�� `-- @DELIMITER ;` �ѷָ����ָ��� `;`.

֮���Բ��ڴ洢���̿�ʼǰ��ʹ�� `/` ��Ϊ�ָ���������Ϊ����洢���̴����������� `4 / 3` �����Ĵ���Ҳ�Ὣ `����` ����Ϊ�Ƿָ�����

�������������£�

```sql
-- // �洢����1
-- Migration SQL that makes the change goes here.

-- @DELIMITER $$$$$
create or replace procedure TEST_�ֵ��_INSERT(����_IN in varchar2, ����_IN in varchar2) is
v_���� varchar2(200);
begin
  select 'aaa' into v_���� from dual;
  select to_char(4 / 2) into v_���� from dual;
end TEST_�ֵ��_INSERT;
-- @DELIMITER /
/
-- @DELIMITER ;


-- @DELIMITER $$$$$
create or replace procedure TEST_�ֵ��_UPDATE(����_IN in varchar2, ����_IN in varchar2) is
v_���� varchar2(200);
begin
  select 'aaa' into v_���� from dual;
end TEST_�ֵ��_UPDATE;
-- @DELIMITER /
/
-- @DELIMITER ;

-- //@UNDO
-- SQL to undo the change goes here.

drop procedure TEST_�ֵ��_INSERT;
drop procedure TEST_�ֵ��_UPDATE;
```

## ʵս����

### ����������

1. ÿ��������Ա���������ݿ⡣�������Ա���������ݱ������˸��Ķ���ɻ��ң��Լ��������ݿ�������˴�����δ�����Ӧ���޸�ʱ��������⣬�������Ч�ʡ������˶����ݿ�ı������ͨ����ȡ���µ� script �ű����򵥵�ִ�� `migrate up` ��Ӧ�õ��Լ������ݿ��С���Ȼ�����Ĳ���/��ʾ���ݿ�Ҳ�Ǳ���ģ���Щ���ݿ����ͨ������ CI ���Զ���������
2. ���ݿ�����׷�ݡ����ݿ�����λ������Աʲôʱ��������Щ�ı䶼��׷�ݡ���Ҫ��ʱ�򻹿��Իع���
3. ���ڲ��������ű�������������ݿ��Ѿ������µģ���ִ�������ű������ᱨ����Ҳ��Ϊʲô������ά��Ա�������ű���������δ�����Եġ��������ǿ����������������ȣ�ʹ�� `migrate script �汾B �汾D > do.sql` ���ɴ� `�汾B` �� `�汾D` �������ű���Ȼ��ʹ�� `migrate version �汾B` �����Կ⽵���� `�汾B`��Ȼ�����ִ�� `do.sql`����Ȼ�����ר�����ڲ��Ե����ݿ⣬���򽵼�������ɾ�������ʱ�����������ݲ�һ�¡�

### ���ɻ�����

1. ��ǰ���ݿ�汾����ȷ�����������������Ȳ鿴��ǰ���ݿ�汾�Ƿ�������汾��ƥ�䡣
2. ���ݿ�����׷�ݡ����Բ鿴ʲôʱ��Ӧ�����ĸ�����ű���
3. �ع����岻��������Σ�ա�һ����˵���ݿ������Ǿ�����ǰ���ݡ������б������������ı��������ع����岻�����������ά���Ժ���ִ���� `migrate down` ����п�����ɾ����������������Ҳû��Ҫ��װ `mybatis migrations` �ˣ������ɿ�����Աʹ�� `migrate script` ���������ű��ȽϺá�
