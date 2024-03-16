package com.kh.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  // DB에 사용될 필드
  private String userId;
  private String userPw;
  private String userName;
  private String nickname;
  private String email;
  private String phone;
  private LocalDate enrollDate;


  private String userPwConfirm;

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

  public boolean equalsPassword() {
    return this.getUserPw().equals(this.getUserPwConfirm());
  }
}
