package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
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
    if (!user.equalsPassword()) {
      throw new IllegalArgumentException("비밀번호와 비밀번호확인이 일치하지 않습니다.");
    }

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

  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = createSelectAllQuery();
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
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = createSelectByUserIdQuery();
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

  public void updateUserInfo(String userId, User update) {
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = createAllUpdateQuery();
      update.setUserId(userId);
      PreparedStatement statement = connection.prepareStatement(sql);
      setValuesForUpdate(update, statement);

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

  public void updatePassword(String validUserId, String password1, String password2) {
    if (!password1.equals(password2)) {
      throw new IllegalArgumentException("패스워드 불일치");
    }
    try (Connection connection = JdbcTemplate.getConnection()) {
      String sql = createPasswordUpdateQuery();
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, password1);
      statement.setString(2, validUserId);

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

  private String createInsertQuery() {
    return "INSERT INTO "
        + "USERS(USER_ID, USER_PW, USER_NAME, NICKNAME, EMAIL, PHONE) "
        + "VALUES(?, ?, ?, ?, ?, ?)";
  }

  private String createSelectAllQuery() {
    return "SELECT * FROM USERS";
  }

  private String createSelectByUserIdQuery() {
    return "SELECT * FROM USERS WHERE USER_ID = ?";
  }

  private String createAllUpdateQuery() {
    return "UPDATE USERS SET NICKNAME=?, EMAIL=?, PHONE=? WHERE USER_ID=?";
  }

  private String createPasswordUpdateQuery() {
    return "UPDATE USERS SET USER_PW=? WHERE USER_ID=?";
  }

  private void setValuesForUpdate(User update, PreparedStatement statement) throws SQLException {
    statement.setString(1, update.getNickname());
    statement.setString(2, update.getEmail());
    statement.setString(3, update.getPhone());
    statement.setString(4, update.getUserId());
  }

  private void setValuesForInsert(User user, PreparedStatement statement) throws SQLException {
    statement.setString(1, user.getUserId());
    statement.setString(2, user.getUserPw());
    statement.setString(3, user.getUserName());
    statement.setString(4, user.getNickname());
    statement.setString(5, user.getEmail());
    statement.setString(6, user.getPhone());
  }


  public void login(String userId, String userPw) {
    User target = this.findByUserId(userId);
    if (target.getUserId() == null) {
      throw new IllegalArgumentException("없는 아이디");
    }
    if (!userPw.equals(target.getUserPw())) {
      throw new IllegalArgumentException("패스워드 불일치");
    }
  }
}
