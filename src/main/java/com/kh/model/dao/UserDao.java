package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.User;
import java.util.List;

public class UserDao {

  public int save(User user) {
    if (!user.equalsPassword()) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "INSERT INTO USERS(USER_ID, USER_PW, USER_NAME, NICKNAME, EMAIL, PHONE)"
        + " VALUES(?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.executeUpdate(query, user.getUserId(), user.getUserPw(), user.getUserName(),
        user.getNickname(), user.getEmail(), user.getPhone());
  }

  public int updateUserInfo(User update) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET NICKNAME=?, EMAIL=?, PHONE=? WHERE USER_ID=?";
    return jdbcTemplate.executeUpdate(query, update.getNickname(), update.getEmail(),
        update.getPhone(),
        update.getUserId());
  }

  public int updatePassword(User update) {
    if (!update.equalsPassword()) {
      throw new IllegalArgumentException("Not Equal Password with Password Confirm");
    }
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE USERS SET USER_PW=? WHERE USER_ID=?";
    return jdbcTemplate.executeUpdate(query, update.getUserPw(), update.getUserId());
  }

  public int deleteByUserId(String userId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "DELETE FROM USERS WHERE USER_ID=?";
    return jdbcTemplate.executeUpdate(query, userId);
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
