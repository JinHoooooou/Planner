package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.User;
import java.util.List;

public class UserDao {

  public void insert(User user) {
    if (!user.equalsPassword()) {
      throw new IllegalArgumentException("Not Equal Password with Password Confirm.");
    }

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "INSERT INTO USERS(USER_ID, USER_PW, USER_NAME, NICKNAME, EMAIL, PHONE)"
        + " VALUES(?, ?, ?, ?, ?, ?)";
    jdbcTemplate.executeUpdate(query, user.getUserId(), user.getUserPw(), user.getUserName(),
        user.getNickname(), user.getEmail(), user.getPhone());
  }

  public List<User> findAll() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM USERS";
    RowMapper<User> mapper = User::from;
    return jdbcTemplate.executeQuery(query, mapper);
  }

  public User findByUserId(String userId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM USERS WHERE USER_ID=?";
    RowMapper<User> mapper = User::from;
    return jdbcTemplate.executeQueryForOne(query, mapper, userId);
  }

  public void updateUserInfo(User update) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET NICKNAME=?, EMAIL=?, PHONE=? WHERE USER_ID=?";
    jdbcTemplate.executeUpdate(query, update.getNickname(), update.getEmail(), update.getPhone(),
        update.getUserId());

  }

  public void updatePassword(User update) {
    if (!update.equalsPassword()) {
      throw new IllegalArgumentException("Not Equal Password with Password Confirm");
    }

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET USER_PW=? WHERE USER_ID=?";
    jdbcTemplate.executeUpdate(query, update.getUserPw(), update.getUserId());
  }

  public void delete(User user) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "DELETE FROM USERS WHERE USER_ID=?";
    jdbcTemplate.executeUpdate(query, user.getUserId());
  }


  public void login(String userId, String userPw) {
    User target = this.findByUserId(userId);
    if (target.getUserId() == null) {
      throw new IllegalArgumentException("Invalid Id");
    }
    if (!userPw.equals(target.getUserPw())) {
      throw new IllegalArgumentException("Invalid Password");
    }
  }
}
