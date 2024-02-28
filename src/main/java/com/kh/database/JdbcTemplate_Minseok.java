package com.kh.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate_Minseok {
  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplate_Minseok.class);
  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SEMI", "semi");
      connection.setAutoCommit(false);

    } catch (ClassNotFoundException | SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return connection;
  }
}
