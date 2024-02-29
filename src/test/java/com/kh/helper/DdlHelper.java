package com.kh.helper;

import com.kh.database.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DdlHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(DdlHelper.class);

  public static void resetPlanSequence() {
    String sql = "DROP SEQUENCE SEQ_PLAN";
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.execute();
      sql = "CREATE SEQUENCE SEQ_PLAN INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE";
      statement = connection.prepareStatement(sql);
      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void dropUsersTable() {
    String sql = "DROP TABLE USERS";
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void dropPlanTable() {
    String sql = "DROP TABLE PLAN";
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void createUsersTable() {
    String sql = """
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

    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void createPlanTable() {
    String sql = """
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

    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }
}
