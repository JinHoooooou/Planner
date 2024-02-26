package com.kh.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate {
  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplate.class);
  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName(DbConfig.DRIVER);
      connection = DriverManager.getConnection(DbConfig.URL, DbConfig.ID,
          DbConfig.PASSWORD);
      connection.setAutoCommit(false);

    } catch (ClassNotFoundException | SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return connection;
  }
}
