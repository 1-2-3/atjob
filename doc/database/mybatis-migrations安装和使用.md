# mybatis migrations ��װ��ʹ��

## ��װ

- �� `https://github.com/mybatis/migrations/releases` �������°沢��ѹ����
- �����ѹ������ļ���Ϊ `mybatis-migrations-3.3.9`���� `mybatis-migrations-3.3.9` ��������·����ӵ��������� `MIGRATIONS_HOME`���� `%MIGRATIONS_HOME%\bin` ��ӵ��������� `PATH` �С�

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

## bootstrap ����
