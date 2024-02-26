package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.model.vo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

  public void insert(User user) {
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = createInsertQuery();
      PreparedStatement statement = connection.prepareStatement(sql);
      setValuesForInsert(user, statement);

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

  public User findByUserNo(int userNo) {
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = "select * from users where userno = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, userNo);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return User.from(resultSet);
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return User.builder().build();
  }

  private void setValuesForInsert(User user, PreparedStatement statement) throws SQLException {
    statement.setString(1, user.getUserId());
    statement.setString(2, user.getUserPw());
    statement.setString(3, user.getUserName());
    statement.setString(4, user.getNickname());
    statement.setString(5, user.getEmail());
    statement.setString(6, user.getPhone());
    statement.setString(7, user.getSsn());
    statement.setString(8, user.getAddress());
  }

  private String createInsertQuery() {
    return "insert into "
        + "users(userno, userid, userpw, username, nickname, email, phone, ssn, address) "
        + "values(seq_user.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
  }
}
