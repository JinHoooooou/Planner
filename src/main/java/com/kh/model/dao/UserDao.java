package com.kh.model.dao;

import com.kh.database.JdbcTemplate_Sim;
import com.kh.model.vo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

  public void insert(User user) {
    try (Connection connection = JdbcTemplate_Sim.getConnection()) {
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

  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    try (Connection connection = JdbcTemplate_Sim.getConnection()) {
      String sql = "select * from users";
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        list.add(User.from(resultSet));
      }
      return list;
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
    return new ArrayList<>();
  }

  public User findByUserId(String userId) {
    try (Connection connection = JdbcTemplate_Sim.getConnection()) {
      String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, userId);
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
  }

  private String createInsertQuery() {
    return "INSERT INTO "
        + "USERS(USER_ID, USER_PW, USER_NAME, NICKNAME, EMAIL, PHONE) "
        + "VALUES(?, ?, ?, ?, ?, ?)";
  }
}
