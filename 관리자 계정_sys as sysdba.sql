alter session set "_ORACLE_SCRIPT"=true;
CREATE USER SEMI identified by semi;
grant connect, resource, dba to SEMI;
