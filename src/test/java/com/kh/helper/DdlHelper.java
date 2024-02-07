package com.kh.helper;

import com.kh.common.JdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DdlHelper {


  public static void dropTable() {
    String sql = "drop table if exists plan";
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void createTable() {
    String sql = """
        create table plan (
        id integer primary key auto_increment,
        title varchar(100) not null,
        memo varchar(300) default '',
        timerCount int default 0,
        clear boolean default false
        )
        """;

    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
