package com.kh.database;

public interface DbConfig {

  String DRIVER = "oracle.jdbc.driver.OracleDriver";
  String URL = "jdbc:oracle:thin:@localhost:1521:xe";
  String ID = "C##JDBC";
  String PASSWORD = "jdbc";
}
