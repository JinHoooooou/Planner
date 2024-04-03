package com.kh.model.dao;

import com.kh.constant.Message;
import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.User;

public class UserDao {

  public int save(User user) {
    if (this.findByUserId(user.getUserId()) != null) {
      throw new RuntimeException(Message.DUPLICATE_USER_ID);
    }
    if (this.findByNickname(user.getNickname()) != null) {
      throw new RuntimeException(Message.DUPLICATE_USER_NICKNAME);
    }
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "INSERT INTO USERS(USER_ID, USER_PW, USER_NAME, NICKNAME, EMAIL, PHONE)"
        + " VALUES(?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.executeUpdate(query, user.getUserId(), user.getUserPw(), user.getUserName(),
        user.getNickname(), user.getEmail(), user.getPhone());
  }

  public int updateUserInfo(User update) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET USER_PW=?, EMAIL=?, PHONE=? WHERE USER_ID=?";
    return jdbcTemplate.executeUpdate(query, update.getUserPw(), update.getEmail(),
        update.getPhone(),
        update.getUserId());
  }

  public int deleteByUserId(String userId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "DELETE FROM USERS WHERE USER_ID=?";
    return jdbcTemplate.executeUpdate(query, userId);
  }

  public User findByUserId(String userId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    RowMapper<User> mapper = User::from;
    String query = "SELECT * FROM USERS WHERE USER_ID=?";
    return jdbcTemplate.executeQueryForOne(query, mapper, userId);
  }

  public User findByUserNameAndPhone(String userName, String phone) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    RowMapper<User> mapper = User::from;
    String query = "SELECT * FROM USERS WHERE USER_NAME=? AND PHONE=?";
    return jdbcTemplate.executeQueryForOne(query, mapper, userName, phone);
  }

  public User findByNickname(String nickname) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    RowMapper<User> mapper = User::from;
    String query = "SELECT * FROM USERS WHERE NICKNAME=?";
    return jdbcTemplate.executeQueryForOne(query, mapper, nickname);
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
