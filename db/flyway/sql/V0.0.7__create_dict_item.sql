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