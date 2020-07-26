CREATE TABLE "AUTH_ROLE_PAGE" (
    "PAGE_ID" VARCHAR2(36),
    "ROLE_ID" VARCHAR2(36),
    "CREATE_TIME" DATE,
    "CREATE_USER_ID" VARCHAR2(36),
    "MODIFY_USER_ID" VARCHAR2(36),
    "MODIFY_TIME" DATE
);

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."PAGE_ID" IS 'PAGE_PERMISSION_ID';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."ROLE_ID" IS 'ROLE_ID';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."CREATE_TIME" IS '创建时间';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."CREATE_USER_ID" IS '创建人Id@AUTH_USER';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."MODIFY_USER_ID" IS '修改人id@AUTH_USER';

COMMENT ON COLUMN "AUTH_ROLE_PAGE"."MODIFY_TIME" IS '修改时间';

COMMENT ON TABLE "AUTH_ROLE_PAGE" IS '角色拥有的页面权限';