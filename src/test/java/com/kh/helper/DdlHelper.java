package com.kh.helper;

import com.kh.database.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DdlHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(DdlHelper.class);

  public static void execute(String query) {
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }


  public static void dropSequence(String sequenceName) {
    String query = String.format("DROP SEQUENCE SEQ_%s", sequenceName);
    DdlHelper.execute(query);
  }

  public static void createSequence(String sequenceName) {
    String query = String.format(
        "CREATE SEQUENCE SEQ_%s INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE", sequenceName);
    DdlHelper.execute(query);
  }

  public static void dropTable(String tableName) {
    String query = String.format("DROP TABLE %s", tableName);
    DdlHelper.execute(query);
  }

  public static void createUsersTable() {
    String query = """
        CREATE TABLE USERS (
            USER_ID     VARCHAR2(30) PRIMARY KEY,
            USER_PW     VARCHAR2(30) NOT NULL,
            USER_NAME   VARCHAR2(30) NOT NULL,
            NICKNAME    VARCHAR2(50) NOT NULL UNIQUE,
            EMAIL       VARCHAR2(50) NOT NULL,
            PHONE       VARCHAR2(20),
            ENROLL_DATE DATE DEFAULT SYSDATE
        )
        """;
    DdlHelper.execute(query);
  }

  public static void createPlanTable() {
    String query = """
        CREATE TABLE PLAN (
          PLAN_ID           NUMBER PRIMARY KEY,
          WRITER            VARCHAR2(30) REFERENCES USERS ON DELETE CASCADE,
          TITLE             VARCHAR2(50) NOT NULL,
          START_DATE        DATE DEFAULT SYSDATE,
          END_DATE          DATE DEFAULT SYSDATE,
          CREATE_DATE       DATE DEFAULT SYSDATE,
          REMIND_ALARM_DATE DATE,
          COMPLETE          CHAR(1) DEFAULT 'N' CHECK ( COMPLETE IN ( 'Y', 'N' ) )
        )
        """;
    DdlHelper.execute(query);
  }

  public static void createDetailPlanTable() {
    String query = """
        CREATE TABLE DETAIL_PLAN (
          DETAIL_PLAN_ID    NUMBER PRIMARY KEY,
          PLAN_ID           NUMBER REFERENCES PLAN ON DELETE CASCADE,
          WRITER            VARCHAR2(30) REFERENCES USERS ON DELETE CASCADE,
          CONTENTS          VARCHAR2(50),
          START_TIME        DATE DEFAULT SYSDATE,
          END_TIME          DATE DEFAULT SYSDATE,
          REMIND_ALARM_TIME DATE DEFAULT SYSDATE,
          COMPLETE          CHAR(1) DEFAULT 'N' CHECK ( COMPLETE IN ( 'Y', 'N' ) ),
          CREATE_DATE       DATE DEFAULT SYSDATE
        )
        """;
    DdlHelper.execute(query);
  }
}
