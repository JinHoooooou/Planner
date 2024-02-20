package com.kh.helper;

import com.kh.database.JdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DdlHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(DdlHelper.class);

  public static void resetSequence() {
    String sql = "DROP SEQUENCE PLAN_SEQ";
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.execute();
      sql = "CREATE SEQUENCE PLAN_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 NOCACHE";
      statement = connection.prepareStatement(sql);
      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void resetUsersSequence() {
    String sql = "DROP SEQUENCE SEQ_USER";
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.execute();
      sql = "CREATE SEQUENCE SEQ_USER NOCACHE";
      statement = connection.prepareStatement(sql);
      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void dropUsersTable() {
    String sql = "drop table users";
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void dropTable() {
    String sql = "drop table plan";
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void createUsersTable() {
    String sql = "CREATE TABLE USERS ("
        + "USERNO     NUMBER PRIMARY KEY,"
        + "USERID     VARCHAR2(20) UNIQUE NOT NULL,"
        + "USERPW     VARCHAR2(20) NOT NULL,"
        + "USERNAME   VARCHAR2(20) NOT NULL,"
        + "NICKNAME   VARCHAR2(20) NOT NULL UNIQUE,"
        + "EMAIL      VARCHAR2(30) NOT NULL,"
        + "PHONE      VARCHAR2(15),"
        + "SSN        VARCHAR2(30) UNIQUE NOT NULL,"
        + "ADDRESS    VARCHAR2(100) NOT NULL,"
        + "INTEREST   VARCHAR2(500),"
        + "ENROLLDATE DATE default sysdate)";

    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }

  public static void createTable() {
    String sql = "CREATE TABLE PLAN (" + "ID          NUMBER PRIMARY KEY,"
        + "TITLE       VARCHAR2(30) NOT NULL," + "START_DATE  DATE DEFAULT SYSDATE NOT NULL,"
        + "END_DATE    DATE DEFAULT SYSDATE NOT NULL," + "CREATE_DATE DATE DEFAULT SYSDATE NOT NULL"
        + ")";

    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      LOGGER.debug(e.getMessage());
    }
  }
}
