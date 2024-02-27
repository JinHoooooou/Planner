package com.kh.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {

  private String userId;
  private String userPw;
  private String userName;
  private String nickname;
  private String email;
  private String phone;
  private LocalDate enrollDate;

  public static User from(ResultSet resultSet) throws SQLException {
    return User.builder()
        .userId(resultSet.getString("user_id"))
        .userPw(resultSet.getString("user_pw"))
        .userName(resultSet.getString("user_name"))
        .nickname(resultSet.getString("nickname"))
        .email(resultSet.getString("email"))
        .phone(resultSet.getString("phone"))
        .enrollDate(resultSet.getDate("enroll_date").toLocalDate())
        .build();
  }
}
