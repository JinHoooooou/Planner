package com.kh.users.model;

import jakarta.servlet.http.HttpServletRequest;
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

  private int userNo;
  private String userId;
  private String userPw;
  private String userName;
  private String nickname;
  private String email;
  private String phone;
  private String ssn;
  private String address;
  private LocalDate enrollDate;

  public static User from(ResultSet resultSet) throws SQLException {
    return User.builder()
        .userNo(resultSet.getInt("userno"))
        .userId(resultSet.getString("userid"))
        .userPw(resultSet.getString("userpw"))
        .userName(resultSet.getString("username"))
        .nickname(resultSet.getString("nickname"))
        .email(resultSet.getString("email"))
        .phone(resultSet.getString("phone"))
        .ssn(resultSet.getString("ssn"))
        .address(resultSet.getString("address"))
        .enrollDate(resultSet.getDate("enrolldate").toLocalDate())
        .build();
  }

  public static User from(HttpServletRequest req) {
    return User.builder()
        .userId(req.getParameter("userId"))
        .userPw(req.getParameter("userPw"))
        .userName(req.getParameter("userName"))
        .ssn(req.getParameter("ssn"))
        .nickname(req.getParameter("nickname"))
        .email(req.getParameter("email"))
        .phone(req.getParameter("phone"))
        .address(req.getParameter("address"))
        .build();
  }
}
