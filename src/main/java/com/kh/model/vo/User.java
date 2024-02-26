package com.kh.model.vo;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
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

  public static User from(HttpServletRequest req) {
    return User.builder()
        .userPw(req.getParameter("userPw"))
        .userName(req.getParameter("userName"))
        .nickname(req.getParameter("nickname"))
        .email(req.getParameter("email"))
        .phone(req.getParameter("phone"))
        .build();
  }
}
