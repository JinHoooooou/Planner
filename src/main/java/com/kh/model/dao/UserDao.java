package com.kh.model.dao;

import com.kh.database.ConnectionManager;
import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

  public void insert(User user) {
    if (!user.equalsPassword()) {
      throw new IllegalArgumentException("비밀번호와 비밀번호확인이 일치하지 않습니다.");
    }
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "INSERT INTO USERS(USER_ID, USER_PW, USER_NAME, NICKNAME, EMAIL, PHONE)"
        + " VALUES(?, ?, ?, ?, ?, ?)";
    jdbcTemplate.executeUpdate(query, user.getUserId(), user.getUserPw(), user.getUserName(),
        user.getNickname(), user.getEmail(), user.getPhone());
  }

  public void updateUserInfo(User update) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET NICKNAME=?, EMAIL=?, PHONE=? WHERE USER_ID=?";
    jdbcTemplate.executeUpdate(query, update.getNickname(), update.getEmail(), update.getPhone(),
        update.getUserId());
  }

  public void updatePassword(User update) {
    if (!update.equalsPassword()) {
      throw new IllegalArgumentException("패스워드 불일치");
    }
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET USER_PW=? WHERE USER_ID=?";
    jdbcTemplate.executeUpdate(query, update.getUserPw(), update.getUserId());
  }

  public List<User> findAll() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    RowMapper<User> mapper = User::from;
    String query = "SELECT * FROM USERS";
    return jdbcTemplate.executeQuery(query, mapper);
  }

  public User findByUserId(String userId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    RowMapper<User> mapper = User::from;
    String query = "SELECT * FROM USERS WHERE USER_ID=?";
    return jdbcTemplate.executeQueryForOne(query, mapper, userId);
  }

  public void deleteUser(String userId) {
    try (Connection connection = ConnectionManager.getConnection()) {
      String sql = createDeleteUserQuery();
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, userId);

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

  private String createDeleteUserQuery() {
    return "DELETE FROM USERS WHERE USER_ID=?";
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
