create user atjob
identified by atjob default tablespace USERS temporary tablespace TEMP;
-- Grant/Revoke role privileges 
grant dba to atjob;
-- Grant/Revoke system privileges 
grant unlimited tablespace to atjob;