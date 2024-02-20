package com.kh.users.dao;

import com.kh.database.JdbcTemplate;
import com.kh.users.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UsersDao.class);

  public void insert(Users users) {
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = createInsertQuery();
      PreparedStatement statement = connection.prepareStatement(sql);
      setValuesForInsert(users, statement);

      int result = statement.executeUpdate();
      if (result > 0) {
        connection.commit();
      } else {
        connection.rollback();
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
  }

  public Users findByUserNo(int userNo) {
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = "select * from users where userno = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, userNo);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return Users.from(resultSet);
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return Users.builder().build();
  }

  private void setValuesForInsert(Users users, PreparedStatement statement) throws SQLException {
    statement.setString(1, users.getUserId());
    statement.setString(2, users.getUserPw());
    statement.setString(3, users.getUserName());
    statement.setString(4, users.getNickName());
    statement.setString(5, users.getEmail());
    statement.setString(6, users.getPhone());
    statement.setString(7, users.getSsn());
    statement.setString(8, users.getAddress());
  }

  private String createInsertQuery() {
    return "insert into "
        + "users(userno, userid, userpw, username, nickname, email, phone, ssn, address) "
        + "values(seq_user.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
  }
}
