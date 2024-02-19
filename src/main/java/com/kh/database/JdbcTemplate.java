package com.kh.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate {

  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName(DbConfig.DRIVER);
      connection = DriverManager.getConnection(DbConfig.MYSQL_URL + DbConfig.CONFIG, DbConfig.ID,
          DbConfig.PASSWORD);
      connection.setAutoCommit(false);

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
