package com.kh.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

  private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
  private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
  private static final String DB_ID = "SEMI";
  private static final String DB_PW = "semi";
  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName(DB_DRIVER);
      connection = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
      connection.setAutoCommit(false);

    } catch (ClassNotFoundException | SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return connection;
  }
}
