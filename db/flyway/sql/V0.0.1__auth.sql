CREATE TABLE "AUTH_DEPT" (
  "DEPT_ID" VARCHAR2(36) NOT NULL ENABLE,
  "NAME" VARCHAR2(255),
  "CODE" VARCHAR2(255),
  "PARENT" VARCHAR2(36),
  "INPUT_CODE" VARCHAR2(255),
  "INDEX_FIELD" NUMBER(10, 0),
  "DESCRIPTION" VARCHAR2(255),
  "IS_STOP" NUMBER(1, 0),
  "CREATE_TIME" DATE,
  "CREATE_USER_ID" VARCHAR2(36),
  "MODIFY_USER_ID" VARCHAR2(36),
  "MODIFY_TIME" DATE,
  CONSTRAINT "PK_AUTH_DEPT" PRIMARY KEY ("DEPT_ID")
);

COMMENT ON COLUMN "AUTH_DEPT"."DEPT_ID" IS 'DEPT_ID';

COMMENT ON COLUMN "AUTH_DEPT"."NAME" IS '部门名称';

COMMENT ON COLUMN "AUTH_DEPT"."CODE" IS '部门编码';

COMMENT ON COLUMN "AUTH_DEPT"."PARENT" IS '父节ID';

COMMENT ON COLUMN "AUTH_DEPT"."INPUT_CODE" IS '录入码';

COMMENT ON COLUMN "AUTH_DEPT"."INDEX_FIELD" IS '排序';

COMMENT ON COLUMN "AUTH_DEPT"."DESCRIPTION" IS '描述';

COMMENT ON COLUMN "AUTH_DEPT"."IS_STOP" IS '是否停用';

COMMENT ON COLUMN "AUTH_DEPT"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "AUTH_DEPT"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "AUTH_DEPT"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "AUTH_DEPT"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "AUTH_DEPT" IS '部门';

CREATE TABLE "AUTH_USER" (
  "USER_ID" VARCHAR2(36) NOT NULL ENABLE,
  "DEPT_ID" VARCHAR2(36),
  "DEPT_NAME" VARCHAR2(255),
  "NAME" VARCHAR2(255),
  "CODE" VARCHAR2(255),
  "LOGIN_NAME" VARCHAR2(255),
  "PWD" VARCHAR2(255),
  "INPUT_CODE" VARCHAR2(255),
  "INDEX_FIELD" NUMBER(10, 0),
  "DESCRIPTION" VARCHAR2(255),
  "PHONE" VARCHAR2(255),
  "IS_STOP" NUMBER(1, 0),
  "CREATE_TIME" DATE,
  "CREATE_USER_ID" VARCHAR2(36),
  "MODIFY_USER_ID" VARCHAR2(36),
  "MODIFY_TIME" DATE,
  CONSTRAINT "PK_AUTH_USER" PRIMARY KEY ("USER_ID")
);

COMMENT ON COLUMN "AUTH_USER"."USER_ID" IS 'USER_ID';

COMMENT ON COLUMN "AUTH_USER"."DEPT_ID" IS 'DEPT_ID';

COMMENT ON COLUMN "AUTH_USER"."DEPT_NAME" IS '部门名称';

COMMENT ON COLUMN "AUTH_USER"."NAME" IS '用户显示名';

COMMENT ON COLUMN "AUTH_USER"."CODE" IS '用户编码';

COMMENT ON COLUMN "AUTH_USER"."LOGIN_NAME" IS '登录名';

COMMENT ON COLUMN "AUTH_USER"."PWD" IS '密码';

COMMENT ON COLUMN "AUTH_USER"."INPUT_CODE" IS '录入码';

COMMENT ON COLUMN "AUTH_USER"."INDEX_FIELD" IS '排序';

COMMENT ON COLUMN "AUTH_USER"."DESCRIPTION" IS '描述';

COMMENT ON COLUMN "AUTH_USER"."PHONE" IS '电话';

COMMENT ON COLUMN "AUTH_USER"."IS_STOP" IS '是否停用';

COMMENT ON COLUMN "AUTH_USER"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "AUTH_USER"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "AUTH_USER"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "AUTH_USER"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "AUTH_USER" IS '系统用户';

CREATE TABLE "AUTH_PAGE" (
  "PAGE_ID" VARCHAR2(36) NOT NULL ENABLE,
  "NAME" VARCHAR2(255),
  "CODE" VARCHAR2(255),
  "PATH" VARCHAR2(255),
  "PARENT" VARCHAR2(36),
  "IS_LEAF" NUMBER(1),
  "ICON" VARCHAR2(255),
  "IS_HIDE" NUMBER(1),
  "IS_STOP" NUMBER(1),
  "INDEX_FIELD" NUMBER(10),
  "CREATE_TIME" DATE,
  "CREATE_USER_ID" VARCHAR2(36),
  "MODIFY_USER_ID" VARCHAR2(36),
  "MODIFY_TIME" DATE,
  CONSTRAINT "PK_AUTH_PAGE" PRIMARY KEY ("PAGE_ID")
);

COMMENT ON COLUMN "AUTH_PAGE"."PAGE_ID" IS 'PAGE_ID';

COMMENT ON COLUMN "AUTH_PAGE"."NAME" IS '页面名称';

COMMENT ON COLUMN "AUTH_PAGE"."CODE" IS '页面编码';

COMMENT ON COLUMN "AUTH_PAGE"."PATH" IS '访问路径';

COMMENT ON COLUMN "AUTH_PAGE"."PARENT" IS '父节ID';

COMMENT ON COLUMN "AUTH_PAGE"."IS_LEAF" IS '是否叶';

COMMENT ON COLUMN "AUTH_PAGE"."ICON" IS 'ICON';

COMMENT ON COLUMN "AUTH_PAGE"."IS_HIDE" IS '是否隐藏';

COMMENT ON COLUMN "AUTH_PAGE"."IS_STOP" IS '是否禁用';

COMMENT ON COLUMN "AUTH_PAGE"."INDEX_FIELD" IS '排序';

COMMENT ON COLUMN "AUTH_PAGE"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "AUTH_PAGE"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "AUTH_PAGE"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "AUTH_PAGE"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "AUTH_PAGE" IS '页面';

CREATE TABLE "AUTH_ROLE" (
  "ROLE_ID" VARCHAR2(36) NOT NULL ENABLE,
  "NAME" VARCHAR2(255),
  "CODE" VARCHAR2(255),
  "IS_STOP" NUMBER(1, 0),
  "DESCRIPTION" VARCHAR2(255),
  "CREATE_TIME" DATE,
  "CREATE_USER_ID" VARCHAR2(36),
  "MODIFY_USER_ID" VARCHAR2(36),
  "MODIFY_TIME" DATE,
  CONSTRAINT "PK_AUTH_ROLE" PRIMARY KEY ("ROLE_ID")
);

COMMENT ON COLUMN "AUTH_ROLE"."ROLE_ID" IS 'ROLE_ID';

COMMENT ON COLUMN "AUTH_ROLE"."NAME" IS '角色名称';

COMMENT ON COLUMN "AUTH_ROLE"."CODE" IS '角色编码';

COMMENT ON COLUMN "AUTH_ROLE"."IS_STOP" IS '是否停用';

COMMENT ON COLUMN "AUTH_ROLE"."DESCRIPTION" IS '描述';

COMMENT ON COLUMN "AUTH_ROLE"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "AUTH_ROLE"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "AUTH_ROLE"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "AUTH_ROLE"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "AUTH_ROLE" IS '系统角色';

CREATE TABLE "AUTH_ROLE_PAGE" (
  "ROLE_PAGE_ID" VARCHAR2(36),
  "ROLE_ID" VARCHAR2(36),
  "PAGE_ID" VARCHAR2(36)
);

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."ROLE_PAGE_ID" IS '主键';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."PAGE_ID" IS '页面Id';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."ROLE_ID" IS '角色Id';

COMMENT ON TABLE "AUTH_ROLE_PAGE" IS '角色拥有的页面权限';

CREATE TABLE "AUTH_USER_ROLE" (
  "USER_ROLE_ID" VARCHAR2(36),
  "USER_ID" VARCHAR2(36),
  "ROLE_ID" VARCHAR2(36)
);

COMMENT ON COLUMN "AUTH_USER_ROLE"."USER_ROLE_ID" IS 'USER_ROLE_ID';

COMMENT ON COLUMN "AUTH_USER_ROLE"."ROLE_ID" IS 'ROLE_ID';

COMMENT ON COLUMN "AUTH_USER_ROLE"."USER_ID" IS 'USER_ID';

COMMENT ON TABLE "AUTH_USER_ROLE" IS '用户拥有的角色';

CREATE TABLE "TPL_DICT_ITEM" (
  "DICT_ITEM_ID" VARCHAR2(36) NOT NULL ENABLE,
  "DICT_CATEGORY_ID" VARCHAR2(36),
  "CODE" VARCHAR2(255),
  "NAME" VARCHAR2(255),
  "INPUT_CODE" VARCHAR2(255),
  "INDEX_FIELD" NUMBER(10, 0),
  "DESCRIPTION" VARCHAR2(255),
  "IS_STOP" NUMBER(1, 0),
  "CREATE_TIME" DATE,
  "CREATE_USER_ID" VARCHAR2(36),
  "MODIFY_USER_ID" VARCHAR2(36),
  "MODIFY_TIME" DATE,
  CONSTRAINT "PK_TPL_DICT_ITEM" PRIMARY KEY ("DICT_ITEM_ID")
);

COMMENT ON COLUMN "TPL_DICT_ITEM"."DICT_ITEM_ID" IS 'DICT_ITEM_ID';

COMMENT ON COLUMN "TPL_DICT_ITEM"."DICT_CATEGORY_ID" IS 'DICT_CATEGORY_ID';

COMMENT ON COLUMN "TPL_DICT_ITEM"."CODE" IS '编码';

COMMENT ON COLUMN "TPL_DICT_ITEM"."NAME" IS '显示文本';

COMMENT ON COLUMN "TPL_DICT_ITEM"."INPUT_CODE" IS '录入码';

COMMENT ON COLUMN "TPL_DICT_ITEM"."INDEX_FIELD" IS '排序';

COMMENT ON COLUMN "TPL_DICT_ITEM"."DESCRIPTION" IS '描述';

COMMENT ON COLUMN "TPL_DICT_ITEM"."IS_STOP" IS '是否停用';

COMMENT ON COLUMN "TPL_DICT_ITEM"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "TPL_DICT_ITEM"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "TPL_DICT_ITEM"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "TPL_DICT_ITEM"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "TPL_DICT_ITEM" IS '字典项';

CREATE TABLE "TPL_DICT_CATEGORY" (
  "DICT_CATEGORY_ID" VARCHAR2(36) NOT NULL ENABLE,
  "NAME" VARCHAR2(255),
  "CODE" VARCHAR2(255),
  "PARENT" VARCHAR2(36),
  "IS_LEAF" NUMBER(1, 0),
  "DESCRIPTION" VARCHAR2(255),
  "CREATE_TIME" DATE,
  "CREATE_USER_ID" VARCHAR2(36),
  "MODIFY_USER_ID" VARCHAR2(36),
  "MODIFY_TIME" DATE,
  CONSTRAINT "PK_TPL_DICT_CATEGORY" PRIMARY KEY ("DICT_CATEGORY_ID")
);

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."DICT_CATEGORY_ID" IS 'DICT_CATEGORY_ID';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."NAME" IS '字典类别名称';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."CODE" IS '字典类别编码';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."PARENT" IS '父节ID';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."IS_LEAF" IS '是否叶';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."DESCRIPTION" IS '描述';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "TPL_DICT_CATEGORY"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "TPL_DICT_CATEGORY" IS '字典类别';

INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166821748737','商务部','01',NULL,'swb',1,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943042','销售部','02',NULL,NULL,2,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943043','运维部','03',NULL,NULL,3,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943044','商务一科','01001','1286967166821748737','swyk',1,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943045','商务二科','01002','1286967166821748737','syek',2,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943046','销售一科','02001','1286967166825943042',NULL,1,'Jetty',0,NULL,NULL,NULL,TIMESTAMP '2020-07-25 23:02:29.000000');
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943047','销售二科','02002','1286967166825943042','ssek',2,NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO ATJOB.AUTH_DEPT (DEPT_ID,NAME,CODE,PARENT,INPUT_CODE,INDEX_FIELD,DESCRIPTION,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1286967166825943048','情报科','03001','1286967166825943043',NULL,1,'Jetty',0,NULL,NULL,NULL,NULL);

INSERT INTO ATJOB.AUTH_PAGE (PAGE_ID,NAME,CODE,"PATH",PARENT,IS_LEAF,ICON,IS_HIDE,IS_STOP,INDEX_FIELD,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1309735249718038529','用户','user','/auth/user','1289920060701298689',0,NULL,0,0,4,TIMESTAMP '2020-09-26 14:03:11.000000',NULL,NULL,TIMESTAMP '2020-09-26 14:03:36.000000');
INSERT INTO ATJOB.AUTH_PAGE (PAGE_ID,NAME,CODE,"PATH",PARENT,IS_LEAF,ICON,IS_HIDE,IS_STOP,INDEX_FIELD,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1289920060701298689','系统','system','/sys',NULL,0,'setting',0,0,0,TIMESTAMP '2020-08-02 20:41:54.000000','string','string',TIMESTAMP '2020-09-26 13:48:56.000000');
INSERT INTO ATJOB.AUTH_PAGE (PAGE_ID,NAME,CODE,"PATH",PARENT,IS_LEAF,ICON,IS_HIDE,IS_STOP,INDEX_FIELD,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1294652078194429954','页面','page','/auth/page','1289920060701298689',0,NULL,0,0,1,TIMESTAMP '2020-08-15 23:08:03.000000',NULL,'1297568016384421890',TIMESTAMP '2020-10-24 22:31:29.000000');
INSERT INTO ATJOB.AUTH_PAGE (PAGE_ID,NAME,CODE,"PATH",PARENT,IS_LEAF,ICON,IS_HIDE,IS_STOP,INDEX_FIELD,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1294652273795796993','部门','dept','/auth/dept','1289920060701298689',0,NULL,0,0,3,TIMESTAMP '2020-08-15 23:08:50.000000',NULL,NULL,TIMESTAMP '2020-09-26 14:05:26.000000');
INSERT INTO ATJOB.AUTH_PAGE (PAGE_ID,NAME,CODE,"PATH",PARENT,IS_LEAF,ICON,IS_HIDE,IS_STOP,INDEX_FIELD,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1294655115986210817','角色','role','/auth/role','1289920060701298689',0,NULL,0,0,2,TIMESTAMP '2020-08-15 23:20:07.000000',NULL,NULL,TIMESTAMP '2020-09-26 14:05:13.000000');

INSERT INTO ATJOB.AUTH_ROLE (ROLE_ID,NAME,CODE,IS_STOP,DESCRIPTION,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1295000450310885378','情报员','02',1,NULL,TIMESTAMP '2020-08-16 22:12:21.000000',NULL,NULL,TIMESTAMP '2020-09-06 12:00:36.000000');
INSERT INTO ATJOB.AUTH_ROLE (ROLE_ID,NAME,CODE,IS_STOP,DESCRIPTION,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1289920060701298690','系统管理员','01',0,NULL,TIMESTAMP '2020-08-02 20:41:54.000000',NULL,NULL,TIMESTAMP '2020-08-02 20:41:54.000000');

INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241848329359361','1295000450310885378','1294655115986210817');
INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241848346136577','1295000450310885378','1294652273795796993');
INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241848383885314','1295000450310885378','1309735249718038529');
INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241871708409858','1289920060701298690','1294652078194429954');
INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241871737769986','1289920060701298690','1294655115986210817');
INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241871754547202','1289920060701298690','1294652273795796993');
INSERT INTO ATJOB.AUTH_ROLE_PAGE (ROLE_PAGE_ID,ROLE_ID,PAGE_ID) VALUES ('1319241871771324418','1289920060701298690','1309735249718038529');

INSERT INTO ATJOB.AUTH_USER (USER_ID,DEPT_ID,DEPT_NAME,NAME,CODE,LOGIN_NAME,PWD,INPUT_CODE,INDEX_FIELD,DESCRIPTION,PHONE,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1297564473480998914','1286967166825943048','情报科','情报员','qby','qby','{bcrypt}$2a$10$a/3SRQg/gEQABTdXcr4AheF1XteevH5MyBaq.5VX3pudmsKb/9BuG','qby',2,NULL,NULL,1,TIMESTAMP '2020-08-24 00:00:52.000000',NULL,NULL,TIMESTAMP '2020-08-24 00:14:14.000000');
INSERT INTO ATJOB.AUTH_USER (USER_ID,DEPT_ID,DEPT_NAME,NAME,CODE,LOGIN_NAME,PWD,INPUT_CODE,INDEX_FIELD,DESCRIPTION,PHONE,IS_STOP,CREATE_TIME,CREATE_USER_ID,MODIFY_USER_ID,MODIFY_TIME) VALUES ('1297568016384421890','1286967166825943048','情报科','管理员','gly','admin','{bcrypt}$2a$10$TR2Uhe2zlUX6JlWwnzn0Ve9MhEE6r/UyOFPpzuXM37tjlh34rEUWG','gly',1,NULL,NULL,0,TIMESTAMP '2020-08-24 00:14:57.000000',NULL,NULL,NULL);

INSERT INTO ATJOB.AUTH_USER_ROLE (USER_ROLE_ID,USER_ID,ROLE_ID) VALUES ('1297564292622610434','1297564104956866561','1289920060701298690');
INSERT INTO ATJOB.AUTH_USER_ROLE (USER_ROLE_ID,USER_ID,ROLE_ID) VALUES ('1297568016413782018','1297568016384421890','1289920060701298690');
INSERT INTO ATJOB.AUTH_USER_ROLE (USER_ROLE_ID,USER_ID,ROLE_ID) VALUES ('1297559143929901058','1297559143846014977','1295000450310885378');
INSERT INTO ATJOB.AUTH_USER_ROLE (USER_ROLE_ID,USER_ID,ROLE_ID) VALUES ('1297559143913123842','1297559143846014977','1289920060701298690');
INSERT INTO ATJOB.AUTH_USER_ROLE (USER_ROLE_ID,USER_ID,ROLE_ID) VALUES ('1297564473514553345','1297564473480998914','1295000450310885378');
