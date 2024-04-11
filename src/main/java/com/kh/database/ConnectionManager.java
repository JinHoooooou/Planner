package com.kh.database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

  public static Connection getConnection() {
    Dotenv dotenv = Dotenv.configure().directory("env").filename("db.env").load();
    Connection connection = null;
    try {
      Class.forName(dotenv.get("MYSQL_DRIVER"));
      connection = DriverManager.getConnection(dotenv.get("MYSQL_URL"), dotenv.get("USER"), dotenv.get("PASSWORD"));
    } catch (ClassNotFoundException | SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return connection;
  }
}
