create schema planner;
use planner;

CREATE TABLE USERS (
  USER_ID     VARCHAR(30) PRIMARY KEY,
  USER_PW     VARCHAR(30) NOT NULL,
  USER_NAME    VARCHAR(30) NOT NULL,
  NICKNAME    VARCHAR(50) NOT NULL UNIQUE,
  EMAIL       VARCHAR(50) NOT NULL,
  PHONE       VARCHAR(20),
  ENROLL_DATE DATE DEFAULT (current_date())
);

CREATE TABLE PLAN (
  PLAN_ID           int PRIMARY KEY auto_increment,
  WRITER            VARCHAR(30) REFERENCES USERS ON DELETE CASCADE,
  TITLE             VARCHAR(300) NOT NULL,
  START_DATE        DATE DEFAULT (current_date()),
  END_DATE          DATE DEFAULT (current_date()),
  REMIND_ALARM_DATE DATE,
  COMPLETE          CHAR(1) DEFAULT 'N' CHECK ( COMPLETE IN ( 'Y', 'N' ) ),
  CREATE_DATE       DATE DEFAULT (current_date())
);

CREATE TABLE DETAIL_PLAN (
  DETAIL_PLAN_ID    int PRIMARY KEY auto_increment,
  PLAN_ID           int REFERENCES PLAN ON DELETE CASCADE,
  WRITER            VARCHAR(30) REFERENCES USERS ON DELETE CASCADE,
  CONTENTS          VARCHAR(1000),
  START_TIME        timestamp DEFAULT current_timestamp,
  END_TIME          timestamp DEFAULT current_timestamp,
  REMIND_ALARM_TIME timestamp,
  COMPLETE          CHAR(1) DEFAULT 'N' CHECK ( COMPLETE IN ( 'Y', 'N' ) ),
  CREATE_DATE       DATE DEFAULT (current_date())
);