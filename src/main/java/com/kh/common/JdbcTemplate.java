package com.kh.common;

import com.kh.view.plan.constant.DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate {

  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName(DbConfig.driver);
      connection = DriverManager.getConnection(DbConfig.url, DbConfig.id,
          DbConfig.password);
      connection.setAutoCommit(false);

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
